/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.model.SdEquationModel;
import org.promasi.sdsystem.model.generated.SdObjectModel;
import org.promasi.sdsystem.model.generated.StockSdObjectModel;
import org.promasi.utilities.equation.CalculationExeption;
import org.promasi.utilities.equation.IEquation;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class StockSdObject implements ISdObject 
{
	/**
	 * 
	 */
	protected IEquation _equation;
	
	/**
	 * 
	 */
	protected Double _value=0.0;
	
	/**
	 * 
	 * @param equation
	 * @throws NullArgumentException
	 */
	public StockSdObject(final IEquation equation, Double value)throws SdSystemException{
		if(equation==null){
			throw new SdSystemException("Wrong argument equation==null");
		}
		
		if(value==null){
			throw new SdSystemException("Wrong argument value==null");
		}
		
		_value=value;
		_equation=equation;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.sdobject.ISdObject#executeStep(java.util.Map)
	 */
	@Override
	public boolean executeStep(Map<String, ISdObject> systemSdObjects) {
		try {
			Map<String, Double> systemValues=new TreeMap<String, Double>();
			for(Map.Entry<String, ISdObject> entry : systemSdObjects.entrySet())
			{
				systemValues.put(entry.getKey(), entry.getValue().getValue());
			}
			
			_value=_value+_equation.calculateEquation(systemValues);
		} catch (CalculationExeption e) {
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

	@Override
	public SdObjectModel getMemento() {
		StockSdObjectModel model = new StockSdObjectModel();
		SdEquationModel eqModel = new SdEquationModel();
		eqModel.setEquationModel(_equation.getMemento());
		model.setEquation(eqModel);
		return model;
	}
}
