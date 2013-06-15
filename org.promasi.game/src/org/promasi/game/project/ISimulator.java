/**
 * 
 */
package org.promasi.game.project;

import java.util.Map;

import org.promasi.game.GameException;
import org.promasi.game.model.generated.SimulatorModel;

/**
 * @author alekstheod
 *
 */
public interface ISimulator {
	/**
	 * Will execute a single step on the
	 * simulation system.
	 * @return true if step has been 
	 * successfully executed, 
	 * false otherwise.
	 */
	boolean executeStep();
	
	/**
	 * Will set the given value to
	 * the input with the given inputName
	 * @param inputName the name of the input
	 * where the value will be applied.
	 * @param value Value to be applied.
	 * @return true if succeed, false otherwise.
	 */
	boolean setInput(final String inputName, final Double value);
	Double getValue(final String sdObjectName) throws GameException;
	Map< String, Double > getValues();
	boolean hasInput(String inputName) throws GameException;
	boolean hasOutput(String outputName) throws GameException;
	SimulatorModel getMemento();
}
