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
	 * User will call this method in order
	 * to update a available games list.
	 * @return
	 */
	public abstract boolean requestGamesList();
	
	/**
	 * User can call this method in order to 
	 * join to the given game.
	 * @param game Instance of {@link IGame} interface
	 * implementation which represent the game.
	 * @return true if succeed, false otherwise.
	 */
	public abstract boolean joinGame( IGame game );
	
	/**
	 * Will create a new game on the current server.
	 * @param game Instance of {@link IGame} interface
	 * implementation which represent the game.
	 * @return true if succeed, false otherwise.
	 */
	public abstract boolean createGame( IGame game );
	
	/**
	 * Will add a new games server listener.
	 * @param listener Instance of {@link IGamesServerListener} interface
	 * implementation.
	 * @return true if succeed, false otherwise. 
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
	 * Will remove the given games server listener.
	 * @param listener Instance of {@link IGamesServerListener} interface
	 * implementation.
	 * @return true if succeed, false otherwise.
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
	 * An child class will call this method in order to
	 * update the games list.
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
	 * An child class will call this method in order
	 * to inform the games server that user would like
	 * to join to the given game.
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
