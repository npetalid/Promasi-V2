/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.promasi.client_swing.components.IMenuEntry;
import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class MultiPlayerPlayMode implements IPlayMode, IMenuEntry {

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
	
															
	/**														
	 * 
	 */
	public static final String CONST_MENUIMAGE = "user_group.png";
		
	/**
	 * 
	 */
	private Icon _menuIcon;
	
	/**
	 * 
	 */
	public MultiPlayerPlayMode(){
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
	public void gotoNextPanel(IMainFrame mainFrame) {
		
	}

	@Override
	public String getUri() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		return _menuIcon;
	}

}
