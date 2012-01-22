package org.promasi.client_swing.gui.desktop;

import javax.swing.Icon;

import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.QuickStartButton;

public interface IDesktop {

	/**
	 * 
	 */
	public void showStartMenu();
	
	/**
	 * 
	 */
	public void shutDown();
	
	/**
	 * Should return the desktop's width
	 * @return desktop width
	 */
	public int getWidth();
	
	/**
	 * Should return the desktop's height
	 * @return desktop height
	 */
	public int getHeight();
	
	/**
	 * 
	 * @param application
	 */
	public void runApplication( ADesktopApplication application );
	
	/**
	 * Should add an new button to the quick start menu.
	 * @param button Instance of the new button
	 */
	public boolean addQuickStartButton( QuickStartButton button );
	
	/**
	 * 
	 * @param msgBox
	 * @return
	 */
	public boolean showMessageBox( Object message, String title, int messageType, Icon icon );
}
