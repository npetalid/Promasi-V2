/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class ScheduledTask {
	
	/**
	 * 
	 */
	private Map<String, EmployeeMemento > _employees;
	
	/**
	 * 
	 */
	private String _taskName;
	
	/**
	 * 
	 */
	private DateTime _startTime;
	
	/**
	 * 
	 */
	private DateTime _endTime;
	
	/**
	 * 
	 */
	private List<String> _dependencies;
	
	/**
	 * 
	 * @param taskName
	 * @param employees
	 */
	public ScheduledTask( String taskName, Map<String, EmployeeMemento> employees, DateTime startTime, DateTime endTime, List<String> dependencies)throws GuiException{
		if( taskName == null ){
			throw new GuiException("Wrong argument taskName == null");
		}
		
		if( employees == null ){
			throw new GuiException("Wrong argument employees == null");
		}
		
		if( startTime == null ){
			throw new GuiException("Wrong argument startTime == null");
		}
		
		if( endTime == null ){
			throw new GuiException("Wrong argument endTime == null");
		}
		
		if( dependencies == null ){
			throw new GuiException("Wrong argument dependencies == null");
		}
		
		_startTime = startTime;
		_endTime = endTime;
		_dependencies = dependencies;
		_taskName = taskName;
		_employees = employees;
	}

	/**
	 * @return the _employees
	 */
	public Map<String, EmployeeMemento > getEmployees() {
		return _employees;
	}

	/**
	 * @return the _taskName
	 */
	public String getTaskName() {
		return _taskName;
	}

	/**
	 * @return the _startTime
	 */
	public DateTime getStartTime() {
		return _startTime;
	}

	/**
	 * @return the _endTime
	 */
	public DateTime getEndTime() {
		return _endTime;
	}
	
	/**
	 * @return the _dependencies
	 */
	public List<String> getDependencies() {
		return _dependencies;
	}
}
