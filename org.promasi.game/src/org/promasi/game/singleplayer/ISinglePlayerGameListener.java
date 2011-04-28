/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.List;

import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;

/**
 * @author m1cRo
 *
 */
public interface ISinglePlayerGameListener 
{
	/**
	 * 
	 * @param game
	 * @param gameModel
	 */
	public void gameStarted(final IGame game, final SerializableGameModel gameModel, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectAssigned(final IGame game, final SerializableCompany company, final SerializableProject project, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectFinished(final IGame game, final SerializableCompany company, final SerializableProject project, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final IGame game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final IGame game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param employee
	 * @param employeeTasks
	 */
	public void employeeTasksAttached(final IGame game, final SerializableCompany company, final SerializableEmployee employee, final List<SerializableEmployeeTask> employeeTasks, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTaskDetached(final IGame game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final SerializableEmployeeTask employeeTask, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param dateTime
	 */
	public void companyIsInsolvent(final IGame game, final SerializableCompany company, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
	public void onExecuteStep(final IGame game, final SerializableCompany company,final SerializableProject assignedProject, final DateTime dateTime);
	
	
	/**
	 * 
	 * @param game
	 * @param dateTime
	 */
	public void onTick(final IGame game, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 */
	public void gameFinished(IGame game,final SerializableGameModel gameModel, final SerializableCompany company);
}
