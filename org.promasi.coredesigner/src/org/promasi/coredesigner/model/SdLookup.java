package org.promasi.coredesigner.model;

import java.util.TreeMap;
/**
 * Represents the Lookup Equation
 * 
 * @author antoxron
 *
 */
public class SdLookup extends SdObject {

	/**
	 * list of lookup equation (x,y) points
	 */
	private TreeMap<Double, Double> _lookupPoints;
	
	public SdLookup( ) {
		setType( LOOKUP_OBJECT );
	}
	/**
	 * 
	 * @param lookupPoints
	 */
	public void setLookupPoints( TreeMap<Double,Double> lookupPoints ) {
		_lookupPoints = lookupPoints;
	}
	/**
	 * 
	 * @return lookupPoints
	 */
	public TreeMap<Double, Double> getLookupPoints( ) {
		return this._lookupPoints;
	}	
}