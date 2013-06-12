package org.promasi.game.multiplayer;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.EmployeeModel;
import org.promasi.game.model.EmployeeTaskModel;
import org.promasi.game.model.GameModelModel;
import org.promasi.game.model.MarketPlaceModel;
import org.promasi.game.model.ProjectModel;


/**
 * 
 * @author m1cRo
 *
 */
public interface IServerGameListener 
{
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param gameModel
	 * @param dateTime
	 */
	public void gameStarted(final String playerId, final IMultiPlayerGame game, final GameModelModel gameModel, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectAssigned(final String playerId, final IMultiPlayerGame game, final CompanyModel company, final ProjectModel project, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectFinished(final String playerId, final IMultiPlayerGame game, final CompanyModel company, final ProjectModel project, final DateTime dateTime);
	
	/**
	 * 
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final String clientId, final IMultiPlayerGame game, final MarketPlaceModel marketPlace, final CompanyModel company, final EmployeeModel employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final String playerId, final IMultiPlayerGame game, final MarketPlaceModel marketPlace, final CompanyModel company, final EmployeeModel employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param employee
	 * @param employeeTasks
	 * @param dateTime
	 */
	public void employeeTasksAssigned(final String playerId, final IMultiPlayerGame game, final CompanyModel company, final EmployeeModel employee, final List<EmployeeTaskModel> employeeTasks, final DateTime dateTime);
	
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
	public void employeeTaskDetached( String playerId, IMultiPlayerGame game, MarketPlaceModel marketPlace, CompanyModel company, EmployeeModel employee, EmployeeTaskModel employeeTask, DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param dateTime
	 */
	public void companyIsInsolvent( String playerId, IMultiPlayerGame game, CompanyModel company, final DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
	public void onExecuteStep( String playerId, IMultiPlayerGame game, CompanyModel company, DateTime dateTime);
	
	/**
	 * 
	 * @param playerId
	 * @param game
	 * @param company
	 * @param assignedProject
	 * @param dateTime
	 */
    public void onExecuteWorkingStep( String playerId, IMultiPlayerGame game, CompanyModel company, ProjectModel assignedProject, DateTime dateTime);
	
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
	public void gameFinished(Map<String, GameModelModel> gameModels);
}
