/**
 * 
 */
package org.promasi.desktop_swing.application.gamestatistics;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.joda.time.DateTime;
import org.promasi.desktop_swing.IDesktop;
import org.promasi.desktop_swing.IMainFrame;
import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.game.IGame;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.GameModelModel;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.utilities.file.RootDirectory;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class GameFinishedApplication extends ADesktopApplication implements IClientGameListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The application's name.
	 */
	private static final String CONST_APPNAME = "Game Statistics";
	
	/**
	 * Instance of {@link IDesktop} interface implementation.
	 * Needed in order to interact with a desktop.
	 */
	private IDesktop _desktop;
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "firefox.png";
	
	/**
	 * Constructor will initialize the object.
	 * @param mainFrame
	 * @throws GuiException
	 * @throws IOException
	 */
	public GameFinishedApplication(final IMainFrame mainFrame, final JPanel gamesPanel, IDesktop desktop)throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null");
		}
		
		if( desktop == null  ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
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
					mainFrame.changePanel( gamesPanel );
					mainFrame.enableWizardMode();
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {}
		});
		
		_desktop = desktop;
		setLayout(new BorderLayout());
	}

	@Override
	public void gameStarted(IGame game, GameModelModel gameModel,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(IGame game, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameFinished(final IGame game, final Map<String, CompanyModel> players) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					add( new GameFinishedPanel(players), BorderLayout.CENTER);
					_desktop.runApplication(GameFinishedApplication.this);
				} catch (GuiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void onExecuteStep(IGame game, CompanyModel company,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

}
