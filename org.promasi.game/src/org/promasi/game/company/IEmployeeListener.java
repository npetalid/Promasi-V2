package org.promasi.game.company;

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
	public void taskAssigned(String supervisor, EmployeeMemento employee);
	
	/**
	 * 
	 * @param employee
	 */
	public void taskDetached(String supervisor, EmployeeMemento employee, EmployeeTaskMemento task);
	
	/**
	 * 
	 * @param supervisor
	 * @param employee
	 * @param employeeTasks
	 */
	public void tasksAssignFailed(String supervisor, EmployeeMemento employee );
}
