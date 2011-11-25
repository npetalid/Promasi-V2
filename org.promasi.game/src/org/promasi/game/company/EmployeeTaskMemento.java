package org.promasi.game.company;

import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class EmployeeTaskMemento extends SerializableObject 
{

	/**
	 * 
	 */
	private String _projectTaskName;
	
	/**
	 * 
	 */
	private int _firstStep;

	/**
	 * 
	 */
	private int _lastStep;
	
	/**
	 * 
	 */
	public EmployeeTaskMemento(){
		
	}
	
	/**
	 * 
	 * @param employeeTask
	 * @throws SerializationException
	 */
	public EmployeeTaskMemento(EmployeeTask employeeTask)throws SerializationException{
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
