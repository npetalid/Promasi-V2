package org.promasi.sdsystem.sdobject.equation;

import java.util.Map;

import org.promasi.sdsystem.serialization.ISerializableEquation;
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
	public Double calculateEquation(Map<String, Double> systemValues)throws NullArgumentException, IllegalArgumentException,CalculationExeption;
	public ISerializableEquation getSerializableEquation()throws SerializationException;
}
