package org.promasi.core;


/**
 * 
 * Basic interface representing a Computational sequence. The computational
 * sequence is responsible for calculating the values of all the
 * {@link ISdObject}s of the system.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IComputationalSequence
{

    /**
     * Initializes the sequence.
     * 
     * @param model
     *            The {@link SdModel} that the computation sequence will
     *            calculate the values for.
     */
    void initialize ( SdModel model );

    /**
     * Computes the value of all the {@link ISdObject}s of the system.
     */
    void computeValues ( );
}
