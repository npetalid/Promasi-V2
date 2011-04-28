package org.promasi.game.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectTask;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;


/**
 * 
 * Represents the company.
 * 
 * @author m1cRo
 * 
 */
public class Company implements IEmployeeListener
{
	/**
     * The name of the company.
     */
    protected String _name;

    /**
     * The prestige points of the company. Prestige points represent the overall
     * score of the company.
     */
    protected double _prestigePoints;

    /**
     * Description about the company.
     */
    protected String _description;

    /**
     * All the hired employees of the company.
     */
    protected Map<String, Employee> _employees;

    /**
     * The time that the company starts working.
     */
    protected LocalTime _startTime;

    /**
     * The time that the company stops working.
     */
    protected LocalTime _endTime;
    
    /**
     * 
     */
    private Project _assignedProject;

    /**
     * 
     */
    protected double _budget;
    
    /**
     * 
     */
    private DateTime _lastPaymentDateTime;
    
    /**
     * 
     */
    private List<ICompanyListener> _companyListeners;
    
    /**
     * Defines the number of working days for each month.
     */
    public static final int CONST_WORKING_DAYS = 23;// TODO add this to the configuration.

    /**
     * 
     * @param name
     * @param description
     * @param startTime
     * @param endTime
     * @param budget
     * @throws NullArgumentException
     * @throws IllegalArgumentException
     */
    public Company(	final String name,final String description,final LocalTime startTime,final LocalTime endTime,final double budget, final double prestigePoints)throws NullArgumentException, IllegalArgumentException
    {
    	if(name==null)
    	{
    		throw new NullArgumentException("Wrong argument name==null");
    	}
    	
    	if(description==null)
    	{
    		throw new NullArgumentException("Wrong argumen description==null");
    	}
    	
    	if(startTime==null)
    	{
    		throw new NullArgumentException("Wrong argument startTime==null");
    	}
    	
    	if(endTime==null)
    	{
    		throw new NullArgumentException("Wrong argument endTime");
    	}
    	
    	if(startTime.equals(endTime) || startTime.isAfter(endTime))
    	{
    		throw new IllegalArgumentException("Wrong argument startTime is equal or after endTime");
    	}
    	
    	if(budget<=0)
    	{
    		throw new IllegalArgumentException("Wrong argument budget<=0");
    	}
    	
        _prestigePoints = prestigePoints;
        _name = name;
		_startTime=startTime;
		_endTime=endTime;
		_description=description;
		_budget=budget;
		_employees = new TreeMap<String, Employee>();
		_lastPaymentDateTime=new DateTime();
		_companyListeners=new LinkedList<ICompanyListener>();
    }

    /**
     * @return The {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }
    
    /**
     * 
     * @return
     */
    public synchronized double getBudget()
    {
    	return _budget;
    }
    
    /**
     * @return the {@link #_description}.
     */
    public synchronized String getDescription ( )
    {
        return _description;
    }
    
    /**
     * @return The {@link #_prestigePoints}.
     */
    public synchronized double getPrestigePoints ( )
    {
        return _prestigePoints;
    }
    
    /**
     * @return The {@link #_employees}. The list cannot be modified.
     * @throws SerializationException 
     */
    public synchronized Map<String,SerializableEmployee> getEmployees ( ) throws SerializationException
    {
    	Map<String,SerializableEmployee> employees=new TreeMap<String, SerializableEmployee>();
    	for(Map.Entry<String, Employee> entry : _employees.entrySet())
    	{
    		employees.put(entry.getKey(), entry.getValue().getSerializableEmployee());
    	}
    	
        return employees;
    }
    
    /**
     * 
     * @param listener
     */
    public synchronized boolean addListener(ICompanyListener listener)throws NullArgumentException{
    	if(listener==null){
    		throw new NullArgumentException("Wrong argument companyEventHandler==null");
    	}
    	
    	if(!_companyListeners.contains(listener)){
    		_companyListeners.add(listener);
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Assigns a {@link Project} to this {@link Company}, and notifies the
     * {@link Notifier}.
     * 
     * @param project
     *            The {@link Project} to assign to the company.
     * @throws SerializationException 
     */
    public synchronized void assignProject ( Project project )throws NullArgumentException, SerializationException
    {
        if(project==null){
        	throw new NullArgumentException("Wrong argument project==null");
        }
        
        for(ICompanyListener listener : _companyListeners){
        	listener.projectAssigned(getSerializableCompany(),project.getSerializableProject());
        }
        
        _assignedProject=project;
    }

    /**
     * 
     * @param employee
     * @throws SerializationException 
     */
    protected synchronized boolean hireEmployee(Employee employee)throws NullArgumentException, SerializationException
    {
    	if(employee==null){
    		throw new NullArgumentException("Wrong argument employee==null");
    	}
    	
    	if( _employees.containsKey( employee.getEmployeeId() ) ){
    		return false;
    	}
    	
    	_employees.put(employee.getEmployeeId(), employee);
    	employee.setListener(this);
    	
        for(ICompanyListener listener : _companyListeners){
        	listener.employeeHired(getSerializableCompany(),employee.getSerializableEmployee());
        }
    	
    	return true;
    }
    
    /**
     * 
     * @param employee
     * @return
     * @throws NullArgumentException
     * @throws SerializationException 
     */
    public synchronized boolean dischargeEmployee(final String employeeId, final MarketPlace marketPlace)throws NullArgumentException,IllegalArgumentException, SerializationException{
    	if(employeeId==null){
    		throw new NullArgumentException("Wrong argument employeeId==null");
    	}
    	
    	if( !_employees.containsKey(employeeId) ){
    		return false;
    	}
    	
    	Employee currentEmployee=_employees.get(employeeId);
    	_employees.remove(employeeId);
    	currentEmployee.setListener(null);
    	if(!marketPlace.dischargeEmployee(currentEmployee)){
    		_employees.put(currentEmployee.getEmployeeId(), currentEmployee);
    		return false;
    	}
    	
        for(ICompanyListener listener : _companyListeners){
        	listener.employeeDischarged(getSerializableCompany(),currentEmployee.getSerializableEmployee());
        }
    	
    	return true;
    }
  
    /**
     * 
     * @param employee
     * @return
     */
    public boolean isEmployeeHired(Employee employee)
    {
    	if(_employees.containsKey(employee.getEmployeeId()) )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * 
     * @return
     */
    public boolean hasAssignedProject()
    {
    	if(_assignedProject==null){
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * 
     * @return
     */
    public LocalTime getStartWorkingTime(){
    	return _startTime;
    }
    
    /**
     * 
     * @return
     */
    public LocalTime getEndWorkingTime(){
    	return _endTime;
    }
    
    /**
     * 
     * @return
     */
    public int getDayDuration(){
    	return _endTime.getHourOfDay()-_startTime.getHourOfDay();
    }
    
    /**
     * 
     * @param dateTime
     * @param marketPlace in case if a company is insolvent all employees shoud be assigned to marketPlace
     * @return
     * @throws SerializationException
     * @throws NullArgumentException
     */
    public synchronized boolean executeWorkingStep(final DateTime dateTime, MarketPlace marketPlace) throws SerializationException, NullArgumentException{
    	if(marketPlace==null){
    		throw new NullArgumentException("Wrong argument marketPlace==null");
    	}
    	
    	DateTime currentDateTime=dateTime;
    	LocalTime currentTime=currentDateTime.toLocalTime();
    	if(currentTime.isBefore(_startTime) || currentTime.isAfter(_endTime) ){
    		return false;
    	}
    	
    	if( _lastPaymentDateTime.plusDays(1).isBefore(currentDateTime))
    	{
    		for(Map.Entry<String, Employee> entry : _employees.entrySet()){
    			_budget=_budget-entry.getValue().getSalary();
    			if(_budget<0){
    				if(_assignedProject!=null){
    					_prestigePoints=_prestigePoints-_assignedProject.getPrestigePoints();
    				}
    				
    				break;
    			}
    		}
    		
    		if(_budget<0){
		        for(ICompanyListener listener : _companyListeners){
		        	listener.companyIsInsolvent(getSerializableCompany(), _assignedProject.getSerializableProject());
		        	_assignedProject=null;
		        }
		        
	    		
	    		for(Map.Entry<String, Employee> entry : _employees.entrySet()){
	    			entry.getValue().removeAllTasks();
	    			marketPlace.dischargeEmployee(entry.getValue());
	    		}
	    		
	    		_employees.clear();
    		}

    		_lastPaymentDateTime=currentDateTime;
    	}
    	
    	if(_assignedProject==null){
    		return false;
    	}
    	
    	if(_assignedProject!=null){
        	for(Map.Entry<String, Employee> entry : _employees.entrySet()){
        		entry.getValue().executeTasks(_assignedProject.getCurrentStep());
        	}
    	}
    	
        for(ICompanyListener eventHandler : _companyListeners){
        	eventHandler.onExecuteWorkingStep(getSerializableCompany(), _assignedProject.getSerializableProject());
        }
        
        _assignedProject.executeStep();
        
    	if(_assignedProject.isExpired()){
    		_prestigePoints=_prestigePoints+_assignedProject.getPrestigePoints();
    		_budget=_budget+_assignedProject.getProjectPrice();
    		
            for(ICompanyListener eventHandler : _companyListeners){
            	eventHandler.projectFinished(getSerializableCompany(), _assignedProject.getSerializableProject());
            }
            
    		_assignedProject=null;
    	}
    	
    	return true;
    }

    /**
     * 
     * @param employee
     * @param employeeTask
     */
    public synchronized boolean assignTasks(final String employeeId, List<SerializableEmployeeTask> employeeTasks)throws NullArgumentException, IllegalArgumentException{
    	if(employeeId==null){
    		throw new NullArgumentException("Wrong argument employee==null");
    	}
    	
    	if(employeeTasks==null){
    		throw new NullArgumentException("Wrong argument employeeTask==null");
    	}

    	if(_assignedProject!=null){
    		List<EmployeeTask> tasks=new  LinkedList<EmployeeTask>();
    		try {
	    		for(SerializableEmployeeTask employeeTask : employeeTasks){
	        		String taskName=employeeTask.getProjectTaskName();
	    			ProjectTask projectTask=_assignedProject.getProjectTask(taskName);
	    			if(!_employees.containsKey(employeeId)){
	    				return false;
	    			}
	    				
	    			EmployeeTask task=new EmployeeTask(projectTask,employeeTask.getFirstStep(),employeeTask.getLastStep());
	    			tasks.add(task);
	    		}
    		
	    		_employees.get(employeeId).removeAllTasks();
				_employees.get( employeeId ).assignTasks(tasks);
			} catch (NullArgumentException e) {
				return false;
			} catch (IllegalArgumentException e) {
				return false;
			} catch (SerializationException e) {
				return false;
			}
    	}else{
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * 
     * @return
     * @throws SerializationException
     */
    public SerializableCompany getSerializableCompany()throws SerializationException{
    	try {
			return new SerializableCompany(this);
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		}
    }
    
	@Override
	public synchronized void taskAttached(SerializableEmployee employee, List<SerializableEmployeeTask> employeeTasks) throws SerializationException {
        for(ICompanyListener eventHandler : _companyListeners){
        	eventHandler.employeeTasksAttached(getSerializableCompany(), employee, employeeTasks);
        }
	}

	@Override
	public synchronized void taskDetached(SerializableEmployee employee, SerializableEmployeeTask employeeTask) throws SerializationException {
        for(ICompanyListener eventHandler : _companyListeners){
        	eventHandler.employeeTaskDetached(getSerializableCompany(),employee, employeeTask);
        }
	}
	
}
