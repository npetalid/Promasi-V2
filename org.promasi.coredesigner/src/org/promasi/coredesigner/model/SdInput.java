package org.promasi.coredesigner.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the Flow SdObject
 * 
 * @author antoxron
 *
 */
public class SdInput extends SdObject {

	/**
	 * the type of equation (Calculate , Lookup)
	 */
	private String _equation;
	/**
	 * Link list of objects from other models in the model , which is the specific object 
	 */
	private List<SdModelConnection> _connections;
	
	
	public SdInput( ) {
		setType( INPUT_OBJECT );
		_connections = new ArrayList<SdModelConnection>();
	}
	/**
	 * 
	 * @param equation
	 */
	public void setEquation( String equation ) {
		_equation = equation;
	}
	/**
	 * 
	 * @return the type of equation
	 */
	public String getEquation( ) {
		return _equation;
	}
	/**
	 * 
	 * @param connections
	 */
	public void setConnections( List<SdModelConnection> connections ) {
		_connections = connections;
	}
	/**
	 * 
	 * @return connections
	 */
	public List<SdModelConnection> getConnections( ) {
		return _connections;
	}

}
