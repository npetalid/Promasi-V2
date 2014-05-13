package org.promasi.coredesigner.model;
/**
 * Represents the Stock SdObject
 * 
 * @author antoxron
 *
 */
public class SdStock extends SdObject{
	
	/**
	 * the type of equation (Calculate , Lookup)
	 */
	private String _equation;
	/**
	 * 
	 */
	private double _initialValue;
	
	public SdStock() {
		setType( STOCK_OBJECT );
		_initialValue = 0.0;
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
	 * @param initialValue
	 */
	public void setInitialValue( double initialValue ) {
		_initialValue = initialValue;
	}
	/**
	 * 
	 * @return the initial value
	 */
	public double getInitialValue( ) {
		return _initialValue;
	}
}