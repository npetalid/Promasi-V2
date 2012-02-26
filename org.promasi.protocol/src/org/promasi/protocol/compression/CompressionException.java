package org.promasi.protocol.compression;

/**
 * 
 * @author alekstheod
 *
 */
public class CompressionException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 */
	public CompressionException( String message ){
		super(message);
	}
	
	/**
	 * 
	 * @param ex
	 */
	public CompressionException( Throwable ex ){
		super(ex);
	}
	
	/**
	 * 
	 * @param message
	 * @param ex
	 */
	public CompressionException( String message, Throwable ex ){
		super(message, ex);
	}
}
