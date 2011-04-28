package org.promasi.game.project;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SerializableProject extends SerializableObject
{
    /**
     * The name of the project.
     */
    private String _name;

    /**
     * The description of the project.
     */
    private String _description;

    /**
     * 
     */
    private int _projectDuration;

    /**
     * 
     */
    private double _overallProgress;
    
	/**
     * 
     */
    private double _projectPrice;
    
    /**
     * 
     */
    private double _difficultyLevel;
    
    /**
     * The tasks for this project.
     */
    private Map<String, SerializableProjectTask> _projectTasks;
    
    /**
     * 
     */
    private List<SerializableTaskBridge> _taskBridges;
	
	/**
     * 
     */
	public SerializableProject(){
		
	}
	
	/**
	 * 
	 * @param project
	 * @throws NullArgumentException
	 * @throws SerializationException 
	 */
	protected SerializableProject(Project project)throws NullArgumentException, SerializationException{
		if(project==null){
			throw new NullArgumentException("Wrong argument project==null");
		}
		
        _name=project._name;
        _description=project._description;
        _projectPrice = project._projectPrice;
        setProjectDuration(project._projectDuration);
        setDifficultyLevel(project._difficultyLevel);
        _overallProgress=project._overallProgress;
        _projectTasks=new TreeMap<String,SerializableProjectTask>();
        for(Map.Entry<String, ProjectTask> entry : project._projectTasks.entrySet()){
        	_projectTasks.put(entry.getKey(),entry.getValue().getSerializableProjectTask());
        }
        
        _taskBridges=new LinkedList<SerializableTaskBridge>();
        for(TaskBridge bridge : project._taskBridges){
        	SerializableTaskBridge sBridge=bridge.getSerializableTaskBridge();
        	_taskBridges.add(sBridge);
        }

	}

	public Project getProject()throws SerializationException{
		if(_name==null){
			throw new SerializationException("Serialization failed because _name property is null");
		}
		
		if(_description==null){
			throw new SerializationException("Serialization failed because _description property is null");
		}
		
		if(_projectDuration<=0){
			throw new SerializationException("Serialization failed because _projectDuration property is invalid");
		}
		
		if(_projectTasks==null){
			throw new SerializationException("Serialization failed because _mainTask property is null");
		}
		
		if(_difficultyLevel<=0){
			throw new SerializationException("Serialization failed because _mainTask property is invalid");
		}
		
		if(_taskBridges==null){
			throw new SerializationException("Serialization failed because _taskBridges property is null");
		}
		
		Map<String, ProjectTask> projectTasks=new TreeMap<String, ProjectTask>();
		for(Map.Entry<String, SerializableProjectTask> entry : _projectTasks.entrySet()){
			projectTasks.put(entry.getKey(), entry.getValue().getProjectTask());
		}
		
		try{
			Project project = new Project(_name,_description,_projectDuration,projectTasks,_projectPrice, _difficultyLevel);
			for(SerializableTaskBridge bridge : _taskBridges){
				if(!project.makeBridge(bridge.getOutputTaskName(),bridge.getOutputSdObjectId(), bridge.getInputTaskName(), bridge.getInputSdObjectId())){
					throw new SerializationException("Serialization failed because Project.makeBridge failed");
				}
			}
			
			return project;
		}catch(NullArgumentException e){
			throw new SerializationException("Serialization failed because : "+e.getMessage() );
		}catch(IllegalArgumentException e){
			throw new SerializationException("Serialization failed because : "+e.getMessage() );
		}
	}
	
	/**
	 * 
	 * @param projectPrice
	 */
	public void setProjectPrice(double projectPrice) {
		_projectPrice = projectPrice;
	}

	/**
	 * 
	 * @return
	 */
	public double getProjectPrice() {
		return _projectPrice;
	}
	
	/**
	 * 
	 * @return
	 */
    public String getName() {
		return _name;
	}

    /**
     * 
     * @param name
     */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		_description = description;
	}
	
	/**
	 * 
	 * @return
	 */
    public Map<String, SerializableProjectTask> getProjectTasks() {
		return _projectTasks;
	}

    /**
     * 
     * @param projectTasks
     */
	public void setProjectTasks(Map<String, SerializableProjectTask> projectTasks) {
		_projectTasks = projectTasks;
	}

	/**
	 * @param difficultyLevel the difficultyLevel to set
	 */
	public void setDifficultyLevel(double difficultyLevel) {
		_difficultyLevel = difficultyLevel;
	}

	/**
	 * @return the difficultyLevel
	 */
	public double getDifficultyLevel() {
		return _difficultyLevel;
	}

	/**
	 * @param projectDuration the projectDuration to set
	 */
	public void setProjectDuration(int projectDuration) {
		_projectDuration = projectDuration;
	}

	/**
	 * @return the projectDuration
	 */
	public int getProjectDuration() {
		return _projectDuration;
	}

	/**
	 * @param overallProgress the overallProgress to set
	 */
	public void setOverallProgress(double overallProgress) {
		_overallProgress = overallProgress;
	}

	/**
	 * @return the overallProgress
	 */
	public double getOverallProgress() {
		return _overallProgress;
	}

	/**
	 * 
	 * @return
	 */
    public List<SerializableTaskBridge> getTaskBridges() {
		return _taskBridges;
	}

    /**
     * 
     * @param taskBridges
     */
	public void setTaskBridges(List<SerializableTaskBridge> taskBridges) {
		_taskBridges = taskBridges;
	}

}