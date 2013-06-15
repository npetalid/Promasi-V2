/**
 * 
 */
package org.promasi.game.project;

import org.promasi.game.model.SimulatorModel;
import org.promasi.sdsystem.model.SdSystemModel;

/**
 * @author alekstheod
 *
 */
public class SdSystemBasedSimulatorModel extends SimulatorModel{
	private SdSystemModel _sdSystemModel;

	/**
	 * @return the _sdSystemModel
	 */
	public SdSystemModel getSdSystemModel() {
		return _sdSystemModel;
	}

	/**
	 * @param _sdSystemModel the _sdSystemModel to set
	 */
	public void setSdSystemModel(SdSystemModel _sdSystemModel) {
		this._sdSystemModel = _sdSystemModel;
	}
}
