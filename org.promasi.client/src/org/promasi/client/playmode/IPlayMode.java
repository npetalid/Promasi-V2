/**
 * 
 */
package org.promasi.client.playmode;

import java.io.IOException;

import org.promasi.client.playmode.singleplayer.userstate.IUserState;

/**
 * @author m1cRo
 *
 */
public interface IPlayMode 
{
	/**
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String getUri() throws IOException;
	
	/**
	 * 
	 */
	public void play();
	
	/**
	 * 
	 * @param newState
	 * @return
	 */
	public boolean changeState(IUserState newState);
}
