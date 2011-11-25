/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class InputSdObjectMemento extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	public InputSdObjectMemento(){
	}
	
	/**
	 * 
	 * @param sdObject
	 * @throws NullArgumentException
	 */
	public InputSdObjectMemento(InputSdObject sdObject){
	}
	
	@Override
	public ISdObject getSdObject() throws SerializationException {
		try {
			return new InputSdObject();
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}

}