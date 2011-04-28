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
public class SerializableFlowSdObject extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	private ISerializableEquation _equation;
	
	/**
	 * 
	 */
	public SerializableFlowSdObject(){
		
	}
	
	/**
	 * 
	 * @param sdObject
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	public SerializableFlowSdObject(final FlowSdObject sdObject)throws NullArgumentException, SerializationException{
		if(sdObject==null){
			throw new NullArgumentException("Wrong argument sdObject==null");
		}

		_equation=sdObject._equation.getSerializableEquation();
	}
	
	/**
	 * 
	 * @return
	 */
	public ISerializableEquation getEquation(){
		return _equation;
	}
	
	/**
	 * 
	 * @param equation
	 */
	public void setEquation(ISerializableEquation equation){
		_equation=equation;
	}
	
	@Override
	public ISdObject getSdObject() throws SerializationException {
		if(_equation==null){
			throw new SerializationException("Serialization failed because _equation property is null");
		}

		try {
			return new FlowSdObject(_equation.getEquation());
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
}
