/**
 * 
 */
package org.promasi.client_swing.playmode;

import java.io.IOException;

import org.promasi.client_swing.gui.IMainFrame;

/**
 * @author alekstheod
 *
 */
public interface IPlayMode {
	
	/**
	 * 
	 * @return
	 */
	public String getDescription();
	
	/**
	 * 
	 * @param mainFrame
	 */
	public void gotoNextPanel( IMainFrame mainFrame );
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getUri();
}
