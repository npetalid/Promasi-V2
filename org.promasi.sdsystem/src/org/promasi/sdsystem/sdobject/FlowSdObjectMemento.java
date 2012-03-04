package org.promasi.sdsystem.sdobject;

import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.serialization.IEquationMemento;
import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class FlowSdObjectMemento extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	private IEquationMemento _equation;
	
	/**
	 * 
	 */
	public FlowSdObjectMemento(){
		
	}
	
	/**
	 * 
	 * @param sdObject
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	public FlowSdObjectMemento(final FlowSdObject sdObject){
		_equation=sdObject._equation.getMemento();
	}
	
	/**
	 * 
	 * @return
	 */
	public IEquationMemento getEquation(){
		return _equation;
	}
	
	/**
	 * 
	 * @param equation
	 */
	public void setEquation(IEquationMemento equation){
		_equation=equation;
	}
	
	@Override
	public ISdObject getSdObject() throws SerializationException {
		if(_equation==null){
			throw new SerializationException("Serialization failed because _equation property is null");
		}

		try {
			return new FlowSdObject(_equation.getEquation());
		} catch (SdSystemException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
}
