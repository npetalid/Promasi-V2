/**
 * 
 */
package org.promasi.sdsystem;

import java.util.Map;

/**
 * @author alekstheod
 * Represent the interface of
 * System Dynamics model. Needed
 * in order to perform the simulation
 * processes.
 */
public interface ISdSystem {
	/**
	 * Will execute a single step on the
	 * simulation system.
	 * @return true if step has been 
	 * successfully executed, 
	 * false otherwise.
	 */
	public boolean executeStep();
	
	/**
	 * Will set the given value to
	 * the input with the given inputName
	 * @param inputName the name of the input
	 * where the value will be applied.
	 * @param value Value to be applied.
	 * @return true if succeed, false otherwise.
	 */
	public boolean setInput(final String inputName, final Double value);
	public Double getValue(final String sdObjectName) throws SdSystemException;
	public Map< String, Double > getSystemValues();
	public boolean hasInput(String inputName) throws SdSystemException;
	public boolean hasOutput(String outputName) throws SdSystemException;
}
