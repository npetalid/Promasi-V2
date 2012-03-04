package org.promasi.sdsystem.sdobject;

import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.serialization.IEquationMemento;
import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.serialization.SerializableObject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public class StockSdObjectMemento extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	private IEquationMemento _equation;
	
	/**
	 * 
	 */
	private Double _value;
	
	/**
	 * 
	 */
	public StockSdObjectMemento(){
		
	}
	
	/**
	 * 
	 * @param sdObject
	 */
	protected StockSdObjectMemento(final StockSdObject sdObject)
	{
		_equation=sdObject._equation.getMemento();
		_value=sdObject._value;
	}
	
	@Override
	public ISdObject getSdObject()throws SerializationException {
		if(_equation==null){
			throw new SerializationException("Serialization failed because _equation property is null");
		}
		
		if(_value==null){
			throw new SerializationException("Serialization failed because _value property is null");
		}

		try {
			return new StockSdObject(_equation.getEquation(),_value);
		} catch (SdSystemException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
	/**
	 * 
	 * @param equation
	 */
	public void setEquation(IEquationMemento equation){
		_equation=equation;
	}
	
	/**
	 * 
	 * @return
	 */
	public IEquationMemento getEquation(){
		return _equation;
	}

	public void setValue(Double value) {
		_value = value;
	}

	public Double getValue() {
		return _value;
	}
	
}
