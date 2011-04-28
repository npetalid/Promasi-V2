package org.promasi.game.project;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.SerializableSdSystem;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SerializableProjectTask extends SerializableObject
{
    /**
     * The name of the task.
     */
    private String _name;

    /**
     * The description of the task.
     */
    private String _description;
	
    /**
     * 
     */
    private double _progress;
    
    /**
     * 
     */
    private Map<Integer,Double> _history;
    
    /**
     * 
     */
    private SerializableSdSystem _sdSystem;
    
    /**
     * 
     */
	public SerializableProjectTask(){
	}
	
	/**
	 * 
	 * @param projectTask
	 * @throws NullArgumentException
	 * @throws SerializationException 
	 */
	protected SerializableProjectTask(final ProjectTask projectTask)throws NullArgumentException, SerializationException{
		if(projectTask==null){
			throw new NullArgumentException("Wrong argument projectTask==null");
		}
		
		_sdSystem=projectTask._sdSystem.getSerializableSdSystem();
		_name=projectTask._name;
		_description=projectTask._description;
		setHistory(new TreeMap<Integer, Double>(projectTask._history));
		setProgress(projectTask._progress);
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public ProjectTask getProjectTask()throws SerializationException{
		if(_name==null){
			throw new SerializationException("Serialization failed because property _name is null");
		}
		
		if(_description==null){
			throw new SerializationException("Serialization failed because property _description is null");
		}
		
		if(_sdSystem==null){
			throw new SerializationException("Serialization failed because property _sdSystem is null");
		}
		
		try{
			return new ProjectTask(_name, _description, _sdSystem.getSdSystem());
		}catch(NullArgumentException e){
			throw new SerializationException("Serialization failed because :"+e.getMessage());
		}catch(IllegalArgumentException e){
			throw new SerializationException("Serialization failed because :"+e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name){
		_name=name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName(){
		return _name;
	}
	
	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description){ 
		_description=description;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescription(){
		return _description;
	}

	/**
	 * @param sdSystem the sdSystem to set
	 */
	public void setSdSystem(SerializableSdSystem sdSystem) {
		_sdSystem = sdSystem;
	}

	/**
	 * @return the sdSystem
	 */
	public SerializableSdSystem getSdSystem() {
		return _sdSystem;
	}
	
	/**
	 * @param progress the progress to set
	 */
	public void setProgress(double progress) {
		_progress = progress;
	}

	/**
	 * @return the progress
	 */
	public double getProgress() {
		return _progress;
	}

	/**
	 * @param _history the _history to set
	 */
	public void setHistory(Map<Integer,Double> _history) {
		this._history = _history;
	}

	/**
	 * @return the _history
	 */
	public Map<Integer,Double> geHistory() {
		return _history;
	}
}