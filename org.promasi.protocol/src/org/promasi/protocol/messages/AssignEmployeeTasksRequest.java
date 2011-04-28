/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.List;

import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class AssignEmployeeTasksRequest extends SerializableObject 
{
	/**
	 * 
	 */
	private String _employeeId;
	
	/**
	 * 
	 */
	private List<SerializableEmployeeTask> _tasks;
	
	/**
	 * 
	 */
	public AssignEmployeeTasksRequest(){}
	
	/**
	 * 
	 * @param employeeId
	 * @param tasks
	 */
	public AssignEmployeeTasksRequest(String employeeId, List<SerializableEmployeeTask> tasks){
		_tasks=tasks;
		_employeeId=employeeId;
	}
	
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		_employeeId = employeeId;
	}
	
	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return _employeeId;
	}
	
	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(List<SerializableEmployeeTask> tasks) {
		_tasks = tasks;
	}
	
	/**
	 * @return the tasks
	 */
	public List<SerializableEmployeeTask> getTasks() {
		return _tasks;
	}
}
