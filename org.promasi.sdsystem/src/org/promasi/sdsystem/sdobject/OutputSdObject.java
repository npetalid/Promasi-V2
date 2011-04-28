/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.sdobject.equation.CalculationExeption;

import org.promasi.sdsystem.sdobject.equation.IEquation;
import org.promasi.sdsystem.serialization.ISerializableSdObject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;


/**
 * @author m1cRo
 *
 */
public class OutputSdObject implements ISdObject 
{
	/**
	 * 
	 */
	protected Double _value;
	
	/**
	 * 
	 */
	protected IEquation _equation;
	
	/**
	 * 
	 * @param equation
	 * @throws NullArgumentException
	 */
	public OutputSdObject(final IEquation equation)throws NullArgumentException{
		if(equation==null){
			throw new NullArgumentException("Wrong argument equation==null");
		}
		
		_value=0.0;
		_equation=equation;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.sdobject.ISdObject#executeStep(java.util.Map)
	 */
	@Override
	public boolean executeStep(Map<String, ISdObject> systemSdObjects) {
		try{
			Map<String, Double> systemValues=new TreeMap<String, Double>();
			for(Map.Entry<String, ISdObject> entry : systemSdObjects.entrySet()){
				systemValues.put(entry.getKey(), entry.getValue().getValue());
			}
			
			_value=_equation.calculateEquation(systemValues);		
		}catch(CalculationExeption e){
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (NullArgumentException e) {
			return false;
		}
		
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
	public ISerializableSdObject getSerializableSdObject()throws SerializationException {
		try{
			return new SerializableOutputSdObject(this);
		}catch(NullArgumentException e){
			throw new SerializationException("Serialization failed because "  +  e.getMessage() );
		}
		
	}

}
