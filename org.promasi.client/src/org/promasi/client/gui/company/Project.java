package org.promasi.client.gui.company;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.promasi.game.project.SerializableProject;
import org.promasi.game.project.SerializableProjectTask;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * 
 * @author m1cRo
 *
 */
public class Project 
{
	/**
	 * 
	 */
	private String _projectName;

	/**
	 * 
	 */
	private String _projectDescription;
	
	/**
	 * 
	 */
	private DateTime _startDate;
	
	/**
	 * 
	 */
	private DateTime _endDate;
	
	/**
	 * 
	 */
	private Map<String, SerializableProjectTask> _projectTasks;
	
	/**
	 * 
	 * @param project
	 * @param startDate
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public Project(SerializableProject project, DateTime startDate)throws NullArgumentException, IllegalArgumentException{
		if(project==null){
			throw new NullArgumentException("Wrong argument project==null");
		}
		
		if(startDate==null){
			throw new NullArgumentException("Wrong argumen tstartTime==null");
		}
		
		if(project.getName()==null){
			throw new IllegalArgumentException("Wrong argument project");
		}
		
		if(project.getProjectTasks()==null || project.getProjectTasks().isEmpty()){
			throw new IllegalArgumentException("Wrong argument project no tasks found");
		}
		
		if(project.getDescription()==null){
			throw new IllegalArgumentException("Wrong argument project invalid projectDescription");
		}
		
		_projectDescription=project.getDescription();
		_projectName=project.getName();
		_startDate=startDate;
		_endDate=startDate.plusMinutes(project.getProjectDuration());
		_projectTasks=new TreeMap<String, SerializableProjectTask>(project.getProjectTasks());
	}
	
	/**
	 * 
	 * @return
	 */
	public DateTime getStartDate(){
		return _startDate;
	}
	
	/**
	 * 
	 * @return
	 */
	public DateTime getEndDate(){
		return _endDate;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProjectName(){
		return _projectName;
	}

	/**
	 * @return the projectTasks
	 */
	public Map<String, SerializableProjectTask> getProjectTasks() {
		return _projectTasks;
	}

	/**
	 * @return the projectDescription
	 */
	public String getProjectDescription() {
		return _projectDescription;
	}
}
