/**
 * 
 */
package org.promasi.game;

import java.util.List;

import org.joda.time.DateTime;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.EmployeeTaskModel;
import org.promasi.game.model.generated.MarketPlaceModel;
import org.promasi.game.model.generated.ProjectModel;


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
	public void gameStarted(final MarketPlaceModel marketPlace, CompanyModel company);
	
	/**
	 * 
	 * @param project
	 */
	public void projectAssigned(final CompanyModel company, final ProjectModel project, final DateTime dateTime);
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished(final CompanyModel company, final ProjectModel project, final DateTime dateTime);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeHired(final MarketPlaceModel marketPlace, final CompanyModel company, final EmployeeModel employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param marketPlace
	 * @param company
	 * @param employee
	 * @param dateTime
	 */
	public void employeeDischarged(final MarketPlaceModel marketPlace, final CompanyModel company, final EmployeeModel employee, final DateTime dateTime);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTasksAssigned(final CompanyModel company, final EmployeeModel employee, final List<EmployeeTaskModel> employeeTasks);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTaskDetached(final MarketPlaceModel marketPlace, final CompanyModel company, final EmployeeModel employee, final EmployeeTaskModel employeeTask);
	
	/**
	 * 
	 * @param employee
	 * @param salary
	 */
	public void onPay(final CompanyModel company, final EmployeeModel employee,final Double salary, final DateTime dateTime);
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final CompanyModel company, final DateTime dateTime);
	
	/**
	 * 
	 */
	public void onExecuteStep(final CompanyModel company,final ProjectModel assignedProject, final DateTime dateTime);
	
	
	/**
	 * 
	 */
	public void onTick(final DateTime dateTime);
}
