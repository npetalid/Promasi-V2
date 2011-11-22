/**
 * 
 */
package org.promasi.client_swing.gui.desktop;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.List;

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
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
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
		
		_workspace = new PromasiJDesktopPane( username , this );
		add(_workspace);
	}

	@Override
	public void gameStarted(IGame game, SerializableGameModel gameModel,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectAssigned(IGame game, SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(IGame game, SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(IGame game, SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeDischarged(IGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTasksAttached(IGame game, SerializableCompany company,
			SerializableEmployee employee,
			List<SerializableEmployeeTask> employeeTasks, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskDetached(IGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee,
			SerializableEmployeeTask employeeTask, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(IGame game, SerializableCompany company,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(IGame game, SerializableCompany company,
			SerializableProject assignedProject, DateTime dateTime) {
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
	public void gameFinished(IGame game, SerializableGameModel gameModel,
			SerializableCompany company) {	
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
