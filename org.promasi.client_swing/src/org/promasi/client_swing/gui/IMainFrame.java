/**
 * 
 */
package org.promasi.client_swing.gui;

import javax.swing.JPanel;

/**
 * @author alekstheod
 *
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
	 */
	public void maximize();
}
