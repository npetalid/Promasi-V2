/**
 * 
 */
package org.promasi.game.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.promasi.game.model.generated.SimulatorModel;
import org.promasi.sdsystem.model.generated.SdSystemModel;

/**
 * @author alekstheod
 *
 */
@XmlRootElement(name = "SdSystemBasedSimulatorModel")
public class SdSystemBasedSimulatorModel extends SimulatorModel{
	
	@XmlElement(name = "_sdSystemModel", required = true)
	protected SdSystemModel _sdSystemModel;

	/**
	 * @return the _sdSystemModel
	 */
	public SdSystemModel getSdSystemModel() {
		return _sdSystemModel;
	}

	/**
	 * @param _sdSystemModel the _sdSystemModel to set
	 */
	public void setSdSystemModel(SdSystemModel sdSystemModel) {
		_sdSystemModel = sdSystemModel;
	}
}
