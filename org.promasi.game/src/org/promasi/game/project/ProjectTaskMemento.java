package org.promasi.game.project;

import org.promasi.game.GameException;
import org.promasi.sdsystem.SdSystemMemento;
import org.promasi.sdsystem.serialization.IEquationMemento;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class ProjectTaskMemento extends SerializableObject
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
    private IEquationMemento _progressEquation;
    
    /**
     * 
     */
    private SdSystemMemento _sdSystem;
    
    /**
     * 
     */
	public ProjectTaskMemento(){
	}
	
	/**
	 * 
	 * @param projectTask
	 * @throws SerializationException 
	 */
	protected ProjectTaskMemento(ProjectTask projectTask){
		_sdSystem=projectTask._sdSystem.getMemento();
		_name=projectTask._name;
		_progressEquation=projectTask._progressEquation.getMemento();
		_description=projectTask._description;
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
		
		if(_progressEquation==null){
			throw new SerializationException("Serialization failed because property SerializationException is null");
		}
		
		try{
			return new ProjectTask(_name, _description, _sdSystem.getSdSystem(), _progressEquation.getEquation());
		}catch(GameException e){
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
	public void setSdSystem(SdSystemMemento sdSystem) {
		_sdSystem = sdSystem;
	}

	/**
	 * @return the sdSystem
	 */
	public SdSystemMemento getSdSystem() {
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
	 * 
	 * @return
	 */
	public IEquationMemento getProgressEquation(){
		return _progressEquation;
	}
	
	/**
	 * 
	 * @param progressEquation
	 */
	public void setProgressEquation( IEquationMemento progressEquation ){
		_progressEquation = progressEquation;
	}
}