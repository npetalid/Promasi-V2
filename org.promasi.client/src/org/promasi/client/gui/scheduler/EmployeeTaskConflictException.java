package org.promasi.client.gui.scheduler;

import org.promasi.utilities.exceptions.NullArgumentException;

public class EmployeeTaskConflictException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String _employeeId;
	
	/**
	 * 
	 */
	private String _taskName;
	
	/**
	 * 
	 * @param employeeId
	 * @param taskName
	 * @param message
	 * @throws NullArgumentException
	 */
	public EmployeeTaskConflictException(String employeeId, String taskName, String message)throws NullArgumentException{
		super(message);
		if(employeeId==null){
			throw new NullArgumentException("Wrong argument employeeId==null");
		}
		
		if(taskName==null){
			throw new NullArgumentException("Wrong argument taskName==null");
		}
		
		_employeeId=employeeId;
		_taskName=taskName;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return _taskName;
	}
	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return _employeeId;
	}
}
