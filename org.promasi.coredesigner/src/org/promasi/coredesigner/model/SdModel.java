package org.promasi.coredesigner.model;
/**
 * Represents the model and used as a host for the SdObjects
 * 
 * @author antoxron
 *
 */
public class SdModel extends SdObject {
	
	/**
	 * 
	 */
	private String _projectName;
	
	public SdModel( ) {
		setName( AbstractSdObject.EMPTY_OBJECT_NAME );
	}
	/**
	 * 
	 * @param projectName
	 */
	public void setProjectName( String projectName ) {
		_projectName = projectName;
	}
	/**
	 * 
	 * @return
	 */
	public String getProjectName( ) {
		return _projectName;
	}
	

}
