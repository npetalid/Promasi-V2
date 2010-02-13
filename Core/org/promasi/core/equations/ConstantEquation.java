package org.promasi.core.equations;


import java.io.Serializable;

import org.promasi.core.EquationType;
import org.promasi.core.IEquation;


/**
 * 
 * The constant equation returns always the value that was used to initialize
 * the equation. The class follows the javabeans specification.
 * 
 * @author eddiefullmetal
 * 
 */
public class ConstantEquation
        implements IEquation, Serializable
{

    /**
     * The value of the constant equation.
     */
    private Double _value;

    /**
     * Initializes the object.
     * 
     * @param value
     *            The {@link #_value}.
     */
    public ConstantEquation( Double value )
    {
        _value = value;
    }

    /**
     * Initializes the object.
     */
    public ConstantEquation( )
    {

    }

    @Override
    public Double calculateValue ( )
    {
        return _value;
    }

    @Override
    public EquationType getType ( )
    {
        return EquationType.Constant;
    }

    /**
     * @return The {@link #_value}.
     */
    public Double getValue ( )
    {
        return _value;
    }

    /**
     * @param value
     *            The {@link #_value} to set.
     */
    public void setValue ( Double value )
    {
        _value = value;
    }
}
