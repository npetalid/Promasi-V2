/**
 * 
 */
package org.promasi.sdsystem;

import org.promasi.sdsystem.model.generated.ASdEquationModel;
import org.promasi.sdsystem.model.generated.SdObjectModel;
import org.promasi.sdsystem.model.generated.SdSystemModel;
import org.promasi.sdsystem.sdobject.ISdObject;
import org.promasi.utilities.equation.EquationModel;
import org.promasi.utilities.equation.IEquation;

/**
 * @author alekstheod
 * Abstract factory interface for SdSystem.
 */
public interface ISdSystemFactory {
	/**
	 * Will create the SdSystem object by the given model
	 * @param model Model which represents the SdSystem.
	 * @return instance of SdSystem.
	 */
	ISdSystem createSdSystem( SdSystemModel model );
	
	/***
	 * Will create a new instance of SdObject by the given model
	 * @param model Model which represents the SdObject.
	 * @return a new instance of SdObject.
	 */
	ISdObject createSdObject( SdObjectModel model );
	
	/***
	 * Will create a new instance of Equation by the given model
	 * @param model Model which represents the equation.
	 * @return a new instance of Equation.
	 */
	IEquation createEquation( ASdEquationModel model );
	
	/***
	 * Will create a new instance of Equation by the given model
	 * @param model Model which represents the equation.
	 * @return a new instance of Equation.
	 */
	IEquation createEquation( EquationModel model );
}
