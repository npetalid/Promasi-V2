package org.promasi.game.multiplayer;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;

/**
 * 
 * @author m1cRo
 *
 */
public interface IMultiPlayerGameListener 
{
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param gameModel
	 * @param dateTime
	 */
	public void gameStarted(final String playerId, final IMultiPlayerGame game, final SerializableGameModel gameModel, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectAssigned(final String playerId, final IMultiPlayerGame game, final SerializableCompany company, final SerializableProject project, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectFinished(final String playerId, final IMultiPlayerGame game, final SerializableCompany company, final SerializableProject project, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final String clientId, final IMultiPlayerGame game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final String playerId, final IMultiPlayerGame game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param employee
	 * @param employeeTasks
	 * @param dateTime
	 */
	public void employeeTasksAssigned(final String playerId, final IMultiPlayerGame game, final SerializableCompany company, final SerializableEmployee employee, final List<SerializableEmployeeTask> employeeTasks, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param employeeTask
	 * @param dateTime
	 */
	public void employeeTaskDetached(final String playerId, final IMultiPlayerGame game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final SerializableEmployeeTask employeeTask, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param dateTime
	 */
	public void companyIsInsolvent(final String playerId, final IMultiPlayerGame game, final SerializableCompany company, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
	public void onExecuteStep(final String playerId, final IMultiPlayerGame game, final SerializableCompany company,final SerializableProject assignedProject, final DateTime dateTime);
	
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param dateTime
	 */
	public void onTick(final String playerId, final IMultiPlayerGame game, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param gamePlayers
	 */
	public void playersListUpdated(final IMultiPlayerGame game, final List<String> gamePlayers);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param Message
	 */
	public void messageSent(final String playerId, final IMultiPlayerGame game, final String message);
	
	/**
	 * 
	 * @param gameModels
	 */
	public void gameFinished(Map<String, SerializableGameModel> gameModels);
}
