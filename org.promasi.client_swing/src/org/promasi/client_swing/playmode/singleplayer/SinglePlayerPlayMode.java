/**
 * 
 */
package org.promasi.client_swing.playmode.singleplayer;

import java.io.IOException;

import org.promasi.client_swing.gui.GamesPanel;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class SinglePlayerPlayMode implements IPlayMode {

	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_NAME = "Single Player";
	
	/**
	 * 
	 */
	public static final String CONST_SINGLEPLAYER_PLAYMODE_FOLDER_NAME = "SinglePlayer";
	
	/**
	 * 
	 */
	public static final String CONST_SINGLEPLAYER_USERNAME = "Player";
	
	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_DESCRIPTION =	"The purpose of this play mode is to gather the highest score.<br>"
															+ "You will play through various levels. On each level you will have to complete a project.<br>";
	
	@Override
	public String toString(){
		return CONST_PLAYMODE_NAME;
	}
	
	@Override
	public String getDescription() {
		return CONST_PLAYMODE_DESCRIPTION;
	}

	@Override
	public void gotoNextPanel( IMainFrame mainFrame ) {
        GamesPanel newPanel;
		try {
			SinglePlayerGamesServer server = new SinglePlayerGamesServer( getUri() );
			newPanel = new GamesPanel(mainFrame, server, CONST_SINGLEPLAYER_USERNAME );
			mainFrame.changePanel(newPanel);
		} catch (GuiException e) {
			//ToDo : error
		} catch (IOException e) {
			//ToDo : error
		}
	}

	@Override
	public String getUri() throws IOException {
		return RootDirectory.getInstance().getRootDirectory() + CONST_SINGLEPLAYER_PLAYMODE_FOLDER_NAME;
	}

}
