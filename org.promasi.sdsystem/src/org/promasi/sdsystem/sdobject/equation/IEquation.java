package org.promasi.sdsystem.sdobject.equation;

import java.util.Map;

import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.serialization.IEquationMemento;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;


/**
 * 
 * @author m1cRo
 *
 */
public interface IEquation {
	/**
	 * 
	 * @param systemValues
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 * @throws CalculationExeption
	 */
	public Double calculateEquation(Map<String, Double> systemValues)throws SdSystemException;
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public IEquationMemento getMemento();
}
