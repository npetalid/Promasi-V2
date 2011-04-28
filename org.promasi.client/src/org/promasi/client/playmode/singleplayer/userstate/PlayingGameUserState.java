/**
 * 
 */
package org.promasi.client.playmode.singleplayer.userstate;

import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import javax.naming.ConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;
import org.promasi.client.gui.GameFinishedDialog;
import org.promasi.client.gui.IDialogListener;
import org.promasi.client.gui.PlayingGameDialog;
import org.promasi.client.playmode.IPlayMode;
import org.promasi.client.playmode.singleplayer.AbstractUserState;
import org.promasi.game.IGame;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
import org.promasi.game.singleplayer.ISinglePlayerGameListener;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class PlayingGameUserState extends AbstractUserState implements IUserState , IDialogListener, ISinglePlayerGameListener
{
	/**
	 * 
	 */
	private PlayingGameDialog _frame;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 * @param playMode
	 * @throws NullArgumentException
	 * @throws ConfigurationException 
	 */
	public PlayingGameUserState(Shell shell, IPlayMode playMode, IGame game) throws NullArgumentException{
		super(playMode);
		
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		if(game==null){
			throw new NullArgumentException("Wrong argument game==null");
		}
		
		_shell=shell;
		_frame=new PlayingGameDialog(shell, 0, game);
		_frame.registerGamesDialogListener(this);
		_game=game;
		_game.addListener(this);
	}

	@Override
	public void run() {
		_game.startGame();
		_frame.open();
	}

	@Override
	public void gameStarted(IGame game, SerializableGameModel gameModel,
			DateTime dateTime) {
		_frame.gameStarted(game, gameModel, dateTime);
		
	}

	@Override
	public void projectAssigned(IGame game, SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		_frame.projectAssigned(game, company, project, dateTime);
		
	}

	@Override
	public void projectFinished(IGame game, SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		_frame.projectFinished(game, company, project, dateTime);
		
	}

	@Override
	public void employeeHired(IGame game, SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			DateTime dateTime) {
		_frame.employeeHired(game, marketPlace, company, employee, dateTime);
		
	}

	@Override
	public void employeeDischarged(IGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee, DateTime dateTime) {
		_frame.employeeDischarged(game, marketPlace, company, employee, dateTime);
		
	}

	@Override
	public void employeeTasksAttached(IGame game, SerializableCompany company,
			SerializableEmployee employee,
			List<SerializableEmployeeTask> employeeTasks, DateTime dateTime) {
		_frame.employeeTasksAttached(game, company, employee, employeeTasks, dateTime);
		
	}

	@Override
	public void employeeTaskDetached(IGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee,
			SerializableEmployeeTask employeeTask, DateTime dateTime) {
		_frame.employeeTaskDetached(game, marketPlace, company, employee, employeeTask, dateTime);
		
	}

	@Override
	public void companyIsInsolvent(IGame game, SerializableCompany company,
			DateTime dateTime) {
		_frame.companyIsInsolvent(game, company, dateTime);
		
	}

	@Override
	public void onExecuteStep(IGame game, SerializableCompany company,
			SerializableProject assignedProject, DateTime dateTime) {
		_frame.onExecuteStep(game, company, assignedProject, dateTime);
		
	}

	@Override
	public void onTick(IGame game, DateTime dateTime) {
		_frame.onTick(game, dateTime);
	}

	@Override
	public void dialogClosed(Dialog dialog) {
		try {
			ChooseGameUserState userState=new ChooseGameUserState(_shell, this._playMode);
			_game.stopGame();
			changeUserState(userState);
			userState.run();
		} catch (NullArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			//Logger.
			_shell.dispose();
		}
	}

	@Override
	public void gameFinished(final IGame game,final SerializableGameModel gameModel, final SerializableCompany company) {
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new GameFinishedEventHandler(gameModel, company));
		}

		_frame.closeDialog();
	}

	/**
	 * 
	 * @author m1cRo
	 *
	 */
	private class GameFinishedEventHandler implements Runnable{
		/**
		 * 
		 */
		private SerializableGameModel _gameModel;
		
		/**
		 * 
		 * @param gameModel
		 * @param company
		 */
		public GameFinishedEventHandler(SerializableGameModel gameModel, SerializableCompany company){
			_gameModel=gameModel;
		}

		@Override
		public void run() {
			try {
				GameFinishedDialog gameFinishedDialog = new GameFinishedDialog(_shell, SWT.DIALOG_TRIM, "SinglePlayer", _gameModel, new TreeMap<String, SerializableGameModel>());
				gameFinishedDialog.open();
			} catch (NullArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			_frame.closeDialog();
		}
	}
}
