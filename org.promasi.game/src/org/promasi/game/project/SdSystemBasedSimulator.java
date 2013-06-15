/**
 * 
 */
package org.promasi.game.project;

import java.util.Map;

import org.promasi.game.GameException;
import org.promasi.game.model.SimulatorModel;
import org.promasi.sdsystem.SdSystem;

/**
 * @author alekstheod
 *
 */
public class SdSystemBasedSimulator implements ISimulator {
	private SdSystem _sdSystem;

	public SdSystemBasedSimulator( SdSystem sdSystem )throws GameException{
		if( sdSystem == null ){
			throw new GameException("Invalid argument sdSystem == null");
		}
		
		_sdSystem = sdSystem;
	}
	
	@Override
	public boolean executeStep() {
		return _sdSystem.executeStep();
	}

	@Override
	public boolean setInput(String inputName, Double value) {
		return _sdSystem.setInput(inputName, value);
	}

	@Override
	public Double getValue(String sdObjectName) throws GameException {
		try{
			return _sdSystem.getValue(sdObjectName);
		}catch( Exception ex ){
			throw new GameException(ex.getMessage());
		}
	}

	@Override
	public Map<String, Double> getValues() {
		return _sdSystem.getSystemValues();
	}

	@Override
	public boolean hasInput(String inputName) throws GameException {
		try{
			return _sdSystem.hasInput(inputName);
		}catch( Exception ex ){
			throw new GameException(ex.getMessage());
		}
	}

	@Override
	public boolean hasOutput(String outputName) throws GameException {
		try{
			return _sdSystem.hasOutput(outputName);
		}catch( Exception ex ){
			throw new GameException(ex.getMessage());
		}
	}

	@Override
	public SimulatorModel getMemento() {
		SdSystemBasedSimulatorModel result = new SdSystemBasedSimulatorModel();
		result.setSdSystemModel(_sdSystem.getMemento());
		return result;
	}
}
