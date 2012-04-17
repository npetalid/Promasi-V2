/**
 * 
 */
package org.promasi.utilities.spring;

/**
 * @author alekstheod
 * Represent the spring exception.
 */
public class SpringException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Empty constructor.
	 */
	public SpringException(){
	}
	
	/**
	 * Constructor with message argument.
	 * @param message
	 */
	public SpringException(String message){
		super(message);
	}
	
	/**
	 * 
	 * @param message
	 * @param innerException
	 */
	public SpringException(String message, Exception innerException){
		super(message, innerException);
	}

}
