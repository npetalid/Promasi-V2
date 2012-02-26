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
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class SinglePlayerGamesServer extends AGamesServer {
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private File _gamesFolder;
	
	/**
	 * 
	 */
	public SinglePlayerGamesServer( String gamesFolder )throws IOException{
		if( gamesFolder == null ){
			throw new IOException("");
		}

		_lockObject=new ReentrantLock();
		_gamesFolder=new File(gamesFolder);
		if(!_gamesFolder.isDirectory()){
			throw new IOException("Wrong argument gamesFolder");
		}
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
						games.add( new SinglePlayerGame(this, builder.getGame() )  );
					}catch (GameException e) {
						// TODO Auto-generated catch block
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
}
