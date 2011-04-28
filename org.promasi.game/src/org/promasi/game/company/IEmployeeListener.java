package org.promasi.game.company;

import java.util.List;

import org.promasi.utilities.serialization.SerializationException;

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
	 * @param employeeTask
	 * @throws SerializationException
	 */
	public void taskAttached(SerializableEmployee employee, List<SerializableEmployeeTask> employeeTask) throws SerializationException;
	
	/**
	 * 
	 * @param employee
	 * @param employee
	 * @throws SerializationException 
	 */
	public void taskDetached(SerializableEmployee employee, SerializableEmployeeTask employeeTask) throws SerializationException;
}
