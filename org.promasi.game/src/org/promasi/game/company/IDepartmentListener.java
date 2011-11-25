/**
 * 
 */
package org.promasi.game.company;

/**
 * @author alekstheod
 *
 */
public interface IDepartmentListener {
	/**
	 * 
	 * @param company
	 * @param employee
	 */
	public void employeeDischarged( String director, EmployeeMemento employee);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 */
	public void employeeHired( String director, EmployeeMemento employee );
}
