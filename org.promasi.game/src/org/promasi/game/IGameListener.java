/**
 * 
 */
package org.promasi.game;

import java.util.List;

import org.joda.time.DateTime;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;

/**
 * @author m1cRo
 *
 */
public interface IGameListener 
{
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 */
	public void gameStarted(final SerializableMarketPlace marketPlace, SerializableCompany company);
	
	/**
	 * 
	 * @param project
	 */
	public void projectAssigned(final SerializableCompany company, final SerializableProject project, final DateTime dateTime);
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished(final SerializableCompany company, final SerializableProject project, final DateTime dateTime);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTasksAssigned(final SerializableCompany company, final SerializableEmployee employee, final List<SerializableEmployeeTask> employeeTasks);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTaskDetached(final SerializableMarketPlace marketPlace, final SerializableCompany company, final SerializableEmployee employee, final SerializableEmployeeTask employeeTask);
	
	/**
	 * 
	 * @param employee
	 * @param salary
	 */
	public void onPay(final SerializableCompany company, final SerializableEmployee employee,final Double salary, final DateTime dateTime);
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final SerializableCompany company, final DateTime dateTime);
	
	/**
	 * 
	 */
	public void onExecuteStep(final SerializableCompany company,final SerializableProject assignedProject, final DateTime dateTime);
	
	
	/**
	 * 
	 */
	public void onTick(final DateTime dateTime);
}
