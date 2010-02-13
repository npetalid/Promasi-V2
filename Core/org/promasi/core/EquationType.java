package org.promasi.core;


import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.equations.ExternalEquation;
import org.promasi.core.equations.LookupEquation;


/**
 * The equation type.
 * 
 * @author eddiefullmetal
 * 
 */
public enum EquationType
{
    /**
     * {@link CalculatedEquation}.
     */
    Calculated,
    /**
     * {@link ConstantEquation}.
     */
    Constant,
    /**
     * {@link LookupEquation}.
     */
    Lookup,
    /**
     * {@link ExternalEquation}.
     */
    External
}
