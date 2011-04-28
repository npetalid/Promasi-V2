package org.promasi.client.gui;

/**
 * 
 * @author m1cRo
 *
 */
public interface IWaitingGameDialogListener 
{
	/**
	 * 
	 * @param messageText
	 */
	public void sendButtonPressed(String messageText);
	
	/**
	 * 
	 * @param dialog
	 */
	public void dialogClosed(WaitingGameDialog dialog);
}
