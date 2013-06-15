package org.promasi.game.company;

import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.EmployeeTaskModel;

/**
 * 
 * @author m1cRo
 *
 */
public interface IEmployeeListener
{
	/**
	 * 
	 * @param employee
	 */
	public void taskAssigned(String supervisor, EmployeeModel employee);
	
	/**
	 * 
	 * @param employee
	 */
	public void taskDetached(String supervisor, EmployeeModel employee, EmployeeTaskModel task);
	
	/**
	 * 
	 * @param supervisor
	 * @param employee
	 * @param employeeTasks
	 */
	public void tasksAssignFailed(String supervisor, EmployeeModel employee );
}
