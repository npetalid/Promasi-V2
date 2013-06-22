/**
 * 
 */
package org.promasi.sdsystem.sdobject;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.SdSystemException;
import org.promasi.sdsystem.model.SdEquationModel;
import org.promasi.sdsystem.model.generated.OutputSdObjectModel;
import org.promasi.sdsystem.model.generated.SdObjectModel;
import org.promasi.utilities.equation.CalculationExeption;
import org.promasi.utilities.equation.IEquation;


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
	public OutputSdObject(final IEquation equation)throws SdSystemException{
		if(equation==null){
			throw new SdSystemException("Wrong argument equation==null");
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
	public SdObjectModel getMemento() {
		OutputSdObjectModel model = new OutputSdObjectModel();
		SdEquationModel eqModel = new SdEquationModel();
		eqModel.setEquationModel(_equation.getMemento());
		model.setEquation(eqModel);
		return model;
	}

}
