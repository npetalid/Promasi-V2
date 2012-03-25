/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.List;

import org.promasi.game.company.EmployeeTaskMemento;

/**
 * @author m1cRo
 *
 */
public class AssignEmployeeTasksRequest extends Message 
{
	/**
	 * 
	 */
	private String _employeeId;
	
	/**
	 * 
	 */
	private List<EmployeeTaskMemento> _tasks;
	
	/**
	 * 
	 */
	public AssignEmployeeTasksRequest(){}
	
	/**
	 * 
	 * @param employeeId
	 * @param tasks
	 */
	public AssignEmployeeTasksRequest(String employeeId, List<EmployeeTaskMemento> tasks){
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
	public void setTasks(List<EmployeeTaskMemento> tasks) {
		_tasks = tasks;
	}
	
	/**
	 * @return the tasks
	 */
	public List<EmployeeTaskMemento> getTasks() {
		return _tasks;
	}
}
