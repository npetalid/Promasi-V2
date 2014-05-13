package org.promasi.coredesigner.model;
/**
 * Represents the Calculate Equation
 * 
 * @author antoxron
 *
 */
public class SdCalculate extends SdObject{

	/**
	 * 
	 */
	private String _calculateString;

	public SdCalculate( ) {
		setType( CALCULATE_OBJECT );
	}
	/**
	 * 
	 * @param calculateString
	 */
	public void setCalculateString( String calculateString ) {
		this._calculateString = calculateString;
	}
	/**
	 * 
	 * @return calculate string
	 */
	public String getCalculateString( ) {
		return this._calculateString;
	}
}
