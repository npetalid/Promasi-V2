/**
 * 
 */
package org.promasi.client_swing.gui.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GamesJPanel;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.IMainFrame;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.game.GameModelMemento;
import org.promasi.game.company.CompanyMemento;
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
	private ToolBarJPanel _toolBarPanel;
	
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
		_toolBarPanel = new ToolBarJPanel( username, this );
		add( _toolBarPanel, BorderLayout.NORTH );
		
		_workspace = new PromasiJDesktopPane( _game, username , this );
		add(_workspace);
		
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
        Rectangle rect = new Rectangle(screenSize.width/2 - screenSize.width/4, screenSize.height/2 - screenSize.height/4, screenSize.width/2, screenSize.height/2);
        mainFrame.setBounds(rect);
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
				_toolBarPanel.updateTime(dateTime);
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
					application.setBounds(workspaceWidth/2 - workspaceWidth/4, workspaceHeight/2 - workspaceHeight/4 , workspaceWidth/2, workspaceHeight/2 );
					application.show();
					application.setSelected(true);
				}else{
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
		return _toolBarPanel.addQuickStartButton(button);
	}
}
