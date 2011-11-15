/**
 * 
 */
package org.promasi.client_swing.playmode;

import org.promasi.game.IGame;

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
