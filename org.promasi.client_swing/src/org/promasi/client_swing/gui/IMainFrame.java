/**
 * 
 */
package org.promasi.client_swing.gui;

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
}
