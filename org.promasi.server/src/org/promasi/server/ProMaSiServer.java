package org.promasi.server;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.network.tcp.ITcpServerListener;
import org.promasi.network.tcp.NetworkException;
import org.promasi.network.tcp.TcpClient;
import org.promasi.network.tcp.TcpServer;
import org.promasi.protocol.client.IClientListener;
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
 * Represent the server in ProMaSi system.
 * Server is responsible to manage all the running online games.
 */
public class ProMaSiServer implements IClientListener, ITcpServerListener
{
	/**
	 * The tcp server needed in order to handle
	 * tcp connections.
	 */
	private TcpServer _server;
	
	/**
	 * List of connected clients. In order to connect client 
	 * should call the login method with the valid userId, which
	 * is unique for each client. After that client will be added to
	 * this list.
	 */
	private Map<String, ProMaSiClient> _clients;
	
	/**
	 * List of available online games. Each registered client is able to
	 * create a new game, in order to create a new game client has to call the
	 * createGame method with a valid arguments. After that a new game will be
	 * inserted to available games list. If a client call startGame method the 
	 * game will be executed and removed from the available games list.
	 */
	private Map<String, MultiPlayerGame> _availableGames;
	
	/**
	 * Lock object. Needed for thread shared data synchronization.
	 */
	private Lock _lockObject;
	
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
		_lockObject = new ReentrantLock();
		_availableGames=new TreeMap<String, MultiPlayerGame>();
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
		
		_clients.clear();
	}

	@Override
	public void clientConnected(TcpClient client) {
		try {
			_lockObject.lock();
			ProMaSiClient pClient=new ProMaSiClient(client, new ZipCompression());
			pClient.addListener(new LoginClientState(this));
			pClient.addListener(this);
		} catch (NullArgumentException e) {
			//Logger
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void clientDisconnected(TcpClient client) {}
	
	/**
	 * Login method, user must call this method in order to
	 * join to online games room. If the an user with the given
	 * userId already registered in the system this method will
	 * fail and return false, otherwise user will be registered.
	 * Call this method before any method you will use with the
	 * given userId.
	 * @param userId Game player identification string, actually the
	 * username of the player.
	 * @param client Instance of {@link = ProMaSiClient} which
	 * represent the game player.
	 * @return true in case if login succeed,
	 * false otherwise.
	 */
	public boolean login(String userId, ProMaSiClient client){
		boolean result = false;
		try{
			_lockObject.lock();
			if(userId!=null && client != null){
				if(!_clients.containsKey(userId)){
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

	@Override
	public void onReceive(ProMaSiClient client, String recData) {}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		try{
			_lockObject.lock();
			Map<String, ProMaSiClient> clients=new TreeMap<String, ProMaSiClient>(_clients);
			for(Map.Entry<String, ProMaSiClient> entry : clients.entrySet()){
				if(entry.getValue() == client){
					_clients.remove(entry.getKey());
				}
				
				if( _availableGames.containsKey(entry.getKey())){
					_availableGames.remove(entry.getKey());
				}
			}
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void onConnect(ProMaSiClient client) {}

	@Override
	public void onConnectionError(ProMaSiClient client) {}
}
