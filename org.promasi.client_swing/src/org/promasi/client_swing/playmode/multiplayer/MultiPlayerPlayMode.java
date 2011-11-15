/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.io.IOException;

import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.playmode.IPlayMode;

/**
 * @author alekstheod
 *
 */
public class MultiPlayerPlayMode implements IPlayMode {

	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_NAME = "MultiPlayer";
	
	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_DESCRIPTION = "The purpose of this play mode is to gather the highest score in the competition with other online game players.\n"+
															"You will chose one game from the list of available games, and will play this with other online players.\n"+
															"On each game you will have to complete one or more projects.\n"+
															"You will share the market place with your competitors and improve your skills as project manager";;
	
	@Override
	public String toString(){
		return CONST_PLAYMODE_NAME;
	}
	
	@Override
	public String getDescription() {
		return CONST_PLAYMODE_DESCRIPTION;
	}

	@Override
	public void gotoNextPanel(IMainFrame mainFrame) {
		
	}

	@Override
	public String getUri() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
