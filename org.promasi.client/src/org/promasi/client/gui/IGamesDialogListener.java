package org.promasi.client.gui;

import org.promasi.client.gui.GamesDialog;


/**
 * 
 * @author m1cRo
 *
 */
public interface IGamesDialogListener 
{
	/**
	 * 
	 * @param selectedGame
	 */
	public void gameSelected(final GamesDialog dialog, final String selectedGame);
	
	/**
	 * 
	 * @param dialog
	 */
	public void gamesDialogClosed(final GamesDialog dialog);
	
}
