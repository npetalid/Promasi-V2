/**
 * 
 */
package org.promasi.client_swing.application.gamestatistics;

import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.jdesktop.swingx.JXPanel;
import org.promasi.desktop_swing.IMainFrame;
import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.game.IGame;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class GameFinishedApplication extends ADesktopApplication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The application's name.
	 */
	private static final String CONST_APPNAME = "Game Statistics";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "firefox.png";
	
	/**
	 * 
	 */
	private JPanel _gamesPanel;
	
	/**
	 * Constructor will initialize the object.
	 * @param mainFrame
	 * @throws GuiException
	 * @throws IOException
	 */
	public GameFinishedApplication(final String userName, final IMainFrame mainFrame, final IGame game, JXPanel gamesPanel)throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		// TODO Auto-generated constructor stub
		_gamesPanel = gamesPanel;
		addInternalFrameListener(new InternalFrameListener() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
					mainFrame.changePanel( _gamesPanel );
					mainFrame.enableWizardMode();
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {}
		});
		
	}

}
