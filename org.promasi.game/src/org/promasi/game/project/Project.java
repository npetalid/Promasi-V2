package org.promasi.game.project;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.game.GameException;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 * Represent the project on PROMASI
 * simulation model.
 */
public class Project
{
	/**
	 * The main task name.
	 */
	public static final String CONST_DEPLOY_TASK_NAME="DEPLOY";
	
    /**
     * The name of the project.
     */
    protected String _name;

    /**
     * The description of the project.
     */
    protected String _description;

    /**
     * Project duration in steps
     */
    protected int _projectDuration;

	/**
     * The difficulty level of current project.
     */
    protected double _difficultyLevel;
    
    /**
     * Overall project complete in percents.
     */
    protected double _overallProgress;
    
    /**
     * The current execution step.
     */
    private int _currentStep;
    
    /**
     * Bridges needed to link the
     * different projects tasks.
     */
    protected List<TaskBridge> _taskBridges;
    
    /**
     * Amount received after project accomplished.
     */
    protected double _projectPrice;
    
    /**
     * 
     */
    private Lock _lockObject;
    
    /**
     * The tasks for this project.
     */
    protected Map<String, ProjectTask> _projectTasks;

    /**
     * Initializes the object.
     */
    public Project(  String name, String description, int projectDuration, Map<String, ProjectTask> projectTasks, double projectPrice, double difficultyLevel)throws NullArgumentException, IllegalArgumentException
    {
    	if(name==null)
    	{
    		throw new NullArgumentException("Wrong argument name==null");
    	}
    	
    	if(description==null)
    	{
    		throw new NullArgumentException("Wrong argument description==null");
    	}
    	
    	if(projectDuration<=0)
    	{
    		throw new NullArgumentException("Wrong argument projectDuration");
    	}
    	
    	if(projectTasks==null)
    	{
    		throw new NullArgumentException("Wrong argument projectTasks==null");
    	}
    	
    	if(difficultyLevel<=0){
    		throw new IllegalArgumentException("Wrong argument difficultyLevel");
    	}
    	
    	for(Map.Entry<String, ProjectTask> entry : projectTasks.entrySet()){
    		if ( entry.getKey()==null || entry.getValue()==null ){
    			throw new IllegalArgumentException("Wrong argument projectTasks contains null");
    		}
    	}
    	
    	_overallProgress=0;
    	_projectDuration=projectDuration;
    	_name=name;
    	_description=description;
        _projectTasks = new TreeMap<String, ProjectTask>(projectTasks);
        _projectPrice=projectPrice;
        _difficultyLevel=difficultyLevel;
        _taskBridges=new LinkedList<TaskBridge>();
        _lockObject = new ReentrantLock();
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
     * @param outputTaskName
     * @param outputId
     * @param inputTaskName
     * @param inputId
     * @return
     */
    public boolean makeBridge(final String outputTaskName, final String outputId, String inputTaskName, final String inputId){
    	boolean result = true;
    	
    	try{
    		_lockObject.lock();    
    		if(outputTaskName!=null && inputTaskName != null && outputId != null && inputId != null )
	    	{
	    		result &= _projectTasks.containsKey(inputTaskName);
	    		TaskBridge bridge=new TaskBridge(outputTaskName, inputTaskName, outputId, inputId, _projectTasks);
	    		_taskBridges.add(bridge);
	    	}
    	}catch( GameException e){
    		result = false;
    	}finally{
    		_lockObject.unlock();
    	}

    	return result;
    }
    
    
    /**
     * 
     * @return
     */
    public double getProjectPrice()
    {
    	return _projectPrice;
    }
    
    /**
     * 
     * @return
     */
    public String getDescription(){
    	try{
    		_lockObject.lock();
    		return _description;
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * @return The {@link #_projectTasks}. The list is read only.
     */
    public Map<String,ProjectTask> getProjectTasks ( ){
    	try{
    		_lockObject.lock();
    		 return  new TreeMap<String,ProjectTask>(_projectTasks);
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * 
     * @param systemClock
     * @return
     * @throws NullArgumentException
     */
    public boolean executeStep(){
    	boolean result = false;
    	
    	try{
    		_lockObject.lock();
        	if( _projectDuration>_currentStep ){
            	for(Map.Entry<String, ProjectTask> entry : _projectTasks.entrySet()){
            		System.out.print(String.format("\n\nExecuting ProjectTask : %s\n", entry.getKey()) );
            	}
            	
            	for(Map.Entry<String, ProjectTask> entry : _projectTasks.entrySet()){
            		entry.getValue().executeBridges();
            	}

            	ProjectTask overallProject=_projectTasks.get(CONST_DEPLOY_TASK_NAME);
            	_overallProgress=overallProject.getProgress();
            	_currentStep++;	
            	result = true;
        	}
    	}finally{
    		_lockObject.unlock();
    	}

    	return result;
    }
    
    public boolean hasTask( String taskName ){
    	try{
    		_lockObject.lock();
    		return _projectTasks.containsKey(taskName);
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * 
     * @return
     * @throws SerializationException
     */
    public ProjectMemento getMemento(){
    	try {
    		_lockObject.lock();
			return new ProjectMemento(this);
		}finally{
			_lockObject.unlock();
		}
    }
    
    /**
     * 
     * @return
     */
    public double getPrestigePoints(){
    	try{
    		_lockObject.lock();
    		return _difficultyLevel*_overallProgress/100.0d;
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * 
     * @return
     */
    public boolean isExpired(){
    	ProjectTask mainTask=_projectTasks.get(CONST_DEPLOY_TASK_NAME);
    	try {
    		_lockObject.lock();
			if(mainTask.getProgress()>=100){
				return true;
			}
			
	    	if(_currentStep>=_projectDuration){
	    		return true;
	    	}
	    	
		} finally{
			_lockObject.unlock();
		}

    	return false;
    }
    
    /**
     * 
     * @return
     */
    public int getCurrentStep(){
    	try{
    		_lockObject.lock();
    		return _currentStep;
    	}finally{
    		_lockObject.unlock();
    	}
    }
    
    /**
     * 
     * @return
     */
    public int getProjectDuration() {
		return _projectDuration;
	}
}
