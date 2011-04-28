package org.promasi.client.gui;

/**
 * 
 * @author m1cRo
 *
 */
public interface IWaitingPlayersDialogListener
{
	/**
	 * 
	 * @param messageText
	 */
	public void sendButtonPressed(String messageText);
	
	/**
	 * 
	 */
	public void startGameButtonPressed();
	
	/**
	 * 
	 */
	public void dialogClosed();
}
