package org.promasi.core;


import java.util.List;


/**
 * Persists the state of the {@link SdModel} for a specific step. The
 * {@link SdSystem} uses this interface to persist the state of it's model for
 * each step, so that graphical representations of the {@link SdModel} can be
 * made.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IStatePersister
{

    /**
     * Initializes the persister.
     * 
     * @param model
     *            The model to persist.
     */
    void initialize ( SdModel model );

    /**
     * Saves all the values of the model (all {@link ISdObject}s).
     * 
     * @param time
     *            The time(step) that the values was stored.
     */
    void persistModel ( double time );

    /**
     * Closes the persister. This method is called when the {@link SdSystem} has
     * finished.
     */
    void close ( );

    /**
     * Gets the value of the object for the specified time. Can return null if
     * the value for the specified object and\or time is not found.
     * 
     * @param sdObjectKey
     *            The key of the sd object.
     * @param time
     *            The time to get the value for.
     */
    Double getValue ( String sdObjectKey, double time );

    /**
     * Gets all the time steps for the specified sdObjectKey.
     * 
     * @param sdObjectKey
     *            The key of the variable to get the values for.
     */
    List<Double> getTimeSteps ( String sdObjectKey );
}
