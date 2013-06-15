/**
 * 
 */
package org.promasi.game.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.promasi.game.model.generated.ProgressModel;
import org.promasi.utilities.equation.EquationModel;

/**
 * @author alekstheod
 *
 */
@XmlRootElement(name = "equationProgressModel")
public class EquationProgressModel extends ProgressModel {
	private EquationModel _equationModel;

	/**
	 * @return the _equationModel
	 */
	public EquationModel getEquationModel() {
		return _equationModel;
	}

	/**
	 * @param _equationModel the _equationModel to set
	 */
	public void setEquationModel(EquationModel _equationModel) {
		this._equationModel = _equationModel;
	}
}
