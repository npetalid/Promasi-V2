package org.promasi.sdsystem.serialization;

import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.utilities.serialization.SerializationException;

public interface ISerializableSdObject {
	public ISdObject getSdObject()throws SerializationException;
}
