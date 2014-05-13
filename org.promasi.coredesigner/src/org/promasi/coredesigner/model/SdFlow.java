package org.promasi.coredesigner.model;
/**
 * Represents the Flow SdObject
 * 
 * @author antoxron
 *
 */
public class SdFlow extends SdObject {
	
	/**
	 * the type of equation (Calculate , Lookup)
	 */
	private String _equation;
	
	public SdFlow( ) {
		setType( FLOW_OBJECT );
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
}