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

import org.promasi.client_swing.playmode.IGamesServer;
import org.promasi.client_swing.playmode.IGamesServerListener;
import org.promasi.game.GameException;
import org.promasi.game.IGame;
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
	private List<IGame> _games;
	
	/**
	 * 
	 */
	public SinglePlayerGamesServer( String gamesFolder )throws IOException{
		_lockObject=new ReentrantLock();
		_games = new LinkedList<IGame>();
		File file=new File(gamesFolder);
		if(file.isDirectory()){
			String gamesFolders[]=file.list();
			for(int i=0;i<gamesFolders.length;i++){
				try{
					SinglePlayerGameBuilder builder;
					builder = new SinglePlayerGameBuilder( gamesFolder + RootDirectory.getInstance().getSeparator() + gamesFolders[i] );
					_games.add( new SinglePlayerGame( builder.getGame() )  );
				}catch (GameException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}

	@Override
	public boolean requestGamesList() {
		boolean result = false;
		synchronized( this ){
			if( _listener != null ){
				_listener.updateGamesList( _games );
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
