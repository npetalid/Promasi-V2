/**
 * 
 */
package org.promasi.game.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.game.GameException;
import org.promasi.game.project.ProjectTask;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author alekstheod
 *
 */
public class Department{

    /**
     * All the hired employees of the company.
     */
    protected Map<String, Employee> _employees;
    
    /**
     * 
     */
    private List<IDepartmentListener> _listeners;
    
    /**
     * 
     */
    private Lock _lockObject;
    
    /**
     * 
     */
    private String _director;
    
    /**
     * 
     */
    public Department(){
    	_employees = new TreeMap<String, Employee>();
    	_lockObject = new ReentrantLock();
    }
    
    /**
     * @return The {@link #_employees}. The list cannot be modified.
     * @throws SerializationException 
     */
    public Map<String,EmployeeMemento> getEmployees ( ) throws SerializationException
    {
    	Map<String,EmployeeMemento> employees=new TreeMap<String, EmployeeMemento>();
    	
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
     * @param employee
     * @throws SerializationException 
     */
    protected boolean hireEmployee(String supervisor, Employee employee) throws SerializationException
    {
       	boolean result = false;
    	if(employee != null){
        	try{
        		_lockObject.lock();
            	if( !_employees.containsKey( employee.getEmployeeId() ) ){
                	_employees.put(employee.getEmployeeId(), employee);
                    for(IDepartmentListener listener : _listeners){
                    	listener.employeeHired(_director, employee.getSerializableEmployee());
                    }
                    
                    employee.setSupervisor(_director);
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
     * @param listener
     * @return
     */
    public boolean addEmployeeListener( IEmployeeListener listener ){
    	boolean result = false;
    	if( listener != null ){
    		try{
    			result = true;
				_lockObject.lock();
				for (Map.Entry<String, Employee> employee : _employees.entrySet()){
					result &= employee.getValue().addListener(listener);
				}
			}finally{
				_lockObject.unlock();
			}
    	}

		return result;
    }
    
    /**
     * 
     * @param listener
     * @return
     */
    public boolean removeEmployeeListener( IEmployeeListener listener ){
    	boolean result = false;
    	if( listener != null ){
    		try{
    			result = true;
				_lockObject.lock();
				for (Map.Entry<String, Employee> employee : _employees.entrySet()){
					result &= employee.getValue().removeListener(listener);
				}
			}finally{
				_lockObject.unlock();
			}
    	}

		return result;
    }
    
    /**
     * 
     * @param listener
     * @return
     */
    public boolean addListener( IDepartmentListener listener ){
    	boolean result = false;
    	if( listener != null ){
    		try{
				_lockObject.lock();
				if ( !_listeners.contains(listener) ){
					result = _listeners.add(listener);
				}
			}finally{
				_lockObject.unlock();
			}
    	}

		return result;
    }
    
    /**
     * 
     * @param listener
     * @return
     */
    public boolean removeListener( IDepartmentListener listener ){
    	boolean result = false;
    	if( listener != null ){
    		try{
				_lockObject.lock();
				if ( _listeners.contains(listener) ){
					result = _listeners.remove(listener);
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
    public boolean dischargeEmployee(final String employeeId, MarketPlace marketPlace){
    	boolean result = false;
    	if(employeeId!=null){
        	if( _employees.containsKey(employeeId) ){
            	try{
            		_lockObject.lock();
                	Employee currentEmployee=_employees.get(employeeId);
                	_employees.remove(employeeId);
                	currentEmployee.addListener(null);
                	currentEmployee.setSupervisor(null);
                	if(!marketPlace.dischargeEmployee(currentEmployee)){
                        for(IDepartmentListener listener : _listeners){
                        	listener.employeeDischarged(_director, currentEmployee.getSerializableEmployee());
                        }
                        
                        result = true;
                	}else{
                			_employees.put(currentEmployee.getEmployeeId(), currentEmployee);
                	}
            	}catch( SerializationException e){
            		result = false;
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
     * @param employee
     * @param employeeTask
     */
    public boolean assignTasks(final String employeeId, List<EmployeeTaskMemento> employeeTasks , Map<String, ProjectTask> projectTasks){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
        	if(employeeId!=null && employeeTasks != null ){
        		List<EmployeeTask> tasks=new  LinkedList<EmployeeTask>();
        		try {
    	    		for(EmployeeTaskMemento employeeTask : employeeTasks){
    	        		String taskName=employeeTask.getProjectTaskName();
    	    			ProjectTask projectTask=projectTasks.get(taskName);
    	    			if(_employees.containsKey(employeeId)){
        	    			EmployeeTask task=new EmployeeTask(projectTask,employeeTask.getFirstStep(),employeeTask.getLastStep());
        	    			result = tasks.add(task);
    	    			}
    	    		}
        		
    	    		_employees.get(employeeId).removeAllTasks();
    				result &= _employees.get( employeeId ).assignTasks(tasks);
    			} catch (GameException e) {
    				result = false;
    			}
        	}
    	}finally{
    		_lockObject.unlock();
    	}
    	
    	return result;
    }

    /**
     * 
     * @param marketPlace
     * @return
     */
    public boolean dischargeEmployees(MarketPlace marketPlace){
    	boolean result = false;
    	
    	if( marketPlace != null ){
        	try{
        		_lockObject.lock();
        		result = true;
        		for(Map.Entry<String, Employee> entry : _employees.entrySet()){
        			entry.getValue().removeAllTasks();
        			
        			result &= marketPlace.dischargeEmployee(entry.getValue());
        		}
        		
        		_employees.clear();
        	}finally{
        		_lockObject.unlock();
        	}	
    	}

    	return result;
    }
    
    /**
     * 
     * @param currentStep
     * @return
     */
    public boolean executeWorkingStep( int currentStep ){
    	boolean result = true;
    	
    	try{
    		_lockObject.lock();
    		for ( Map.Entry<String, Employee> entry : _employees.entrySet() ){
    			result &= entry.getValue().executeTasks(currentStep);
    		}
    	}finally{
    		_lockObject.unlock();
    	}	

    	return result;
    }
    
    /**
     * 
     * @return
     */
    public double calculateFees(){
    	double result = 0;
    	
    	try{
    		_lockObject.lock();
    		for(Map.Entry<String, Employee> entry : _employees.entrySet()){
    			result += entry.getValue().getSalary();
    		}
    	}finally{
    		_lockObject.unlock();
    	}	

    	return result;
    }
    
    /**
     * 
     * @param director
     */
    public void setDirector( String director ){
    	try{
    		_lockObject.lock();
    		_director = director;
    		for(Map.Entry<String, Employee> entry : _employees.entrySet()){
    			entry.getValue().setSupervisor(_director);
    		}
    	}finally{
    		_lockObject.unlock();
    	}	
    }
    
    /**
     * 
     * @return
     */
    public String getDirector(){
    	try{
    		_lockObject.lock();
    		return _director;
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * 
     */
    public void removeListeners(){
    	try{
    		_lockObject.lock();
    		_listeners.clear();
    		for ( Map.Entry<String, Employee> employee : _employees.entrySet()){
    			employee.getValue().removeListeners();
    		}
    	}finally{
    		_lockObject.unlock();
    	}
    }
}
