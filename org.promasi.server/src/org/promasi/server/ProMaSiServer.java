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
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utilities.logger.ILogger;

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
	 * the tcp connections.
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
	 * Instance of {@link ILogger} interface implementation,
	 * needed for logging.
	 */
	private static final ILogger _logger = LoggerFactory.getInstance(ProMaSiServer.class);
	
	/**
	 * Lock object. Needed for thread shared data synchronization.
	 */
	private Lock _lockObject;
	
	/**
	 * Constructor will initialize the ProMaSi server.
	 * @param portNumber the listening tcp port number.
	 * @throws NetworkException In case of initialization errors.
	 */
	public ProMaSiServer(int portNumber)throws NetworkException{
		if(portNumber<0){
			_logger.error("Initialization failed because a wrong portNumber argument < 0");
			throw new NetworkException("Wrong argument portNumber==null");
		}
		
		_server=new TcpServer();
		if(!_server.addListener(this)){
			_logger.error("Initialization failed");
			throw new NetworkException("TcpServer configuration failed");
		}

		if(!_server.start(portNumber)){
			_logger.error("Server start failed");
			throw new NetworkException("Wrong argument portNumber");
		}
		
		_clients=new TreeMap<String, ProMaSiClient>();
		_lockObject = new ReentrantLock();
		_availableGames=new TreeMap<String, MultiPlayerGame>();
		_logger.info("ProMaSi server initialization compelete, listening on port :" + portNumber);
	}

	@Override
	public void serverStarted(int portNumber) {}

	/**
	 * If the ProMaSi server is stopped all
	 * the available clients will be disconnected.
	 */
	@Override
	public void serverStopped() {
		for(Map.Entry<String, ProMaSiClient> entry : _clients.entrySet()){
			entry.getValue().disconnect();
		}
		
		_clients.clear();
		_logger.info("Server stopped all the clients are disconnected");
	}

	@Override
	public void clientConnected(TcpClient client) {
		try {
			_lockObject.lock();
			ProMaSiClient pClient=new ProMaSiClient(client, new ZipCompression());
			pClient.addListener(new LoginClientState(this, pClient));
			pClient.addListener(this);
			_logger.info("New client connection detected");
		} catch (NetworkException e) {
			_logger.info("Exception on client connection : '" + e.toString() + "'");
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
					_logger.info("Client login succeed for client : '" + userId+"'");
				}else{
					_logger.info("Client login failed for client : '" + userId+"'");
				}
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * Will create a new game assigned to the given client id.
	 * The game will be inserted to the available games list.
	 * @param clientId Client's id who are a creator of the game
	 * @param game Instance of {@link = MultiPlayerGame} which represents the game.
	 * @return true if succeed, false otherwise.
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
					
					_logger.info("New game created by client '" + clientId+ "' with game id: '" + game.getGameName() + "'");
				}else{
					_logger.error("Create game failed because an game with the same game id is already running on this server game id = '" + game.getGameName()+"'");
				}
			}else{
				_logger.error("Create game failed because the wrong arguments");
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * Client should call this method in order to start
	 * the prepared by him game.
	 * @param clientId The client id.
	 * @param gameId Id of prepared game.
	 * @return true if succeed, false otherwise.
	 */
	public boolean startGame(String clientId, String gameId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( clientId != null && gameId != null){
				if(_availableGames.containsKey(gameId)){
					if(!_availableGames.get(gameId).startGame(clientId)){
						_logger.error("Game start failed for game with id '" + gameId + "'");
						return false;
					}
					
					_availableGames.remove(gameId);
					for(Map.Entry<String, ProMaSiClient> entry : _clients.entrySet()){
						result = sendUpdateGamesListRequest(entry.getValue());
					}
					
					_logger.info("Start game succeed for game '" + gameId + "'");
				}
			}else{
				_logger.warn("Start game failed for game '" + gameId + "' because no game with the same Id was found in the available games list");
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * User can call this method in order to check
	 * if the ProMasiServer is currently running or not.
	 * @return true if server is running, false otherwise.
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
	 * Will return the list of available games.
	 * @return instance of {@link Map} interface implementation
	 * which contains the pairs of gameId and game description strings.
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
	 * User should call this method in order 
	 * to join to the ProMaSi game.
	 * @param clientId Unique user identification string.
	 * @param gameId The game identification string in which a user will join.
	 * @return Instance of {@link MultiPlayerGame} which represent the game.
	 * @throws NetworkException In case of invalid arguments ( Game with the given game Id is not available,
	 * null arguments and so on).
	 */
	public MultiPlayerGame joinGame(String clientId, String gameId)throws NetworkException{
		if(gameId==null){
			_logger.error("joinGame failed because the wrong argument gameId == null");
			throw new NetworkException("Wrong argument gameId==null");
		}
		
		if(clientId==null){
			_logger.error("joinGame failed because the wrong argument clientId == null");
			throw new NetworkException("Wrong argument clientId==null");
		}
		
		try{
			_lockObject.lock();
			if(!_clients.containsKey(clientId)){
				_logger.error("joinGame failed because the wrong argument clientId which is not in the client list");
				throw new NetworkException("Wrong argument clientId");
			}
			
			if(!_availableGames.containsKey(gameId)){
				_logger.error("joinGame failed because no game with the given id was found in the available games list");
				throw new NetworkException("Wrong argument gameId");
			}
			
			if( _availableGames.get(gameId).joinGame(clientId) ) {
				return _availableGames.get(gameId);
			}else{
				_logger.error("joinGame failed because an internal error");
				throw new NetworkException("Wrong argument gameId");
			}
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * Will send the available games list to the given client.
	 * @param client Instance of {@link ProMaSiClient} which represent a client.
	 * @return true if succeed, false otherwise.
	 */
	private boolean sendUpdateGamesListRequest(ProMaSiClient client){
		boolean result = false;
		try{
			_lockObject.lock();
			if(client!=null){
				Map<String, String> gamesList=new TreeMap<String, String>();
				for(Map.Entry<String, MultiPlayerGame> entry : _availableGames.entrySet()){
					gamesList.put(entry.getKey(), entry.getValue().getGameDescription());
				}
				
				result = client.sendMessage(new UpdateGameListRequest(gamesList));
				_logger.info("Update games request was sent to the client with");
			}else{
				_logger.error("sendUpdateGamesListRequest failed becuase the wrong argument client == null");
			}
			
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * User can call this method in order to abort the 
	 * created from him game. All the game players available
	 * in this game will be removed, {@link GameCanceledRequest} will
	 * be sent to all of these players.
	 * @param gameId The games name.
	 * @return true if succeed, false otherwise.
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
							_clients.get(playerId).sendMessage(new GameCanceledRequest());
						}
					}
					
					if(_clients.containsKey(game.getGameOwnerId())){
						_clients.get(game.getGameOwnerId()).sendMessage(new CancelGameResponse());
					}
					
					_logger.info("Game with id '" + gameId + "' was cancelled");
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
	 * User will call this method in order to leave the
	 * joined game. User will be removed from the game players list.
	 * An UpdateGamePlayersListRequest message will be sent to all the players
	 * of the given name.
	 * @param gameId Id of the game.
	 * @param clientId Client identification string.
	 * @return true if succeed, false otherwise.
	 */
	public boolean leaveGame(String gameId, String clientId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( gameId != null && clientId != null ){
				if(_availableGames.containsKey(gameId)){
					List<String> gamePlayers=_availableGames.get(gameId).getGamePlayers();
					if(gamePlayers.contains(clientId) && _clients.containsKey(clientId)){
						_clients.get(clientId).sendMessage(new LeaveGameResponse());
						gamePlayers.remove(clientId);
						for(String player : gamePlayers){
							if(_clients.containsKey(player)){
								result = _clients.get(clientId).sendMessage(new UpdateGamePlayersListRequest(gamePlayers));
								_logger.info("Client with id '" + clientId + "' did leave the game with id '" + gameId +"'");
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

	/**
	 * In case of an client was disconnected from the
	 * server we have to remove him from the clients list.
	 */
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
				
				_logger.info("Client disconnected");
			}
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void onConnect(ProMaSiClient client) {}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		onDisconnect(client);
	}
}
