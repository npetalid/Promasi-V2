package org.promasi.sdsystem.serialization;

import org.promasi.sdsystem.sdobject.equation.IEquation;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public interface ISerializableEquation {
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public IEquation getEquation()throws SerializationException;
}
