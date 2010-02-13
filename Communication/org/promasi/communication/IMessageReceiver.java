package org.promasi.communication;


/**
 * Represents a message receiver. It provides methods to handle requests from
 * the core layer.
 * 
 * @author eddiefullmetal
 * 
 */
public interface IMessageReceiver
{
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
    void eventRaised ( String sdObjectKey, String eventName );

    /**
     * Is called when an {@link org.promasi.core.ISdObject} requests a value.
     * 
     * @param sdObjectKey
     *            The key of the {@link org.promasi.core.ISdObject} that
     *            requested a value.
     * 
     * @return A value that corresponds to the sdObjectKey.
     */
    Double valueRequested ( String sdObjectKey );

    /**
     * Is called when an {@link org.promasi.core.ISdObject} sends it's value to
     * the upper layer.
     * 
     * @param sdObjectKey
     *            The {@link org.promasi.core.ISdObject}
     * @param value
     *            The value of the {@link org.promasi.core.ISdObject}.
     */
    void valueSent ( String sdObjectKey, Double value );

}
