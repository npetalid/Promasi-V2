package org.promasi.sdsystem.sdobject.equation;

import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.serialization.IEquationMemento;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class CalculatedEquationMemento extends SerializableObject implements IEquationMemento{
	
	/**
	 * 
	 */
	private String _equationString;
	
	/**
	 * 
	 */
	public CalculatedEquationMemento(){
	}
	
	/**
	 * 
	 * @param equation
	 */
	public CalculatedEquationMemento(final CalculatedEquation equation){
		_equationString=equation._equationString;
	}
	
	/**
	 * 
	 * @param equationString
	 */
	public void setEquationString(final String equationString){
		_equationString=equationString;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEquationString(){
		return _equationString;
	}

	@Override
	public IEquation getEquation()throws SerializationException {
		try {
			return new CalculatedEquation(_equationString);
		} catch (SdSystemException e) {
			throw new SerializationException(e.getMessage());
		}
	}
}
