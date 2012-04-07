package org.promasi.desktop_swing;

import javax.swing.Icon;
import javax.swing.JDesktopPane;

import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.desktop_swing.application.QuickStartButton;

public interface IDesktop {

	/**
	 * Will show the start menu on the ProMaSi's desktop.
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
	 * @return
	 */
	public JDesktopPane getDesktopPane();
	
	/**
	 * Will add a new widget to the ProMaSi desktop.
	 * @param widget Instance of {@link Widget} class.
	 * @return true if succeed, false otherwise.
	 */
	public boolean addWidget( Widget widget );
	
	/**
	 * 
	 * @param msgBox
	 * @return
	 */
	public boolean showMessageBox( Object message, String title, int messageType, Icon icon );
}
