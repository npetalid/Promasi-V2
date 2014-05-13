package org.promasi.coredesigner.model;
/**
 * Represents the Variable SdObject
 * 
 * @author antoxron
 *
 */
public class SdVariable extends SdObject{
	
	/**
	 * the type of equation (Calculate , Lookup)
	 */
	private String _equation;
	
	public SdVariable( ) {
		setType( VARIABLE_OBJECT );
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