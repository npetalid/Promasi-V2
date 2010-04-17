/**
 *
 */
package org.promasi.communication;

/**
 * @author m1cRo
 *
 */
public interface ICommunicator
{
	 public void sendValue ( String sdObjectKey, Double value );

	    /**
	     *
	     * Is called when an {@link org.promasi.core.ISdObject} from the core raises
	     * an event. It notifies the {@link #_mainDispatcher} and all others.
	     *
	     * @param sdObjectKey
	     *            The key of the {@link org.promasi.core.ISdObject} that raised
	     *            the event.
	     *
	     * @param eventName
	     *            The name of the event.
	     */
	    public void raiseEvent ( String sdObjectKey, String eventName );

	    /**
	     * Is called when an {@link org.promasi.core.ISdObject} requests a value. It
	     * notifies the {@link #_mainDispatcher}.
	     *
	     * @param sdObjectKey
	     *            The key of the {@link org.promasi.core.ISdObject} that
	     *            requested a value.
	     *
	     * @return A value that corresponds to the sdObjectKey.
	     */
	    public Double requestValue ( String sdObjectKey );

	    /**
	     * Adds an {@link IMessageDispatcher} to the {@link #_additionalDispatchers}
	     * if it doesn't exist.
	     *
	     * @param dispatcher
	     *            The {@link IMessageDispatcher} to add.
	     */
	    public void addAdditionalDispatcher ( IMessageDispatcher dispatcher );

	    /**
	     * Removes an {@link IMessageDispatcher} to the
	     * {@link #_additionalDispatchers}.
	     *
	     * @param dispatcher
	     *            The {@link IMessageDispatcher} to remove.
	     */
	    public void removeAdditionalDispatcher ( IMessageDispatcher dispatcher );

	    /**
	     *@param receiver
	     *            The {@link IMessageReceiver} to set.
	     */
	    public void setMainReceiver ( IMessageReceiver receiver );
}
