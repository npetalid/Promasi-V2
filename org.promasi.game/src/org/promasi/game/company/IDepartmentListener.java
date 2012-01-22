/**
 * 
 */
package org.promasi.game.company;

import org.joda.time.DateTime;

/**
 * @author alekstheod
 *
 */
public interface IDepartmentListener {
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void employeeDischarged( String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime);
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void employeeHired( String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void tasksAssigned(String director, DepartmentMemento department, DateTime dateTime );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void tasksAssignFailed( String director, DepartmentMemento department, DateTime dateTime );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void departmentAssigned( String director, DepartmentMemento department, DateTime dateTime );
}
