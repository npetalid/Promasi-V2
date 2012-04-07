/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.Icon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXPanel;
import org.joda.time.DateTime;
import org.promasi.desktop_swing.IDesktop;
import org.promasi.desktop_swing.PromasiJDesktopPane;
import org.promasi.desktop_swing.TaskBarJPanel;
import org.promasi.desktop_swing.Widget;
import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.desktop_swing.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.game.GameModelMemento;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.PainterFactory;

/**
 * @author alekstheod
 * Represent the Desktop on the ProMaSi system,
 * this class contains all the needed components such
 * as TaskBar, Workspace and so on in
 * order to simulate the pc's desktop functionality.
 */
public class DesktopJPanel extends JXPanel implements IClientGameListener , IDesktop{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of the {@link IGame} interface implementation
	 * which represent the running game.
	 */
	private IGame _game;
	
	/**
	 * Instance of {@link TaskBarJPanel}
	 * represent the task bar on ProMaSi desktop.
	 */
	private TaskBarJPanel _taskBar;
	
	/**
	 * Instance of {@link PromasiJDesktopPane} which represents the desktop
	 * in the promasi system.
	 */
	private PromasiJDesktopPane _workspace;
	
	/**
	 * 
	 */
	private IMainFrame _mainFrame;
	
	/**
	 * 
	 */
	private String _username;
	
	/**
	 * Constructor will initialize the object.
	 * @param mainFrame instance of {@link IMainFrame} interface
	 * implementation needed in order to interact with the main frame.
	 * @param game instance of {@link IGame} interface implementation
	 * needed in order to interact with the running game.
	 * @param username a user id needed in order to determine the user.
	 * @throws GuiException in case of invalid arguments.
	 */
	public DesktopJPanel( IMainFrame mainFrame, IGame game, String username )throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null");
		}
		
		setBackgroundPainter(PainterFactory.getInstance(PainterFactory.ENUM_PAINTER.InactiveBackground));
		_username = username;
		_mainFrame = mainFrame;
		_game = game;
		_game.addListener(this);
		setLayout(new BorderLayout());
		_taskBar = new TaskBarJPanel( username, this );
		add( _taskBar, BorderLayout.NORTH );
		
		_workspace = new PromasiJDesktopPane( _game, username , this);
		_workspace.setOpaque(false);
		_workspace.setBackground(Colors.White.alpha(0f));
		add(_workspace, BorderLayout.CENTER);
	}

	@Override
	public void gameStarted(IGame game, GameModelMemento gameModel,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(IGame game, CompanyMemento company, DateTime dateTime) {}

	@Override
	public void onTick(IGame game, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_taskBar.updateTime(dateTime);
			}
		});
		
	}

	@Override
	public void gameFinished(IGame game, GameModelMemento gameModel,
			CompanyMemento company) {	
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					_game.stopGame();
					_mainFrame.changePanel( new GamesJPanel(_mainFrame, _game.getGamesServer(), _username));
					_mainFrame.enableWizardMode();
				} catch (GuiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void showStartMenu() {
		_workspace.showStartMenu();
	}

	@Override
	public void shutDown() {
		try {
			_game.stopGame();
			_mainFrame.changePanel( new GamesJPanel(_mainFrame, _game.getGamesServer(), _username));
			_mainFrame.enableWizardMode();
		} catch (GuiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void runApplication( ADesktopApplication application ) {
		try {
			if( application != null ){
				boolean alreadyRunning = false;
				JInternalFrame[] frames = _workspace.getAllFrames();
				for (int i = 0; i < frames.length; i++) {
					if( frames[i] == application ){
						alreadyRunning = true;
					}
				 }
				
				if( !alreadyRunning ){
					_workspace.add(application);
					int workspaceWidth = _workspace.getWidth();
					int workspaceHeight = _workspace.getHeight();
					application.setBounds(workspaceWidth/10, workspaceHeight/10 , workspaceWidth - workspaceWidth/5, workspaceHeight -  workspaceHeight/5);
					application.show();
					application.setSelected(true);
				}else{
					application.setIcon(false);
					application.setSelected(true);
				}
			}
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean addQuickStartButton(QuickStartButton button) {
		return _taskBar.addQuickStartButton(button);
	}

	@Override
	public boolean showMessageBox(Object message, String title, int messageType, Icon icon) {
		boolean result = false;
		if( message != null && title != null ){
			JOptionPane.showInternalMessageDialog(_mainFrame.getContentPane(), message, title, messageType, icon);
			result = true;
		}
		
		return result;
	}

	@Override
	public JDesktopPane getDesktopPane() {
		return _workspace;
	}

	@Override
	public boolean addWidget(Widget widget) {
		return _workspace.addWidget(widget);
	}
}
