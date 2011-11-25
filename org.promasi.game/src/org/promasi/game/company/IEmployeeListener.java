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
	public void taskAttached(String supervisor, EmployeeMemento employee, List<EmployeeTaskMemento> employeeTask);
	
	/**
	 * 
	 * @param employee
	 */
	public void taskDetached(String supervisor, EmployeeMemento employee, EmployeeTaskMemento employeeTask);
}
