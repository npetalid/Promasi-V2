/**
 * 
 */
package org.promasi.client_swing.gui;

import javax.swing.JPanel;

import org.promasi.game.AGamesServer;

/**
 * @author alekstheod
 * Represent the panel which gives to user
 * the opportunity to create new online game.
 */
public class CreateGameJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private AGamesServer _gamesServer;
	
	/**
	 * 
	 * @param gamesServer
	 * @throws GuiException
	 */
	public CreateGameJPanel( AGamesServer gamesServer)throws GuiException{
		if( gamesServer == null ){
			throw new GuiException("Wrong argument gamesServer == null");
		}
		
		_gamesServer = gamesServer;
	}
}
