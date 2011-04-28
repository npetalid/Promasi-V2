/**
 * 
 */
package org.promasi.client.gui.scheduler;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class ScheduledTask
{
	/**
	 * 
	 */
	private double _percentageComplete;
	
	/**
	 * 
	 */
	private DateTime _startDate;
	
	/**
	 * 
	 */
	private String _projectTaskName;

	/**
	 * 
	 */
	private String _taskId;
	
	/**
	 * 
	 */
	private Map<String, ScheduledEmployee> _employees;
	
	/**
	 * 
	 */
	private Map<String, ScheduledTask> _dependencies;
	
	/**
	 * 
	 */
	private int _dayDuration;
	
	/**
	 * 
	 * @param projectStartDate
	 * @param endDate
	 * @throws NullArgumentException
	 */
	public ScheduledTask(	String taskId, 
							String projectTaskName, 
							DateTime projectStartDate, 
							Map<String, ScheduledEmployee> employees, 
							Map<String, ScheduledTask> dependencies,
							Map<String, ScheduledTask> allScheduledTasks,
							int dayDuration)throws NullArgumentException, IllegalArgumentException, EmployeeTaskConflictException{
		if(projectStartDate==null){
			throw new NullArgumentException("Wrong argument startDate==null");
		}
		
		if(employees==null){
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		if(projectTaskName==null){
			throw new NullArgumentException("Wrong argument projectTaskName==null");
		}
		
		if(employees.size()==0){
			throw new IllegalArgumentException("Wrong argument employees is empty");
		}
		
		if(taskId==null){
			throw new NullArgumentException("Wrong argument taskId==null");
		}
		
		if(taskId.isEmpty()){
			throw new IllegalArgumentException("Wrong argument taskId : is Invalid");
		}
		
		if(dependencies==null){
			throw new NullArgumentException("Wrong argument dependencies==null");
		}
		
		if(dependencies.containsKey(taskId)){
			throw new IllegalArgumentException("Wrong argument taskId is already in dependencies list");
		}
		
		if(dayDuration<=0){
			throw new IllegalArgumentException("Wrong argument dayDuration<=0");
		}
		
		if(allScheduledTasks==null){
			throw new NullArgumentException("Wrong argument allScheduledTasks==null");
		}
		
		for(Map.Entry<String, ScheduledEmployee> entry : employees.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null || !entry.getKey().equals(entry.getValue().getEmployeeId())){
				throw new IllegalArgumentException("Wrong argument employees invalid mapping");
			}
		}
		
		_projectTaskName=projectTaskName;
		_startDate=projectStartDate;
		for(Map.Entry<String, ScheduledTask> entry : dependencies.entrySet()){
			if(_startDate.isBefore(entry.getValue().getEndDate())){
				_startDate=entry.getValue().getEndDate().plusDays(1);
			}
		}
		
		_employees=new TreeMap<String, ScheduledEmployee>(employees);
		_taskId=taskId;
		_dependencies=new TreeMap<String, ScheduledTask>(dependencies);
		_dayDuration=dayDuration;
		validateScheduledTasks(employees,allScheduledTasks);
	}

	/**
	 * 
	 * @return
	 */
	public synchronized Map<String, ScheduledTask> getDependencies(){
		return new TreeMap<String, ScheduledTask>(_dependencies);
	}
	
	/**
	 * @return the endDate
	 */
	public synchronized DateTime getEndDate() {
		int maxWorkingDuration=0;
		for(Map.Entry<String, ScheduledEmployee> entry : _employees.entrySet()){
			if(entry.getValue().getWorkingDuration()>maxWorkingDuration){
				maxWorkingDuration=entry.getValue().getWorkingDuration();
			}
		}
		
		for(Map.Entry<String, ScheduledTask> entry : _dependencies.entrySet()){
			if(_startDate.isBefore(entry.getValue().getEndDate())){
				_startDate=entry.getValue().getEndDate();
			}
		}
		
		return _startDate.plusDays(maxWorkingDuration);
	}

	/**
	 * @return the startDate
	 */
	public synchronized DateTime getStartDate() {
		return _startDate;
	}

	/**
	 * @param percentageComplete the percentageComplete to set
	 */
	public synchronized void setPercentageComplete(double percentageComplete) {
		_percentageComplete = percentageComplete;
	}

	/**
	 * @return the percentageComplete
	 */
	public synchronized double getPercentageComplete() {
		return _percentageComplete;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getTaskName() {
		return _projectTaskName;
	}

	
	/**
	 * 
	 * @return
	 */
	public synchronized Map<String, SerializableEmployeeTask> getEmployeeTasks(final DateTime projectStartDate)throws NullArgumentException, IllegalArgumentException{
		if(projectStartDate==null){
			throw new NullArgumentException("Wrong argument projectStartDate==null");
		}
		
		if(_startDate.isBefore(projectStartDate)){
			throw new IllegalArgumentException("Wrong argument taskStartDate is before projectStartDate");
		}
		
		Period offsetPeriod = new Period(projectStartDate, _startDate, PeriodType.minutes());
		int firstStep=offsetPeriod.getMinutes();
		
		Map<String, SerializableEmployeeTask> result=new TreeMap<String, SerializableEmployeeTask>();
		for(Map.Entry<String, ScheduledEmployee> entry : _employees.entrySet()){
			ScheduledEmployee scheduledEmployee=entry.getValue();
			int lastStep=firstStep+scheduledEmployee.getWorkingDuration()*_dayDuration;
			SerializableEmployeeTask employeeTask=new SerializableEmployeeTask();
			employeeTask.setFirstStep(firstStep);
			employeeTask.setLastStep(lastStep);
			employeeTask.setProjectTaskName(_projectTaskName);

			result.put(scheduledEmployee.getSerializableEmployee().getEmployeeId(), employeeTask);
		}
		
		return result;
	}
	
	/**
	 * @return the taskId
	 */
	public synchronized String getTaskId() {
		return _taskId;
	}
	
	/**
	 * 
	 * @param employees
	 * @param scheduledTasks
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	private synchronized void validateScheduledTasks(Map<String, ScheduledEmployee> employees, Map<String, ScheduledTask> scheduledTasks)throws NullArgumentException, IllegalArgumentException, EmployeeTaskConflictException{
		if(employees==null){
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		if(scheduledTasks==null){
			throw new NullArgumentException("Wrong argument dependencies==null");
		}
		
		for(Map.Entry<String, ScheduledEmployee> employeeEntry : employees.entrySet()){
			if( employeeEntry.getKey()==null || employeeEntry.getValue()==null || !employeeEntry.getKey().equals(employeeEntry.getValue().getEmployeeId()) ){
				throw new IllegalArgumentException("Wrong argument employees");
			}
			
			DateTime startTaskDate=_startDate;
			DateTime endTaskDate=_startDate.plusDays(employeeEntry.getValue().getWorkingDuration());
			
			for(Map.Entry<String, ScheduledTask> depEntry : scheduledTasks.entrySet()){
				if( depEntry.getValue()._employees.containsKey(employeeEntry.getKey() ) && !depEntry.getValue()._taskId.equals(_taskId) ){
					ScheduledEmployee scheduledEmployee=depEntry.getValue()._employees.get(employeeEntry.getKey());
					
					DateTime scheduledStartTaskDate=depEntry.getValue().getStartDate();
					DateTime scheduledEndTaskDate=scheduledStartTaskDate.plusDays( scheduledEmployee.getWorkingDuration() );
					
					if( ( scheduledStartTaskDate.isAfter(startTaskDate) && scheduledStartTaskDate.isBefore(endTaskDate) ) || scheduledStartTaskDate.isEqual(startTaskDate) ){
						throw new EmployeeTaskConflictException(employeeEntry.getKey(), depEntry.getKey(), "Conflict found");
					}
					
					if( ( scheduledEndTaskDate.isAfter(startTaskDate) && scheduledEndTaskDate.isBefore(endTaskDate) ) || scheduledEndTaskDate.isEqual(endTaskDate)){
						throw new EmployeeTaskConflictException(employeeEntry.getKey(), depEntry.getKey(), "Conflict found");
					}
				}
			}
		}
	}
	
}
