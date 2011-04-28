package org.promasi.game.project;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.SdSystem;
import org.promasi.sdsystem.SdSystemBridge;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;


public class ProjectTask
{
	/**
	 * 
	 */
	public static final double CONST_PERCENTAGE_COMPLETE_MAX_VALUE=100.0;
	
	/**
	 * 
	 */
	public static final String CONST_SUM_EMPLOYEES_INPUT_SDOBJECT_NAME="EmployeesNumber";
	
	/**
	 * 
	 */
	public static final String CONST_PROGRESS_SDOBJECT_NAME="PROGRESS";
	
    /**
     * The name of the task.
     */
    protected String _name;

    /**
     * The description of the task.
     */
    protected String _description;

    /**
     * 
     */
    protected SdSystem _sdSystem;
    
    /**
     * The completed percentage.
     */
    protected double _progress;
    
    /**
     * 
     */
    protected Map<Integer, Double> _history;
    
    /**
     * 
     */
    private Map<String, Double> _employeeSkills;
    
    /**
     * 
     */
    protected List<SdSystemBridge> _sdSystemBridges;
    
    /**
     * 
     */
    private double _sumEmployees;

    /**
     * Initializes the object.
     */
    public ProjectTask(final String taskName, final String description, final SdSystem sdSystem )throws NullArgumentException, IllegalArgumentException
    {
        if(description==null)
        {
        	throw new NullArgumentException("Wrong argument description==null");
        }
        
        if(taskName==null)
        {
        	throw new NullArgumentException("Wrong argument taskName==null");
        }
        
        if(sdSystem==null){
        	throw new NullArgumentException("Wrong argument sdSystem==null"); 
        }
        
        if(!sdSystem.hasOutput(CONST_PROGRESS_SDOBJECT_NAME)){
        	throw new IllegalArgumentException("Wrong argument sdSystem does not contains output named " + CONST_PROGRESS_SDOBJECT_NAME);
        }
        
        _employeeSkills=new HashMap<String, Double>();
        _history=new TreeMap<Integer, Double>();
        _description=description;
        _name=taskName;
        _progress=0;
        _sdSystem=sdSystem;
        _sdSystemBridges=new LinkedList<SdSystemBridge>();
    }

    /**
     * 
     * @param outputSdObjectId
     * @param inputSdObjectId
     * @param inputProjectTask
     * @return
     */
    protected synchronized boolean makeBridge(final String outputSdObjectId, final String inputSdObjectId, final ProjectTask inputProjectTask)throws NullArgumentException, IllegalArgumentException{
    	if(outputSdObjectId==null){
    		throw new NullArgumentException("Wrong argument outputSdObjectId==null");
    	}
    	
    	if(inputSdObjectId==null){
    		throw new NullArgumentException("Wrong argument inputSdObjectId==null");
    	}
    	
    	if(inputProjectTask==null){
    		throw new NullArgumentException("Wrong argument inputProjectTask==null");
    	}
    	
    	SdSystemBridge bridge=new SdSystemBridge(outputSdObjectId, _sdSystem, inputSdObjectId, inputProjectTask._sdSystem);
    	_sdSystemBridges.add(bridge);
    	return true;
    }
    
    /**
     * @return The {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * @return The {@link #_description}.
     */
    public String getDescription ( )
    {
        return _description;
    }

    /**
     * @return The {@link #_progress}.
     */
    public double getProgress ( )
    {
        return _progress;
    }
    
    /**
     * 
     * @param systemClock
     * @return
     * @throws NullArgumentException
     */
    public synchronized boolean executeTask(int currentStep)throws IllegalArgumentException
    {
    	try{
        	if(_sumEmployees<=0.0){
        		_sumEmployees=1;
        	}
        	
        	if(currentStep<0 || _history.containsKey(currentStep)){
        		throw new IllegalArgumentException("Wrong arugment currentStep");
        	}
        	
        	for(Map.Entry<String, Double> entry : _employeeSkills.entrySet()){
        		_sdSystem.setInput(entry.getKey(), entry.getValue());
        	}
        		
            _sdSystem.setInput(CONST_SUM_EMPLOYEES_INPUT_SDOBJECT_NAME, _sumEmployees);
            
            _sdSystem.executeStep();
            _progress=_sdSystem.getValue(CONST_PROGRESS_SDOBJECT_NAME);
            _history.put(currentStep, _progress);
        	
        	_sdSystem.setInput(CONST_SUM_EMPLOYEES_INPUT_SDOBJECT_NAME, 0.0);
    		for(Map.Entry<String, Double> entry : _employeeSkills.entrySet()){
    			_sdSystem.setInput(entry.getKey(), 0.0);
    		}
        	
    		_employeeSkills.clear();
        	_sumEmployees=0;
    	}catch(NullArgumentException e){
    		return false;
    	}catch(IllegalArgumentException e){
    		return false;
    	}

    	return true;
    }
	
    /**
     * 
     */
    public synchronized void executeBridges(){
    	for(SdSystemBridge bridge :_sdSystemBridges){
    		bridge.executeStep();
    	}
    }
    
	/**
	 * 
	 * @return
	 * @throws SerializationException 
	 * @throws NullArgumentException 
	 */
	public SerializableProjectTask getSerializableProjectTask()throws SerializationException{
		try {
			return new SerializableProjectTask(this);
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param employeeSkills
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean applyEmployeeSkills(Map<String, Double> employeeSkills)throws NullArgumentException {
		if( employeeSkills==null){
			throw new NullArgumentException("Wrong argument employeeSkills==null");
		}
		
		for(Map.Entry<String, Double> entry : employeeSkills.entrySet()){
			if(entry.getKey()!=null && entry.getValue()!=null){
				if(_employeeSkills.containsKey(entry.getKey())){
					Double oldValue=_employeeSkills.get(entry.getKey());
					_employeeSkills.put(entry.getKey(), entry.getValue()+oldValue);
				}else{
					_employeeSkills.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		_sumEmployees+=1;
		return true;
	}
	
	/**
	 * 
	 * @param ouputId
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized Double getOutput(final String outputId)throws NullArgumentException, IllegalArgumentException{
		if(outputId==null){
			throw new NullArgumentException("Wrong argument outputId==null");
		}
		
		return _sdSystem.getValue(outputId);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getOutputs(){
		return _sdSystem.getInputs();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getInputs(){
		return _sdSystem.getInputs();
	}
	
	/**
	 * 
	 * @param outputId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean hasOutput(final String outputId)throws NullArgumentException{
		if(outputId==null){
			throw new NullArgumentException("Wrong argument outputId==null");
		}
		
		return _sdSystem.hasOutput(outputId);
	}

    /**
     * 
     * @return
     */
    public synchronized boolean isValidTask(){
            Double value;
			try {
				value = _sdSystem.getValue(CONST_PROGRESS_SDOBJECT_NAME);
	            if(value>=CONST_PERCENTAGE_COMPLETE_MAX_VALUE){
	                    return false;
	            }
			} catch (IllegalArgumentException e) {
				return false;
			} catch (NullArgumentException e) {
				return false;
			}

            return true;
    }
}
