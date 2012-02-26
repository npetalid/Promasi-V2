package org.promasi.server;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.network.tcp.ITcpServerListener;
import org.promasi.network.tcp.NetworkException;
import org.promasi.network.tcp.TcpClient;
import org.promasi.network.tcp.TcpServer;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.compression.ZipCompression;
import org.promasi.protocol.messages.CancelGameResponse;
import org.promasi.protocol.messages.GameCanceledRequest;
import org.promasi.protocol.messages.LeaveGameResponse;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.UpdateGamePlayersListRequest;
import org.promasi.server.clientstate.LoginClientState;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * 
 * @author m1cRo
 *
 */
public class ProMaSiServer implements ITcpServerListener
{
	/**
	 * 
	 */
	private TcpServer _server;
	
	/**
	 * 
	 */
	private Map<String, ProMaSiClient> _clients;
	
	/**
	 * 
	 */
	private Map<DateTime, ProMaSiClient> _connectedClients;
	
	/**
	 * 
	 */
	private Map<String, MultiPlayerGame> _availableGames;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private Thread _loginCheckThread;
	
	/**
	 * 
	 * @param portNumber
	 * @throws IllegalArgumentException
	 * @throws NetworkException 
	 */
	public ProMaSiServer(int portNumber)throws NetworkException{
		if(portNumber<0){
			throw new NetworkException("Wrong argument portNumber==null");
		}
		
		_server=new TcpServer();
		if(!_server.addListener(this)){
			throw new NetworkException("TcpServer configuration failed");
		}

		if(!_server.start(portNumber)){
			throw new NetworkException("Wrong argument portNumber");
		}
		
		_clients=new TreeMap<String, ProMaSiClient>();
		_connectedClients=new TreeMap<DateTime, ProMaSiClient>();
		
		_loginCheckThread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(_server.isRunning()){
					try{
						_lockObject.lock();
						Map<DateTime,ProMaSiClient> connectedClients=new TreeMap<DateTime, ProMaSiClient>();
						for(Map.Entry<DateTime, ProMaSiClient > entry : _connectedClients.entrySet()){
							if(entry.getKey().plusSeconds(60).isBefore(new DateTime())){
								entry.getValue().disconnect();
							}else{
								connectedClients.put(entry.getKey(), entry.getValue());
							}
						}
						
						_connectedClients=connectedClients;
					}finally{
						_lockObject.unlock();
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						//Logger
					}
				}

			}
		});
		
		_lockObject = new ReentrantLock();
		_availableGames=new TreeMap<String, MultiPlayerGame>();
		_loginCheckThread.start();
	}

	@Override
	public void serverStarted(int portNumber) {
		// TODO Auto-generated method stub
	}

	@Override
	public void serverStopped() {
		for(Map.Entry<String, ProMaSiClient> entry : _clients.entrySet()){
			entry.getValue().disconnect();
		}
		
		for(Map.Entry<DateTime, ProMaSiClient > entry : _connectedClients.entrySet()){
			entry.getValue().disconnect();
		}
		
		_clients.clear();
		_connectedClients.clear();
	}

	@Override
	public void clientConnected(TcpClient client) {
		try {
			ProMaSiClient pClient=new ProMaSiClient(client, new ZipCompression());
			pClient.addListener(new LoginClientState(this));
			_connectedClients.put(new DateTime(),pClient);
		} catch (NullArgumentException e) {
			//Logger
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void clientDisconnected( TcpClient client) {
		if(_connectedClients.containsKey(client)){
			_connectedClients.remove(client);
		}
		
		Map<String, ProMaSiClient> clients=new TreeMap<String, ProMaSiClient>();
		for(Map.Entry<String, ProMaSiClient> entry : _clients.entrySet()){
			if(!entry.getValue().equals(client)){
				clients.put(entry.getKey(), entry.getValue());
			}
		}
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean login(String userId, ProMaSiClient client){
		boolean result = false;
		try{
			_lockObject.lock();
			if(userId!=null && client != null){
				if(!_clients.containsKey(userId)){
					DateTime loginTime=null;
					for(Map.Entry<DateTime, ProMaSiClient> entry : _connectedClients.entrySet()){
						if(entry.getValue()==client){
							loginTime=entry.getKey();
							break;
						}
					}
					
					if(loginTime!=null && _connectedClients.containsKey(loginTime)){
						_connectedClients.remove(loginTime);
					}
					
					_clients.put(userId, client);
					result = true;
				}
			}
		}finally{
			_lockObject.unlock();
		}

		
		return result;
	}
	
	/**
	 * 
	 * @param clientId
	 * @param game
	 * @return
	 */
	public boolean createGame(String clientId, MultiPlayerGame game){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(clientId != null && game != null){
				if( !_availableGames.containsKey(game.getGameName()) ) {
					_availableGames.put(game.getGameName(), game);
					for(Map.Entry<String, ProMaSiClient> entry : _clients.entrySet()){
						result = sendUpdateGamesListRequest(entry.getValue());
					}
				}
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param clientId
	 * @return
	 */
	public boolean startGame(String clientId, String gameId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( clientId != null && gameId != null){
				if(_availableGames.containsKey(gameId)){
					if(!_availableGames.get(gameId).startGame(clientId)){
						return false;
					}
					
					_availableGames.remove(gameId);
					for(Map.Entry<String, ProMaSiClient> entry : _clients.entrySet()){
						result = sendUpdateGamesListRequest(entry.getValue());
					}
				}
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRunning(){
		boolean result = false;
		
		try{
			_lockObject.lock();
			result = _server.isRunning();;
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getAvailableGames(){
		Map<String, String> games=new TreeMap<String, String>();
		
		try{
			_lockObject.lock();
			for(Map.Entry<String, MultiPlayerGame> entry : _availableGames.entrySet()){
				games.put(entry.getKey(), entry.getValue().getGameDescription());
			}
		}finally{
			_lockObject.unlock();
		}
		
		return games;
	}
	
	/**
	 * 
	 * @param clientId
	 * @param gameId
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public MultiPlayerGame joinGame(String clientId, String gameId)throws NetworkException{
		if(gameId==null){
			throw new NetworkException("Wrong argument gameId==null");
		}
		
		if(clientId==null){
			throw new NetworkException("Wrong argument clientId==null");
		}
		
		try{
			_lockObject.lock();
			if(!_clients.containsKey(clientId)){
				throw new IllegalArgumentException("Wrong argument clientId");
			}
			
			if(!_availableGames.containsKey(gameId)){
				throw new NetworkException("Wrong argument gameId");
			}
			
			if( _availableGames.get(gameId).joinGame(clientId) ) {
				return _availableGames.get(gameId);
			}else{
				throw new IllegalArgumentException("Wrong argument gameId");
			}
		}finally{
			_lockObject.unlock();
		}
	}
	
	
	private boolean sendUpdateGamesListRequest(ProMaSiClient client){
		boolean result = false;
		try{
			_lockObject.lock();
			if(client!=null){
				Map<String, String> gamesList=new TreeMap<String, String>();
				for(Map.Entry<String, MultiPlayerGame> entry : _availableGames.entrySet()){
					gamesList.put(entry.getKey(), entry.getValue().getGameDescription());
				}
				
				UpdateGameListRequest request=new UpdateGameListRequest(gamesList);
				result = client.sendMessage(request.serialize());
			}
			
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param gameId
	 * @throws NullArgumentException
	 */
	public boolean cancelGame(String gameId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(gameId!=null){
				if(_availableGames.containsKey(gameId)){
					MultiPlayerGame game=_availableGames.get(gameId);
					List<String> gamePlayers=game.getGamePlayers();
					for(String playerId : gamePlayers){
						if(playerId!=game.getGameOwnerId() && _clients.containsKey(playerId)){
							_clients.get(playerId).sendMessage(new GameCanceledRequest().serialize());
						}
					}
					
					if(_clients.containsKey(game.getGameOwnerId())){
						_clients.get(game.getGameOwnerId()).sendMessage(new CancelGameResponse().serialize());
					}
				}
			}
			
			_availableGames.remove(gameId);
			result = true;
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param gameId
	 * @param clientId
	 */
	public boolean leaveGame(String gameId, String clientId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( gameId != null && clientId != null ){
				if(_availableGames.containsKey(gameId)){
					List<String> gamePlayers=_availableGames.get(gameId).getGamePlayers();
					if(gamePlayers.contains(clientId) && _clients.containsKey(clientId)){
						_clients.get(clientId).sendMessage(new LeaveGameResponse().serialize());
						gamePlayers.remove(clientId);
						for(String player : gamePlayers){
							if(_clients.containsKey(player)){
								result = _clients.get(clientId).sendMessage(new UpdateGamePlayersListRequest(gamePlayers).serialize());
							}
						}
					}
				}
			}
			
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
}
