package org.promasi.client.gui;

import org.promasi.game.GameModel;

/**
 * 
 * @author m1cRo
 *
 */
public interface IMakeGameDialogListener 
{
	/**
	 * 
	 * @param game
	 */
	public void creteGameButtonPressed(String onlineGameId, GameModel game);
	
	/**
	 * 
	 * @param dialog
	 */
	public void dialogClosed(MakeGameDialog dialog);
}
