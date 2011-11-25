/**
 * 
 */
package org.promasi.game;

import java.util.List;

import org.joda.time.DateTime;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.MarketPlaceMemento;
import org.promasi.game.project.ProjectMemento;

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
	public void gameStarted(final MarketPlaceMemento marketPlace, CompanyMemento company);
	
	/**
	 * 
	 * @param project
	 */
	public void projectAssigned(final CompanyMemento company, final ProjectMemento project, final DateTime dateTime);
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished(final CompanyMemento company, final ProjectMemento project, final DateTime dateTime);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final MarketPlaceMemento marketPlace, final CompanyMemento company, final EmployeeMemento employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final MarketPlaceMemento marketPlace, final CompanyMemento company, final EmployeeMemento employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTasksAssigned(final CompanyMemento company, final EmployeeMemento employee, final List<EmployeeTaskMemento> employeeTasks);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTaskDetached(final MarketPlaceMemento marketPlace, final CompanyMemento company, final EmployeeMemento employee, final EmployeeTaskMemento employeeTask);
	
	/**
	 * 
	 * @param employee
	 * @param salary
	 */
	public void onPay(final CompanyMemento company, final EmployeeMemento employee,final Double salary, final DateTime dateTime);
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final CompanyMemento company, final DateTime dateTime);
	
	/**
	 * 
	 */
	public void onExecuteStep(final CompanyMemento company,final ProjectMemento assignedProject, final DateTime dateTime);
	
	
	/**
	 * 
	 */
	public void onTick(final DateTime dateTime);
}
