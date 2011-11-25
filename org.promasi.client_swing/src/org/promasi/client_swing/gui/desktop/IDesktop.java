package org.promasi.client_swing.gui.desktop;

import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;

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
	 * 
	 */
	public void sleep();
	
	/**
	 * 
	 * @param application
	 */
	public void runApplication( ADesktopApplication application );
}
