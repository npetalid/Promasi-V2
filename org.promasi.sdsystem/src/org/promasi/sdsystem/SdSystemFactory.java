/**
 * 
 */
package org.promasi.sdsystem;

import java.util.Map;
import java.util.TreeMap;

import org.promasi.sdsystem.model.FlowSdObjectModel;
import org.promasi.sdsystem.model.InputSdObjectModel;
import org.promasi.sdsystem.model.OutputSdObjectModel;
import org.promasi.sdsystem.model.SdObjectModel;
import org.promasi.sdsystem.model.SdSystemModel;
import org.promasi.sdsystem.model.StockSdObjectModel;
import org.promasi.sdsystem.model.TimeSdObjectModel;
import org.promasi.sdsystem.sdobject.FlowSdObject;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.sdsystem.sdobject.InputSdObject;
import org.promasi.sdsystem.sdobject.OutputSdObject;
import org.promasi.sdsystem.sdobject.StockSdObject;
import org.promasi.sdsystem.sdobject.TimeSdObject;
import org.promasi.utilities.equation.CalculatedEquation;
import org.promasi.utilities.equation.CalculatedEquationModel;
import org.promasi.utilities.equation.CalculationExeption;
import org.promasi.utilities.equation.EquationModel;
import org.promasi.utilities.equation.IEquation;

/**
 * @author alekstheod
 *
 */
public class SdSystemFactory implements ISdSystemFactory {

	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.ISdSystemFactory#createSdSystem(org.promasi.sdsystem.model.SdSystemModel)
	 */
	@Override
	public ISdSystem createSdSystem( SdSystemModel model) {
		ISdSystem result = null;
		
		try {
			if( model != null){
				Map< String, ISdObject > sdObjects =new TreeMap<>();
				for( SdSystemModel.SdObjects.Entry entry : model.getSdObjects().getEntry() ){
					ISdObject sdObject = createSdObject(entry.getValue());
					if( sdObject != null ){
						sdObjects.put(entry.getKey(), sdObject);
					}
				}	

				result = new SdSystem(sdObjects);
			}
		} catch (SdSystemException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.ISdSystemFactory#createSdObject(org.promasi.sdsystem.model.SdObjectModel)
	 */
	@Override
	public ISdObject createSdObject(SdObjectModel model) {
		
		ISdObject result = null;
		
		try {
			if( model instanceof TimeSdObjectModel ){
				result = new TimeSdObject();
			}else if( model instanceof FlowSdObjectModel ){
				result = new FlowSdObject(createEquation(((FlowSdObjectModel) model).getEquation()));
			}else if( model instanceof StockSdObjectModel ){
				result = new StockSdObject(createEquation(((StockSdObjectModel) model).getEquation()), ((StockSdObjectModel) model).getValue());
			}else if( model instanceof InputSdObjectModel ){
				result = new InputSdObject();
			}else if( model instanceof OutputSdObjectModel ){
				result = new OutputSdObject( createEquation(((OutputSdObjectModel) model).getEquation()));
			}
		} catch (SdSystemException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.sdsystem.ISdSystemFactory#createEquation(org.promasi.sdsystem.model.EquationModel)
	 */
	@Override
	public IEquation createEquation(EquationModel model) {
		IEquation result = null;
		
		if( model instanceof CalculatedEquationModel ){
			try {
				result = new CalculatedEquation(((CalculatedEquationModel) model).getEquationString());
			} catch (CalculationExeption e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

}
