/**
 * 
 */
package org.promasi.game;

/**
 * @author alekstheod
 *
 */
public class GameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public GameException(){
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public GameException( String message ){
		super( message );
	}

}
