/**
 * 
 */
package org.promasi.client_swing.gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.desktop.DesktopJPanel;
import org.promasi.game.IGame;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.project.SerializableProject;
import org.promasi.game.singleplayer.IClientGameListener;

/**
 * @author alekstheod
 *
 */
public class LoadingJPanel extends JPanel implements IClientGameListener {

	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private IMainFrame _mainFrame;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String _username;
	
	/**
	 * 
	 * @param mainFrame
	 * @param game
	 * @throws GuiException
	 */
	public LoadingJPanel( IMainFrame mainFrame, IGame game, String username )throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null");
		}
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		_username = username;
		_mainFrame = mainFrame;
		_game = game;
		_game.addListener(this);
		_game.startGame();
	}

	@Override
	public void gameStarted(IGame game, SerializableGameModel gameModel,
			DateTime dateTime) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					_mainFrame.changePanel(new DesktopJPanel( _mainFrame, _game, _username));
				} catch (GuiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onExecuteStep(IGame game, SerializableCompany company,
			SerializableProject assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(IGame game, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameFinished(IGame game, SerializableGameModel gameModel,
			SerializableCompany company) {
		// TODO Auto-generated method stub
		
	}

}
