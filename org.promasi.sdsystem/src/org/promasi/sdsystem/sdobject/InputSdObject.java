/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import java.util.Map;

import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class InputSdObject implements ISdObject
{
	/**
	 * 
	 */
	private Double _value=0.0;
	
	/**
	 * 
	 */
	public InputSdObject()throws NullArgumentException{
	}
	
	@Override
	public boolean executeStep(Map<String, ISdObject> systemSdObjects) {
		return true;
	}

	@Override
	public Double getValue() {
		return _value;
	}
	
	/**
	 * 
	 * @param value
	 * @throws NullArgumentException
	 */
	public boolean setValue(final Double value){
		if(value!=null){
			_value=value;
		}
		
		return true;
	}

	@Override
	public ISerializableSdObject getSerializableSdObject()throws SerializationException {
		try {
			return new SerializableInputSdObject(this);
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
}
