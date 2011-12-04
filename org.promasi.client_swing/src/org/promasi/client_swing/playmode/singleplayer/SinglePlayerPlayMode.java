/**
 * 
 */
package org.promasi.client_swing.playmode.singleplayer;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.promasi.client_swing.components.JList.IMenuEntry;
import org.promasi.client_swing.gui.GamesJPanel;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class SinglePlayerPlayMode implements IPlayMode, IMenuEntry {

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
	public static final String CONST_MENUIMAGE = "user.png";
	
	/**
	 * 
	 */
	private Icon _menuIcon;
	
	/**
	 * 
	 */
	public static final String CONST_PLAYMODE_DESCRIPTION =	"The purpose of this play mode is to gather the highest score.<br>"
															+ "You will play through various levels. On each level you will have to complete a project.<br>";
	
	
	public SinglePlayerPlayMode(){
		try {
			_menuIcon = new ImageIcon(RootDirectory.getInstance().getImagesDirectory() + CONST_MENUIMAGE);
		} catch (IOException e) {
			//TODO log
		}
	}
	
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
        GamesJPanel newPanel;
		try {
			SinglePlayerGamesServer server = new SinglePlayerGamesServer( getUri() );
			newPanel = new GamesJPanel(mainFrame, server, CONST_SINGLEPLAYER_USERNAME );
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

	@Override
	public Icon getIcon() {
		return _menuIcon;
	}

}
