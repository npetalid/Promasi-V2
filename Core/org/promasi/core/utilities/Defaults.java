package org.promasi.core.utilities;


import org.promasi.core.IEquation;
import org.promasi.core.sdobjects.AbstractSdObject;


/**
 * Interface that contains a variety of default values.
 * 
 * @author eddiefullmetal
 * 
 */
public interface Defaults
{
    /**
     * Defines the number of decimals to use when calculating a value. it is
     * used by the {@link AbstractSdObject} to round the values of an
     * {@link IEquation}.
     */
    int NUMBER_OF_DECIMALS = 7;
}
