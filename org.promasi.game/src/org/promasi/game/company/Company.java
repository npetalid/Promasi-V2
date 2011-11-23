package org.promasi.game.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private Lock _lockObject;
    
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
    	
    	_lockObject = new ReentrantLock();
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
    public double getBudget()
    {
    	double result = 0;
    	
    	try{
    		_lockObject.lock();
    		result = _budget;
    	}finally{
    		_lockObject.unlock();
    	}
    	
    	return result;
    }
    
    /**
     * @return the {@link #_description}.
     */
    public String getDescription ( )
    {
    	String result;
    	
    	try{
    		_lockObject.lock();
    		result = _description;
    	}finally{
    		_lockObject.unlock();
    	}
    	
        return result;
    }
    
    /**
     * @return The {@link #_prestigePoints}.
     */
    public double getPrestigePoints ( )
    {
    	double result = 0;
    	try{
    		_lockObject.lock();
    		result = _prestigePoints;
    	}finally{
    		_lockObject.unlock();
    	}
    	
        return result;
    }
    
    /**
     * @return The {@link #_employees}. The list cannot be modified.
     * @throws SerializationException 
     */
    public Map<String,SerializableEmployee> getEmployees ( ) throws SerializationException
    {
    	Map<String,SerializableEmployee> employees=new TreeMap<String, SerializableEmployee>();
    	
    	try{
    		_lockObject.lock();
        	for(Map.Entry<String, Employee> entry : _employees.entrySet())
        	{
        		employees.put(entry.getKey(), entry.getValue().getSerializableEmployee());
        	}
    	}finally{
    		_lockObject.unlock();
    	}

        return employees;
    }
    
    /**
     * 
     * @param listener
     */
    public boolean addListener(ICompanyListener listener){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
        	if(listener!=null){
            	if(!_companyListeners.contains(listener)){
            		result = _companyListeners.add(listener);
            	}
        	}
    	}finally{
    		_lockObject.unlock();
    	}

    	return result;
    }
    
    /**
     * 
     * @param listener
     * @return
     */
    public boolean removeListener( ICompanyListener listener ){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
        	if(listener!=null){
            	if( _companyListeners.contains(listener) ){
            		result = _companyListeners.remove(listener);
            	}
        	}
    	}finally{
    		_lockObject.unlock();
    	}
    	
    	return result;
    }
    
    /**
     * Assigns a {@link Project} to this {@link Company}, and notifies the
     * {@link Notifier}.
     * 
     * @param project
     *            The {@link Project} to assign to the company.
     * @throws SerializationException 
     */
    public void assignProject ( Project project )throws NullArgumentException, SerializationException
    {
    	
        if(project==null){
        	throw new NullArgumentException("Wrong argument project==null");
        }
        
    	try{
    		_lockObject.lock();
            for(ICompanyListener listener : _companyListeners){
            	listener.projectAssigned(getSerializableCompany(),project.getSerializableProject());
            }
    	}finally{
    		_lockObject.unlock();
    	}

        _assignedProject=project;
    }

    /**
     * 
     * @param employee
     * @throws SerializationException 
     */
    protected boolean hireEmployee(Employee employee) throws SerializationException
    {
       	boolean result = false;
    	if(employee != null){
        	try{
        		_lockObject.lock();
            	if( !_employees.containsKey( employee.getEmployeeId() ) ){
                	_employees.put(employee.getEmployeeId(), employee);
                	employee.setListener(this);
                	
                    for(ICompanyListener listener : _companyListeners){
                    	listener.employeeHired(getSerializableCompany(),employee.getSerializableEmployee());
                    }
                    
                    result = true;
            	}
        	}finally{
        		_lockObject.unlock();
        	}
    	}
    	
    	return result;
    }
    
    /**
     * 
     * @param employee
     * @return
     * @throws NullArgumentException
     * @throws SerializationException 
     */
    public boolean dischargeEmployee(final String employeeId, final MarketPlace marketPlace)throws NullArgumentException,IllegalArgumentException, SerializationException{
    	boolean result = false;
    	if(employeeId!=null){
        	if( _employees.containsKey(employeeId) ){
            	try{
            		_lockObject.lock();
                	Employee currentEmployee=_employees.get(employeeId);
                	_employees.remove(employeeId);
                	currentEmployee.setListener(null);
                	if(!marketPlace.dischargeEmployee(currentEmployee)){
                        for(ICompanyListener listener : _companyListeners){
                        	listener.employeeDischarged(getSerializableCompany(),currentEmployee.getSerializableEmployee());
                        }
                        
                        result = true;
                	}else{
                			_employees.put(currentEmployee.getEmployeeId(), currentEmployee);
                	}

            	}finally{
            		_lockObject.unlock();
            	}
        	}
    	}
    	
    	return result;
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
    public boolean executeWorkingStep(final DateTime dateTime, MarketPlace marketPlace) throws SerializationException{
    	boolean result = false;
    	
    	if( marketPlace != null && dateTime != null){
    		try{
        		_lockObject.lock();
        		DateTime currentDateTime=dateTime;
            	LocalTime currentTime=currentDateTime.toLocalTime();
            	if(currentTime.isBefore(_endTime) && currentTime.isAfter(_startTime) ){
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
                		
                        for(ICompanyListener listener : _companyListeners){
                        	listener.projectFinished(getSerializableCompany(), _assignedProject.getSerializableProject());
                        }
                        
                		_assignedProject=null;
                	}
            	}
            	
            	result = true;
        	}finally{
        		_lockObject.unlock();
        	}
    	}
    	
    	return result;
    }

    /**
     * 
     * @param employee
     * @param employeeTask
     */
    public boolean assignTasks(final String employeeId, List<SerializableEmployeeTask> employeeTasks){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
        	if(employeeId!=null && employeeTasks != null ){
        		if(_assignedProject!=null){
            		List<EmployeeTask> tasks=new  LinkedList<EmployeeTask>();
            		try {
        	    		for(SerializableEmployeeTask employeeTask : employeeTasks){
        	        		String taskName=employeeTask.getProjectTaskName();
        	    			ProjectTask projectTask=_assignedProject.getProjectTask(taskName);
        	    			if(_employees.containsKey(employeeId)){
            	    			EmployeeTask task=new EmployeeTask(projectTask,employeeTask.getFirstStep(),employeeTask.getLastStep());
            	    			result = tasks.add(task);
        	    			}
        	    		}
            		
        	    		_employees.get(employeeId).removeAllTasks();
        				result &= _employees.get( employeeId ).assignTasks(tasks);
        			} catch (NullArgumentException e) {
        				return false;
        			} catch (IllegalArgumentException e) {
        				return false;
        			}
            	}
        	}
    	}finally{
    		_lockObject.unlock();
    	}
    	
    	return result;
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
