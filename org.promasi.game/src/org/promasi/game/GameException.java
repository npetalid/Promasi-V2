/**
 * 
 */
package org.promasi.game;

/**
 * @author alekstheod
 * Represent the game exception.
 * Used in org.promasi.game module.
 */
public class GameException extends Exception {

	/**
	 * Serial version Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty constructor.
	 */
	public GameException(){
		super();
	}
	
	/**
	 * Constructor with message argument.
	 * @param message The message string.
	 */
	public GameException( String message ){
		super( message );
	}
	
	/**
	 * Constructor with a message string and the inner exception.
	 * @param message The message string.
	 * @param innerException Inner exception.
	 */
	public GameException( String message, Throwable innerException){
		super(message, innerException);
	}

}
