package org.promasi.coredesigner.model;
/**
 * Represents the connection between models
 * 
 * @author antoxron
 *
 */
public class SdModelConnection {
	
	/**
	 * the output SdObject name
	 */
	private String _objectName;
	/**
	 * the model name which contains the object type SdOutput
	 */
	private String _modelName;
	
	
	public SdModelConnection( ) {
		
	}
	/**
	 * 
	 * @param name
	 */
	public void setObjectName( String name ) {
		_objectName = name;
	}
	/**
	 * 
	 * @return
	 */
	public String getObjectName( ) {
		return _objectName;
	}
	/**
	 * 
	 * @param modelName
	 */
	public void setModelName( String modelName ) {
		_modelName = modelName;
	}
	/**
	 * 
	 * @return modelName
	 */
	public String getModelName( ) {
		return _modelName;
	}
}
