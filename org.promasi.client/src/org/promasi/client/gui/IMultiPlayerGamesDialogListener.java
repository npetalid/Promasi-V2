package org.promasi.client.gui;

public interface IMultiPlayerGamesDialogListener {
	/**
	 * 
	 * @param selectedGame
	 */
	public void gameSelected(final MultiPlayerGamesDialog dialog, final String selectedGame);
	
	/**
	 * 
	 */
	public void createNewGameSelected();
}
