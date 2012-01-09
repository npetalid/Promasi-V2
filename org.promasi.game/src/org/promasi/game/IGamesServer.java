/**
 * 
 */
package org.promasi.game;

import org.promasi.game.IGamesServerListener;

/**
 * @author alekstheod
 *
 */
public interface IGamesServer {
	
	/**
	 * 
	 * @return
	 */
	public boolean requestGamesList();
	
	/**
	 * 
	 * @param game
	 * @return
	 */
	public boolean joinGame( IGame game );
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean registerGamesServerListener( IGamesServerListener listener );
}
