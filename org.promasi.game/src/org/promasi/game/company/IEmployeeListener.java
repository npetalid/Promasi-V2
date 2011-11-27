package org.promasi.game.company;

import java.util.List;

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
	public void taskAttached(String supervisor, EmployeeMemento employee, List<EmployeeTaskMemento> employeeTasks);
	
	/**
	 * 
	 * @param employee
	 */
	public void taskDetached(String supervisor, EmployeeMemento employee, EmployeeTaskMemento employeeTask);
	
	/**
	 * 
	 * @param supervisor
	 * @param employee
	 * @param employeeTasks
	 */
	public void tasksAssignFailed(String supervisor, EmployeeMemento employee, List<EmployeeTaskMemento> employeeTasks );
}
