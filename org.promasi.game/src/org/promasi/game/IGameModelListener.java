/**
 * 
 */
package org.promasi.game;

import java.util.List;

import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;

/**
 * @author m1cRo
 *
 */
public interface IGameModelListener 
{
	/**
	 * 
	 * @param project
	 */
	public void projectAssigned(final GameModel game, final SerializableCompany company, final SerializableProject project);
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished(final GameModel game, final SerializableCompany company, final SerializableProject project);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final GameModel game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final GameModel game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTasksAssigned(final GameModel game, final SerializableCompany company, final SerializableEmployee employee, final List<SerializableEmployeeTask> employeeTasks);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTaskDetached(final GameModel game, final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final SerializableEmployeeTask employeeTask);
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final GameModel game, final SerializableCompany company);
	
	/**
	 * 
	 */
	public void onExecuteStep(final GameModel game, final SerializableCompany company,final SerializableProject assignedProject);
	
	/**
	 * 
	 * @param game
	 * @param company
	 */
	public void gameFinished(final GameModel game, final SerializableCompany company);
}
