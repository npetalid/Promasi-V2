package org.promasi.core;


import org.apache.commons.math.MathException;
import org.nfunk.jep.ParseException;
import org.promasi.communication.ICommunicator;


/**
 * Represents an equation.
 *
 * @author eddiefullmetal
 *
 */
public interface IEquation
{
    /**
     * Calculates the value of the equation.
     *
     * @return The value of the equation.
     * @throws ParseException
     *             In case any parsing that is done fails.
     * @throws MathException
     *             In case an error occurs when using classes from the
     *             org.apache.commons.math
     */
    Double calculateValue ( )
            throws ParseException, MathException;

    /**
     * Returns the {@link EquationType}.
     *
     * @return The {@link EquationType}.
     */
    EquationType getType ( );

    /**
     * Will register the system communicator.
     * @param communicator
     */
    public void registerCommunicator(ICommunicator communicator);
}
