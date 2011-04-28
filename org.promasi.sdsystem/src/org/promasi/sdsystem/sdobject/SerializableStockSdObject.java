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
public class SerializableStockSdObject extends SerializableObject implements ISerializableSdObject
{
	/**
	 * 
	 */
	private ISerializableEquation _equation;
	
	/**
	 * 
	 */
	private Double _value;
	
	/**
	 * 
	 */
	public SerializableStockSdObject(){
		
	}
	
	/**
	 * 
	 * @param sdObject
	 * @throws NullArgumentException
	 * @throws SerializationException 
	 */
	public SerializableStockSdObject(final StockSdObject sdObject)throws NullArgumentException, SerializationException
	{
		if(sdObject==null){
			throw new NullArgumentException("Wrong argument sdObject==null");
		}
		
		_equation=sdObject._equation.getSerializableEquation();
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
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
	}
	
	/**
	 * 
	 * @param equation
	 */
	public void setEquation(ISerializableEquation equation){
		_equation=equation;
	}
	
	/**
	 * 
	 * @return
	 */
	public ISerializableEquation getEquation(){
		return _equation;
	}

	public void setValue(Double value) {
		_value = value;
	}

	public Double getValue() {
		return _value;
	}
	
}
