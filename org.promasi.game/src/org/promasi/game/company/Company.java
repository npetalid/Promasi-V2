package org.promasi.game.company;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.promasi.game.GameException;
import org.promasi.game.project.Project;
import org.promasi.utilities.design.Observer;
import org.promasi.utilities.serialization.SerializationException;


/**
 * 
 * Represents the company.
 * 
 * @author m1cRo
 * Represent the Company on ProMaSi system.
 * This class can accept a project in order to execute it
 * also the company has the IT Department where the
 * employees management is implemented.
 */
public class Company extends Observer<ICompanyListener>
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
     * Instance of the {@link Project} assigned
     * to the current company.
     */
    private Project _assignedProject;

    /**
     * Company's budget. Needed in order
     * to make the payments to company's employees.
     */
    protected double _budget;
    
    /**
     * Instance of the {@link DateTime} which represent
     * the last payments date.
     */
    private DateTime _lastPaymentDateTime;
    
    /**
     * Lock object needed in order to synchonize
     * the content of this class.
     */
    private Lock _lockObject;
    
    /**
     * The owner's Id.
     */
    private String _owner;
    
    /**
     * Defines the number of working days for each month.
     */
    public static final int CONST_WORKING_DAYS = 23;// TODO add this to the configuration.

    /**
     * Constructor will initialize the object.
     * @param name the company's name.
     * @param description company's description.
     * @param startTime a working day start time.
     * @param endTime a working day end time.
     * @param budget budget of the company.
     * @param prestigePoints initialization value for the prestige points
     * @throws GameException in case of invalid arguments.
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
    }

    /**
     * @return The {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }
    
    /**
     * Will return the budget of the
     * current company.
     * @return The {@link #_budget}
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
     * Will assign the {@link Project} to the
     * current company and notify the listeners about 
     * that action.
     * @param project instance of the {@link Project}
     * which will be assigned to the current company.
     * @param currentDate the system's date time.
     */
    public boolean assignProject ( Project project, DateTime currentDate )
    {
    	boolean result = false;
    	
        if( project!=null && currentDate != null ){
        	try{
        		_lockObject.lock();
        		_assignedProject=project;
                for(ICompanyListener listener : getListeners()){
                	listener.projectAssigned(_owner, getMemento(),project.getMemento(), currentDate);
                }
                
                result = true;
        	}finally{
        		_lockObject.unlock();
        	}
        }
        
        return result;
    }
    
    /**
     * Will check the company for the assigned projects.
     * @return true in case if the current
     * company has a assigned project to execution.
     */
    public boolean hasAssignedProject()
    {
    	try{
    		_lockObject.lock();
    		return (_assignedProject!=null);
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * Will return the company's working
     * day start time.
     * @return {@link #_startTime}
     */
    public LocalTime getStartWorkingTime(){
    	return _startTime;
    }
    
    /**
     * Will return the company's working
     * day end time.
     * @return {@link #_endTime}
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
     * Will execute the working step for the current company. The execution
     * of the working step will execute the project assigned to the company.
     * The working step will be executed if and only if the given current date is
     * in between the {@link #_startTime} and {@link #_endTime} dates.
     * @param dateTime the system's date time.
     * @param marketPlace in case if a company is insolvent all employees shoud be assigned to marketPlace
     * @return true if succeed, false otherwise.
     */
    public boolean executeWorkingStep(final DateTime dateTime, MarketPlace marketPlace, DateTime currentDate){
    	boolean result = false;
    	
    	if( marketPlace != null && dateTime != null && currentDate != null ){
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
                		        for(ICompanyListener listener : getListeners()){
                		        	if( _assignedProject != null ){
                    		        	listener.companyIsInsolvent(_owner, getMemento(), _assignedProject.getMemento(), currentDate);
                    		        	_assignedProject=null;
                		        	}else{
                		        		listener.companyIsInsolvent(_owner, getMemento(), null, currentDate);
                		        	}

                		        }
                		        
                		        _itDepartment.dischargeEmployees( marketPlace );
                    		}
            			}
                		
                		_lastPaymentDateTime=currentDateTime;
                	}
                	
                	if(_assignedProject!=null){
                    	 _itDepartment.executeWorkingStep(_assignedProject.getCurrentStep());
                    	 
                         for(ICompanyListener eventHandler : getListeners()){
                         	eventHandler.onExecuteWorkingStep(_owner, getMemento(), _assignedProject.getMemento(), currentDate);
                         }
                         
                         double progress = _assignedProject.executeStep(true);
                         _budget=_budget+progress*_assignedProject.getProjectPrice()/100;
                     	 if(_assignedProject.isExpired()){
                     		_prestigePoints=_prestigePoints+_assignedProject.getPrestigePoints();
                     		_itDepartment.removeAssignedTasks();
                     		
                             for(ICompanyListener listener : getListeners()){
                             	listener.projectFinished(_owner, getMemento(), _assignedProject.getMemento(), currentDate);
                             }
                              
                     		_assignedProject=null;
                     	}
                	}
            	}else if( _assignedProject != null ){
            		_assignedProject.executeStep(false);
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
    public boolean assignTasks(final String employeeId, List<EmployeeTaskMemento> employeeTasks, DateTime time){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
        	if( _assignedProject != null ){
        		result = _itDepartment.assignTasks(employeeId, employeeTasks, _assignedProject.getProjectTasks(), time );
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
    public boolean hireEmployee( Employee employee, DateTime time ){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
    		result = _itDepartment.hireEmployee(_owner, employee, time);
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
	public boolean dischargeEmployee(String employeeId, MarketPlace marketPlace, DateTime time) {
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
    		result = _itDepartment.dischargeEmployee(employeeId, marketPlace, time);
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
	public boolean addITDepartmentListener( IDepartmentListener listener ){
		return _itDepartment.addListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeITDepartmentListener( IDepartmentListener listener ){
		return _itDepartment.removeListener(listener);
	}
	
    /**
     * 
     * @param listener
     * @return
     */
    public boolean addEmploeeListener( IEmployeeListener listener ){
        return _itDepartment.addEmployeeListener(listener);
    }
    
    /**
     * 
     * @param listener
     * @return
     */
    public boolean removeEmploeeListener( IEmployeeListener listener ){
    	return _itDepartment.removeEmployeeListener(listener);
    }
	
    /**
     * 
     * @return
     * @throws SerializationException
     */
    public CompanyMemento getMemento(){
		return new CompanyMemento(this);
    }
    
    /**
     * 
     * @param owner
     */
    public void setOwner( String owner ){
    	try{
    		_lockObject.lock();
        	_owner = owner;
        	_itDepartment.setDirector(_owner);
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * 
     * @return
     */
    public String getOwner(){
    	try{
    		_lockObject.lock();
    		return _owner;
    	}finally{
    		_lockObject.unlock();
    	}
    }
}
