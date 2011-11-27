/**
 * 
 */
package org.promasi.client_swing.gui.desktop;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GamesJPanel;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.taskbar.TaskBarJPanel;
import org.promasi.game.IGame;
import org.promasi.game.GameModelMemento;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.project.ProjectMemento;
import org.promasi.game.singleplayer.IClientGameListener;

/**
 * @author alekstheod
 *
 */
public class DesktopJPanel extends JPanel implements IClientGameListener , IDesktop{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private TaskBarJPanel _taskBarPanel;
	
	/**
	 * 
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
	 * 
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
		
		_username = username;
		_mainFrame = mainFrame;
		_game = game;
		_game.addListener(this);
		setLayout(new BorderLayout());
		_taskBarPanel = new TaskBarJPanel( username, this );
		add( _taskBarPanel, BorderLayout.NORTH );
		
		_workspace = new PromasiJDesktopPane( _game, username , this );
		add(_workspace);
	}

	@Override
	public void gameStarted(IGame game, GameModelMemento gameModel,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(IGame game, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(IGame game, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_taskBarPanel.updateTime(dateTime);
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
		} catch (GuiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void sleep() {
		// TODO Auto-generated method stub
		
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
					application.setBounds(_workspace.getWidth()/2 - 100, _workspace.getHeight()/2 - 100 , _workspace.getWidth()/2 + 100, _workspace.getHeight()/2 + 100 );
					_workspace.add(application);
					application.show();
					application.setSelected(true);
				}
			}
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
