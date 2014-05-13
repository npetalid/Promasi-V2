package org.promasi.coredesigner.model;

/**
 * Represents the dependency
 * 
 * @author antoxron
 *
 */
public class Dependency {


	/**
	 * name of SdObject
	 */
	private String _name;
	/**
	 * type of SdObject (Flow, Stock , Variable etc).
	 */
	private String _type;
	/**
	 * the model name which is the SdObject
	 */
	private String _modelName;
	
	
	public Dependency( ) {
		
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
	 * @return name
	 */
	public String getName( ) {
		return _name;
	}
	/**
	 * 
	 * @param type
	 */
	public void setType( String type ) {
		_type = type;
	}
	/**
	 * 
	 * @return type
	 */
	public String getType( ) {
		return _type;
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
	 * @return ModelName
	 */
	public String getModelName( ) {
		return _modelName;
	}
}