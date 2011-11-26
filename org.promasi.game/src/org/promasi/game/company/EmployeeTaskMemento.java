package org.promasi.game.company;

import java.util.List;

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
	private List<String> _dependencies;
	
	/**
	 * 
	 */
	private String _taskName;
	
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
	public EmployeeTaskMemento(EmployeeTask employeeTask){
		_firstStep=employeeTask._firstStep;
		_lastStep=employeeTask._lastStep;
		_projectTaskName=employeeTask._projectTask.getName();
		setDependencies(employeeTask._dependencies);
		setTaskName(employeeTask._taskName);
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

	/**
	 * @return the _dependencies
	 */
	public List<String> getDependencies() {
		return _dependencies;
	}

	/**
	 * @param _dependencies the _dependencies to set
	 */
	public void setDependencies(List<String> _dependencies) {
		this._dependencies = _dependencies;
	}

	/**
	 * @return the _taskName
	 */
	public String getTaskName() {
		return _taskName;
	}

	/**
	 * @param _taskName the _taskName to set
	 */
	public void setTaskName(String _taskName) {
		this._taskName = _taskName;
	}
}
