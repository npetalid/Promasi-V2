package org.promasi.sdsystem.sdobject;

import org.promasi.sdsystem.serialization.ISerializableEquation;
import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class SerializableOutputSdObject extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	private ISerializableEquation _equation;
	
	/**
	 * 
	 */
	public SerializableOutputSdObject(){
		
	}
	
	/**
	 * 
	 * @param sdObject
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	public SerializableOutputSdObject(final OutputSdObject sdObject)throws NullArgumentException, SerializationException{
		if(sdObject==null){
			throw new NullArgumentException("Wrong argument sdObject==null");
		}

		setEquation(sdObject._equation.getSerializableEquation());
	}
	
	/**
	 * @param equation the equation to set
	 */
	public void setEquation(ISerializableEquation equation) {
		_equation = equation;
	}

	/**
	 * @return the equation
	 */
	public ISerializableEquation getEquation() {
		return _equation;
	}

	@Override
	public ISdObject getSdObject() throws SerializationException {
		if(_equation==null){
			throw new SerializationException("Serialization failed because _equation property is null");
		}
		
		try {
			return new OutputSdObject(_equation.getEquation());
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
}
