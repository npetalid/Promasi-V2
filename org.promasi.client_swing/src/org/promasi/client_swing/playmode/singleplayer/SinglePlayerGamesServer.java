/**
 * 
 */
package org.promasi.client_swing.playmode.singleplayer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.game.AGamesServer;
import org.promasi.game.GameException;
import org.promasi.game.IGame;
import org.promasi.game.singleplayer.SinglePlayerGame;
import org.promasi.game.singleplayer.SinglePlayerGameFolder;
import org.promasi.utilities.clock.Clock;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author alekstheod
 * Represent the games server of single player play mode.
 * This class is responsible to de-serialize the games which are available in
 * the games directory.
 */
public class SinglePlayerGamesServer extends AGamesServer {
	/**
	 * Lock object needed for the 
	 * threads synchronization.
	 */
	private Lock _lockObject;
	
	/**
	 * The games folder.
	 */
	private File _gamesFolder;
	
	/**
	 * Logger needed for logging.
	 */
	private ILogger _logger = LoggerFactory.getInstance(SinglePlayerGamesServer.class);
	
	/**
	 * Constructor will initialize the object.
	 * @param gamesFolder the games folder name.
	 * @throws IOException In case of initialization fail
	 * or wrong arguments.
	 */
	public SinglePlayerGamesServer( String gamesFolder )throws IOException{
		if( gamesFolder == null ){
			throw new IOException("");
		}

		_lockObject=new ReentrantLock();
		_gamesFolder=new File(gamesFolder);
		if(!_gamesFolder.isDirectory()){
			_logger.warn("Initializatin failed because games directory doesn't exist");
			throw new IOException("Wrong argument gamesFolder");
		}
		
		_logger.debug("Initialization complete");
	}

	@Override
	public boolean requestGamesList() {
		boolean result = false;
		try{
			_lockObject.lock();
			try{
				List<IGame> games = new LinkedList<IGame>();
				String gamesFolders[]=_gamesFolder.list();
				for(int i=0;i<gamesFolders.length;i++){
					try{
						SinglePlayerGameFolder builder;
						builder = new SinglePlayerGameFolder( _gamesFolder.getAbsolutePath() + RootDirectory.getInstance().getSeparator() + gamesFolders[i] );
						games.add( new SinglePlayerGame(this, builder.readGame(), new Clock() )  );
					}catch (GameException e) {
						_logger.warn("Request games list failed because the GameException " + e.toString());
					}
				}
				
				updateGamesList(games);
			}catch(IOException e){
				
			}

		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean joinGame( IGame game ) {
		try{
			_lockObject.lock();
			onJoinGame(game);
		}finally{
			_lockObject.unlock();
		}

		return false;
	}

	@Override
	public boolean createGame(String gameId, IGame game) {
		return false;
	}
}
