/**
 * 
 */
package org.promasi.game.project;

import org.promasi.game.model.ProgressModel;
import org.promasi.utilities.equation.EquationModel;

/**
 * @author alekstheod
 *
 */
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
