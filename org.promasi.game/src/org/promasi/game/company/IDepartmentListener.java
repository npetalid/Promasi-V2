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
	 * @param director
	 * @param department
	 */
	public void employeeDischarged( String director, DepartmentMemento department);
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void employeeHired( String director, DepartmentMemento department );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void tasksAssigned(String director, DepartmentMemento department );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void tasksAssignFailed( String director, DepartmentMemento department );
	
	/**
	 * 
	 * @param director
	 * @param department
	 */
	public void departmentAssigned( String director, DepartmentMemento department );
}
