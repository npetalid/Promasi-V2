/**
 * 
 */
package org.promasi.game.company;

import org.joda.time.DateTime;
import org.promasi.game.model.DepartmentModel;
import org.promasi.game.model.EmployeeModel;

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
	public void employeeDischarged( String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime);
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void employeeHired( String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void tasksAssigned(String director, DepartmentModel department, DateTime dateTime );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void tasksAssignFailed( String director, DepartmentModel department, DateTime dateTime );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void departmentAssigned( String director, DepartmentModel department, DateTime dateTime );
}
