package org.promasi.coredesigner.model;
/**
 * Represents the Output SdObject
 * 
 * @author antoxron
 *
 */
public class SdOutput extends SdObject {

	/**
	 * the type of equation (Calculate , Lookup)
	 */
	private String _equation;
	
	public SdOutput( ) {
		setType( OUTPUT_OBJECT );
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
