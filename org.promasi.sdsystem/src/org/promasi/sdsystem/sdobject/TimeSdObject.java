/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import java.util.Map;

import org.promasi.sdsystem.model.generated.TimeSdObjectModel;


/**
 * @author m1cRo
 *
 */
public class TimeSdObject implements ISdObject
{
	/**
	 * 
	 */
	private double _value=0.0;
	
	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.sdobject.ISdObject#executeStep(java.util.Map)
	 */
	@Override
	public boolean executeStep(Map<String, ISdObject> systemSdObjects) {
		_value+=1;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.sdobject.ISdObject#getValue()
	 */
	@Override
	public Double getValue() {
		return _value;
	}

	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.sdobject.ISdObject#getSerializableSdObject()
	 */
	@Override
	public TimeSdObjectModel getMemento() {
		return new TimeSdObjectModel();
	}

}
