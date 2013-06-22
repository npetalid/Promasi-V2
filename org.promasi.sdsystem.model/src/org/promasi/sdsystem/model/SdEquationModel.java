/**
 * 
 */
package org.promasi.sdsystem.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.promasi.sdsystem.model.generated.ASdEquationModel;
import org.promasi.utilities.equation.EquationModel;

/**
 * @author alekstheod
 *
 */
@XmlRootElement(name = "sdEquationModel")
public class SdEquationModel extends ASdEquationModel {
	@XmlElement(name = "_equationModel")
	protected EquationModel _equationModel;
	
	public EquationModel getEquationModel()
	{
		return _equationModel;
	}
	
	public void setEquationModel(EquationModel model )
	{
		_equationModel = model;
	}
}
