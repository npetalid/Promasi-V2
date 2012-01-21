/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.WebBrowser;

import java.io.IOException;

import javax.swing.JTabbedPane;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.game.IGame;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class WebBrowserDesktopApplication extends ADesktopApplication{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String CONST_APPNAME = "MarketPlace";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "firefox.png";
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 * @throws GuiException
	 * @throws IOException 
	 */
	public WebBrowserDesktopApplication( IGame game ) throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_game = game;
		
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);
		tabbedPane.addTab(MarketPlaceJPanel.CONST_SITENAME, new MarketPlaceJPanel(_game));
		tabbedPane.addTab(HumanResourcesJPanel.CONST_SITENAME, new HumanResourcesJPanel(_game));
	}
}
