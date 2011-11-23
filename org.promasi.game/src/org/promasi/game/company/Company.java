package org.promasi.game.company;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.promasi.game.GameException;
import org.promasi.game.project.Project;
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
     * 
     */
    protected Department _itDepartment;

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
     */
    public Company( String name, String description, LocalTime startTime, LocalTime endTime, double budget, double prestigePoints)throws GameException
    {
    	if(name==null)
    	{
    		throw new GameException("Wrong argument name==null");
    	}
    	
    	if(description==null)
    	{
    		throw new GameException("Wrong argumen description==null");
    	}
    	
    	if(startTime==null)
    	{
    		throw new GameException("Wrong argument startTime==null");
    	}
    	
    	if(endTime==null)
    	{
    		throw new GameException("Wrong argument endTime");
    	}
    	
    	if(startTime.equals(endTime) || startTime.isAfter(endTime))
    	{
    		throw new GameException("Wrong argument startTime is equal or after endTime");
    	}
    	
    	if(budget<=0)
    	{
    		throw new GameException("Wrong argument budget<=0");
    	}
    	
    	_lockObject = new ReentrantLock();
        _prestigePoints = prestigePoints;
        _name = name;
		_startTime=startTime;
		_endTime=endTime;
		_description=description;
		_budget=budget;
		_itDepartment = new Department();
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
            			_budget=_budget-_itDepartment.calculateFees();
            			if(_budget<0){
            				if(_assignedProject!=null){
            					_prestigePoints=_prestigePoints-_assignedProject.getPrestigePoints();
            				}
            				
                    		if(_budget<0){
                		        for(ICompanyListener listener : _companyListeners){
                		        	listener.companyIsInsolvent(getSerializableCompany(), _assignedProject.getSerializableProject());
                		        	_assignedProject=null;
                		        }
                		        
                		        _itDepartment.dischargeEmployees( marketPlace );
                    		}
            			}
                		
                		_lastPaymentDateTime=currentDateTime;
                	}
                	
                	if(_assignedProject!=null){
                    	 _itDepartment.executeWorkingStep(_assignedProject.getCurrentStep());
                    	 
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
        	if( _assignedProject != null ){
        		result = _itDepartment.assignTasks(employeeId, employeeTasks, _assignedProject.getProjectTasks() );
        	}
    	}finally{
    		_lockObject.unlock();
    	}

    	return result;
    }
    
    /**
     * 
     * @param employee
     * @return
     */
    public boolean hireEmployee( Employee employee ){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
    		result = _itDepartment.hireEmployee(employee);
    	}catch(SerializationException e){
    		result = false;
    	}finally{
    		_lockObject.unlock();
    	}
    	
    	return result;
    }
    
    /**
     * 
     * @param employeeId
     * @param _marketPlace
     * @return
     */
	public boolean dischargeEmployee(String employeeId, MarketPlace marketPlace) {
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
    		result = _itDepartment.dischargeEmployee(employeeId, marketPlace);
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
		return new SerializableCompany(this);
    }
    
	@Override
	public void taskAttached(SerializableEmployee employee, List<SerializableEmployeeTask> employeeTasks) throws SerializationException {
        for(ICompanyListener eventHandler : _companyListeners){
        	eventHandler.employeeTasksAttached(getSerializableCompany(), employee, employeeTasks);
        }
	}

	@Override
	public void taskDetached(SerializableEmployee employee, SerializableEmployeeTask employeeTask) throws SerializationException {
        for(ICompanyListener eventHandler : _companyListeners){
        	eventHandler.employeeTaskDetached(getSerializableCompany(),employee, employeeTask);
        }
	}

	
}
