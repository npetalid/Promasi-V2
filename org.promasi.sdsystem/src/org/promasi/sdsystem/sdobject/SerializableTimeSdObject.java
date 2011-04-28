/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class SerializableTimeSdObject extends SerializableObject implements
		ISerializableSdObject {

	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.serialization.ISerializableSdObject#getSdObject()
	 */
	@Override
	public ISdObject getSdObject() throws SerializationException {
		return new TimeSdObject();
	}

}
