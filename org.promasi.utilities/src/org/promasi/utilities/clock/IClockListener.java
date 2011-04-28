package org.promasi.utilities.clock;

import org.joda.time.DateTime;


/**
 * 
 * Listener for the {@link Clock}.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IClockListener
{
	/**
	 * 
	 * @param dateTime
	 */
    void onTick ( final DateTime dateTime );
}
