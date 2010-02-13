package org.promasi.ui.gmtools.coredesigner;


import org.promasi.core.IEquation;


/**
 * 
 * Class responsible for building {@link IEquation}s.
 * 
 * @author eddiefullmetal
 */
public interface IEquationBuilder
{

    /**
     * 
     * Creates the {@link IEquation}.
     * 
     * @return The created {@link IEquation} or null if the equation has invalid
     *         arguments.
     */
    IEquation buildEquation ( );

    /**
     * 
     * Sets the current equation of the builder.
     * 
     * @param equation
     *            The {@link IEquation} to set as current.
     */
    void setCurrentEquation ( IEquation equation );
}
