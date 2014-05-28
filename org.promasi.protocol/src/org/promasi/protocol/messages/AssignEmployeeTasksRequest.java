/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.List;

import org.promasi.game.model.generated.EmployeeTaskModel;

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
	private List<EmployeeTaskModel> _tasks;
	
	/**
	 * 
	 */
	public AssignEmployeeTasksRequest(){}
	
	/**
	 * 
	 * @param employeeId
	 * @param tasks
	 */
	public AssignEmployeeTasksRequest(String employeeId, List<EmployeeTaskModel> tasks){
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
	public void setTasks(List<EmployeeTaskModel> tasks) {
		_tasks = tasks;
	}
	
	/**
	 * @return the tasks
	 */
	public List<EmployeeTaskModel> getTasks() {
		return _tasks;
	}
}
