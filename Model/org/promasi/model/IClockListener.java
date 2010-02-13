package org.promasi.model;


import java.util.List;

import org.joda.time.DurationFieldType;


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
     * Called when the {@link Clock} ticks.
     * 
     * @param changedTypes
     *            if the clock ticks each minute then the changed types will
     *            contain the {@link DurationFieldType#minutes()}. At the 61
     *            ticked the changedTypes will also contain the
     *            {@link DurationFieldType#hours()} etc.
     */
    void ticked ( List<DurationFieldType> changedTypes );
}
