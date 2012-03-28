/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.client_swing.playmode.singleplayer.SinglePlayerGamesServer;
import org.promasi.game.AGamesServer;
import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGame;
import org.promasi.game.multiplayer.MultiPlayerGameFolder;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.CreateGameRequest;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class MultiplayerFolderGamesServer extends AGamesServer implements IClientListener{

	private Lock _lockObject;
	
	/**
	 * The games folder.
	 */
	private File _gamesFolder;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private Map<String, GameModel > _games; 
	
	/**
	 * Logger needed for logging.
	 */
	private ILogger _logger = LoggerFactory.getInstance(SinglePlayerGamesServer.class);
	
	/**
	 * 
	 */
	public MultiplayerFolderGamesServer( String gamesFolder, ProMaSiClient client )throws IOException, GuiException{
		if( gamesFolder == null ){
			throw new IOException("");
		}

		if( client == null ){
			throw new GuiException("Wrong argument client == null");
		}
		
		_lockObject=new ReentrantLock();
		_gamesFolder=new File(gamesFolder);
		_client = client;
		_games = new TreeMap<String, GameModel>();
		if(!_gamesFolder.isDirectory()){
			_logger.warn("Initializatin failed because games directory doesn't exist");
			throw new IOException("Wrong argument gamesFolder");
		}
		
		_logger.debug("Initialization complete");
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.game.AGamesServer#requestGamesList()
	 */
	@Override
	public boolean requestGamesList() {
		boolean result = false;
		try{
			_lockObject.lock();
			_games.clear();
			try{
				List<IGame> games = new LinkedList<IGame>();
				for(String folderName : _gamesFolder.list() ){
					try{
						MultiPlayerGameFolder folder = new MultiPlayerGameFolder( _gamesFolder.getAbsolutePath() + RootDirectory.getInstance().getSeparator() + folderName );
						GameModel model = folder.readGame();
						games.add( new MultiPlayerGame(this, _client, model.getName(), model.getGameDescription()) );
						_games.put(model.getName(), model);
					}catch (GameException e) {
						_logger.warn("Request games list failed because the GameException " + e.toString());
					}catch (GuiException e) {
						_logger.warn("Request games list failed because the GuiException " + e.toString());
					}
				}
				
				updateGamesList(games);
			}catch(IOException e){
				_logger.warn("Unable to create a MultiPlayer game because of exception : " + e.toString());
			}

		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.AGamesServer#joinGame(org.promasi.game.IGame)
	 */
	@Override
	public boolean joinGame(IGame game) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.AGamesServer#createGame(org.promasi.game.IGame)
	 */
	@Override
	public boolean createGame(String gameId, IGame game) {
		boolean result = false;
		
		if( game != null && gameId != null){
			if( _games.containsKey(game.getName())){
				CreateGameRequest request = new CreateGameRequest(gameId, _games.get(game.getName()).getMemento());
				result = _client.sendMessage(request);
			}
		}
		
		return result;
	}

	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		// TODO Auto-generated method stub
	}

}
