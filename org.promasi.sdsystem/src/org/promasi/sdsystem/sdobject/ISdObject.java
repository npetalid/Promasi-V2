package org.promasi.sdsystem.sdobject;

import java.util.Map;

import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.serialization.SerializationException;

public interface ISdObject {
	public boolean executeStep(Map<String,ISdObject> systemSdObjects);
	public Double getValue();
	public ISerializableSdObject getSerializableSdObject()throws SerializationException;
}
