package org.promasi.coredesigner.model;

import java.util.List;
/**
 * Represents the project and used as a host for the SdModels
 * 
 * @author antoxron
 *
 */
public class SdProject {
	
	/**
	 * the project name
	 */
	private String _name;
	/**
	 * list of SdModel
	 */
	private List<SdModel> _models;
	
	
	private int _projectDuration;
	private double _projectPrice;
	private double _difficultyLevel;
	
	
	public SdProject() {
		
	}
	/**
	 * 
	 * @param name
	 */
	public void setName( String name ) {
		_name = name;
	}
	/**
	 * 
	 * @return
	 */
	public String getName( ) {
		return _name;
	}
	/**
	 * 
	 * @param models
	 */
	public void setModels( List<SdModel> models ) {
		_models = models;
	}
	/**
	 * 
	 * @return the list of SdModel
	 */
	public List<SdModel> getModels( ) {
		return _models;
	}
	
	
	public void setProjectDuration( int projectDuration ) {
		_projectDuration = projectDuration;
	}
	public int getProjectDuration( ) {
		return _projectDuration;
	}
	public void setProjectPrice( double projectPrice ) {
		_projectPrice = projectPrice;
	}
	public double getProjectPrice( ) {
		return _projectPrice;
	}
	public void setDifficultyLevel( double difficultyLevel ) {
		_difficultyLevel = difficultyLevel;
	}
	public double getDifficultyLevel( ) {
		return _difficultyLevel;
	}
	
}