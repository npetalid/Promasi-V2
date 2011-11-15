/**
 * 
 */
package org.promasi.client_swing.gui;

import java.util.List;

import javax.swing.JPanel;

import org.joda.time.DateTime;
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
public class PromasiGamePanel extends JPanel implements IClientGameListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	IGame _game;
	
	/**
	 * 
	 */
	public PromasiGamePanel( IGame game )throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_game = game;
		_game.addListener(this);
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
	public void onTick(IGame game, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameFinished(IGame game, SerializableGameModel gameModel,
			SerializableCompany company) {
		// TODO Auto-generated method stub
		
	}

}
