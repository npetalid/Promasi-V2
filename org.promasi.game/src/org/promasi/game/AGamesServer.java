/**
 * 
 */
package org.promasi.game;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author alekstheod
 * Represent the GamesServer on ProMaSi system.
 */
public abstract class AGamesServer {
	
	/**
	 * List of Games server listeners.
	 */
	private List< IGamesServerListener > _listeners;
	
	/**
	 * Lock object.
	 */
	private Lock _lockObject;
	
	/**
	 * Constructor will initialize the object.
	 */
	public AGamesServer(){
		_listeners = new LinkedList<>();
		_lockObject = new ReentrantLock();
	}
	
	/**
	 * 
	 * @return
	 */
	public abstract boolean requestGamesList();
	
	/**
	 * 
	 * @param game
	 * @return
	 */
	public abstract boolean joinGame( IGame game );
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean addListener(IGamesServerListener listener){
		boolean result = false;
		try{
			_lockObject.lock();
			if( listener != null && !_listeners.contains(listener)){
				result = _listeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeListener(IGamesServerListener listener){
		boolean result = false;
		try{
			_lockObject.lock();
			if( listener != null && _listeners.contains(listener)){
				result = _listeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param games
	 */
	protected void updateGamesList( List<IGame> games){
		try{
			_lockObject.lock();
			for( IGamesServerListener listener : _listeners ){
				listener.updateGamesList(games);
			}
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * 
	 * @param game
	 */
	protected void onJoinGame( IGame game ){
		try{
			_lockObject.lock();
			for( IGamesServerListener listener : _listeners ){
				listener.onJoinGame(game);
			}
		}finally{
			_lockObject.unlock();
		}
	}
}
