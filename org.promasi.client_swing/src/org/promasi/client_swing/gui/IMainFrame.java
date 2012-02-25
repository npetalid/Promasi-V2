/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.Container;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * @author alekstheod
 *	Represent the interface of MainFrame.
 */
public interface IMainFrame {
	/**
	 * changeFrame method
	 * @param newFrame
	 */
	public void changePanel( JPanel newFrame );
	
	/**
	 * 
	 */
	public void closeMainFrame();
	
	/**
	 * 
	 * @param rect
	 */
	public void setBounds(Rectangle rect);
	
	/**
	 * 
	 */
	public void maximize();
	
	/**
	 * 
	 * @return
	 */
	public Container getContentPane();
	
	/**
	 * Will enable the playing game mode
	 * on the current frame.
	 */
	public void enablePlayingGameMode( );
	
	/**
	 * Will enable the wizard mode of the
	 * current frame.
	 */
	public void enableWizardMode();
	
	/**
	 * Will enable of disable the resize
	 * actions on the main frame.
	 * @param resizable true in order to enable the resizable options,
	 * false otherwise.
	 */
	public void setResizable(boolean resizable);
}
