/**
 * 
 */
package org.promasi.client_swing.gui;

/**
 * @author alekstheod
 *
 */
public class GuiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public GuiException(){
		super();
	}
	
	/**
	 * 
	 * @param e
	 */
	public GuiException(Exception e){
		super(e);
	}
	
	/**
	 * 
	 * @param message
	 */
	public GuiException( String message ){
		super(message);
	}

}
