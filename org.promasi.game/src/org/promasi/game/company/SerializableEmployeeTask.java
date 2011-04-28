package org.promasi.game.company;

import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SerializableEmployeeTask extends SerializableObject 
{

	/**
	 * 
	 */
	private String _projectTaskName;
	
	/**
	 * 
	 */
	public int _firstStep;

	/**
	 * 
	 */
	public int _lastStep;
	
	/**
	 * 
	 */
	public SerializableEmployeeTask(){
		
	}
	
	/**
	 * 
	 * @param employeeTask
	 * @throws SerializationException
	 */
	public SerializableEmployeeTask(EmployeeTask employeeTask)throws SerializationException{
		_firstStep=employeeTask._firstStep;
		_lastStep=employeeTask._lastStep;
		_projectTaskName=employeeTask._projectTask.getName();
	}

	/**
	 * @param projectTaskName the projectTaskName to set
	 */
	public void setProjectTaskName(String projectTaskName) {
		_projectTaskName = projectTaskName;
	}

	/**
	 * @return the projectTaskName
	 */
	public String getProjectTaskName() {
		return _projectTaskName;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLastStep() {
		return _lastStep;
	}

	/**
	 * 
	 * @param lastStep
	 */
	public void setLastStep(int lastStep) {
		_lastStep = lastStep;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFirstStep() {
		return _firstStep;
	}

	/**
	 * 
	 * @param firstStep
	 */
	public void setFirstStep(int firstStep) {
		_firstStep = firstStep;
	}
}
