/**
 * 
 */
package org.promasi.client_swing.playmode.singleplayer;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.promasi.client_swing.gui.GamesJPanel;
import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.jlist.IMenuEntry;

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
	public static final ILogger _logger = LoggerFactory.getInstance(SinglePlayerPlayMode.class);
	
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
			_logger.error(e.toString());
		} catch (IOException e) {
			_logger.error(e.toString());
		}
	}

	@Override
	public String getUri() {
		String result = "";
		try{
			result = RootDirectory.getInstance().getRootDirectory() + CONST_SINGLEPLAYER_PLAYMODE_FOLDER_NAME;
		}catch( Exception e){
			//TODO log.
		}
		
		return result; 
	}

	@Override
	public Icon getIcon() {
		return _menuIcon;
	}

}
