/**
 * 
 */
package org.promasi.utilities.clock;

import org.joda.time.DateTime;
import org.promasi.utilities.design.IObservable;

/**
 * @author alekstheod
 * Interface which represents the
 * clock.
 */
public interface IClock extends IObservable<IClockListener> {
	/**
	 * Will start the clock.
	 */
	public void start ();
	
	/**
	 * Will stop the clock.
	 */
	public void stop ();
	
	/**
	 * Will return the current time.
	 * @return instance of {@link DateTime} which
	 * represents the date current date time.
	 */
	public DateTime getCurrentDateTime ( );
	
	/**
	 * Will return the state of the clock.
	 * @return true if clock is started, false otherwise.
	 */
	public boolean isActive();
	
	/**
	 * Will set the timeout between
	 * the clock ticks.
	 * @param speed a time delay in miliseconds.
	 */
	public void setDelay ( int speed );
}
