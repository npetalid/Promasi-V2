package org.promasi.utilities.equation;

import java.util.Map;

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
	public Double calculateEquation(Map<String, Double> systemValues)throws CalculationExeption;
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public EquationModel getMemento();
}
