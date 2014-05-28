/**
 * 
 */
package org.promasi.network.tcp;

/**
 * @author alekstheod
 *
 */
public class NetworkException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public NetworkException(){
		super();
	}
	
	/**
	 * 
	 * @param message
	 */
	public NetworkException( String message ){
		super(message);
	}

}
