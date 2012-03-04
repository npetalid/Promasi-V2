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
public class OutputSdObjectMemento extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	private IEquationMemento _equation;
	
	/**
	 * 
	 */
	public OutputSdObjectMemento(){
		
	}
	
	/**
	 * 
	 * @param sdObject
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	protected OutputSdObjectMemento(final OutputSdObject sdObject){
		setEquation(sdObject._equation.getMemento());
	}
	
	/**
	 * @param equation the equation to set
	 */
	public void setEquation(IEquationMemento equation) {
		_equation = equation;
	}

	/**
	 * @return the equation
	 */
	public IEquationMemento getEquation() {
		return _equation;
	}

	@Override
	public ISdObject getSdObject() throws SerializationException {
		if(_equation==null){
			throw new SerializationException("Serialization failed because _equation property is null");
		}
		
		try {
			return new OutputSdObject(_equation.getEquation());
		} catch (SdSystemException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
}
