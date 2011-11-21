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

import org.promasi.game.GameException;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServer;
import org.promasi.game.IGamesServerListener;
import org.promasi.game.singleplayer.SinglePlayerGame;
import org.promasi.game.singleplayer.SinglePlayerGameBuilder;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class SinglePlayerGamesServer implements IGamesServer {
	
	/**
	 * 
	 */
	private IGamesServerListener _listener;
	
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
		synchronized( this ){
			try{
				List<IGame> games = new LinkedList<IGame>();
				String gamesFolders[]=_gamesFolder.list();
				for(int i=0;i<gamesFolders.length;i++){
					try{
						SinglePlayerGameBuilder builder;
						builder = new SinglePlayerGameBuilder( _gamesFolder.getAbsolutePath() + RootDirectory.getInstance().getSeparator() + gamesFolders[i] );
						games.add( new SinglePlayerGame(this, builder.getGame() )  );
					}catch (GameException e) {
						// TODO Auto-generated catch block
					}
				}
				
				if( _listener != null ){
					_listener.updateGamesList( games );
				}	
			}catch(IOException e){
				
			}

		}
		
		return result;
	}


	@Override
	public boolean registerGamesServerListener(IGamesServerListener listener) {
		boolean result = false;
		try{
			_lockObject.lock();
			if( listener != null ){
				_listener = listener;
				result = true;
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
			if( _listener != null ){
				_listener.onJoinGame(game);
			}
		}finally{
			_lockObject.unlock();
		}

		return false;
	}

}
