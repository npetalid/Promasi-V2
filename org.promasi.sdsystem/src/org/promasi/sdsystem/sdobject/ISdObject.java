package org.promasi.sdsystem.sdobject;

import java.util.Map;

import org.promasi.sdsystem.model.generated.SdObjectModel;


public interface ISdObject {
	public boolean executeStep(Map<String,ISdObject> systemSdObjects);
	public Double getValue();
	public SdObjectModel getMemento();
}
