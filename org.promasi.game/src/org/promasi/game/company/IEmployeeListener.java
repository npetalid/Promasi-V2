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
	public void taskAttached(String supervisor, SerializableEmployee employee, List<SerializableEmployeeTask> employeeTask);
	
	/**
	 * 
	 * @param employee
	 */
	public void taskDetached(String supervisor, SerializableEmployee employee, SerializableEmployeeTask employeeTask);
}
