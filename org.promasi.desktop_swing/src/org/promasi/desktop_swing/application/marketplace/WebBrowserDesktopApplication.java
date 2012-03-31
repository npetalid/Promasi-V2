/**
 * 
 */
package org.promasi.desktop_swing.application.marketplace;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JTabbedPane;

import org.jdesktop.swingx.JXPanel;
import org.promasi.desktop_swing.IDesktop;
import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.desktop_swing.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utils_swing.AlphaContainer;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.PainterFactory;

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
	public static final String CONST_APPNAME = "Human resources";
	
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
	public WebBrowserDesktopApplication( IGame game, IDesktop desktop ) throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_game = game;
		
		JXPanel bgPanel = new JXPanel();
		bgPanel.setBackgroundPainter(PainterFactory.getInstance(PainterFactory.ENUM_PAINTER.InactiveBackground));
		bgPanel.setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setOpaque(false);
		tabbedPane.setBackground(Colors.White.alpha(0f));
		bgPanel.add(new AlphaContainer(tabbedPane) , BorderLayout.CENTER);
		
		add(bgPanel);
		
		tabbedPane.addTab(MarketPlaceJPanel.CONST_SITENAME, new MarketPlaceJPanel(_game));
		tabbedPane.addTab(HumanResourcesJPanel.CONST_SITENAME, new HumanResourcesJPanel(_game));
		desktop.addQuickStartButton(new QuickStartButton(this, desktop));
	}
}
