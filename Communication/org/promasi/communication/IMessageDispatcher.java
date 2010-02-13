package org.promasi.communication;


/**
 * 
 * Represents a message dispatcher. It sends core messages to an
 * {@link IMessageReceiver}.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IMessageDispatcher
{

    /**
     * Is called when an {@link org.promasi.core.ISdObject} sends it's value to
     * the upper layer.
     * 
     * @param sdObjectKey
     *            The {@link org.promasi.core.ISdObject}
     * @param value
     *            The value of the {@link org.promasi.core.ISdObject}.
     */
    void sendValue ( String sdObjectKey, Double value );

    /**
     * 
     * Is called when an {@link org.promasi.core.ISdObject} from the core raises
     * an event.
     * 
     * @param sdObjectKey
     *            The key of the {@link org.promasi.core.ISdObject} that raised
     *            the event.
     * 
     * @param eventName
     *            The name of the event.
     */
    void raiseEvent ( String sdObjectKey, String eventName );

    /**
     * Is called when an {@link org.promasi.core.ISdObject} requests a value.
     * 
     * @param sdObjectKey
     *            The key of the {@link org.promasi.core.ISdObject} that
     *            requested a value.
     * 
     * @return A value that corresponds to the sdObjectKey.
     */
    Double requestValue ( String sdObjectKey );
}
