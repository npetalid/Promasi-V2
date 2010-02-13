package org.promasi.communication;


import java.util.List;
import java.util.Vector;


/**
 * 
 * Singleton class. Provides methods for the core to communicate with an upper
 * layer.
 * 
 * @author eddiefullmetal
 * 
 */
public final class Communicator
{

    /**
     * A {@link List} with all the registered {@link IMessageDispatcher}s. Those
     * dispatchers will be called when a sendValue or raiseEvent message are
     * send from the core in order to notify other components.
     */
    private List<IMessageDispatcher> _additionalDispatchers;

    /**
     * The main {@link IMessageDispatcher}. The main dispatcher is the
     * {@link IMessageDispatcher} that sends all the core messages to the upper
     * layer.
     */
    private InternalMessageDispatcher _mainDispatcher;

    /**
     * Singleton implementation.
     */
    private static Communicator INSTANCE;

    /**
     * Initializes the object.
     */
    private Communicator( )
    {
        _additionalDispatchers = new Vector<IMessageDispatcher>( );
        _mainDispatcher = new InternalMessageDispatcher( );
    }

    /**
     * Is called when an {@link org.promasi.core.ISdObject} sends it's value to
     * the upper layer. It notifies the {@link #_mainDispatcher} and all others.
     * 
     * @param sdObjectKey
     *            The {@link org.promasi.core.ISdObject}
     * @param value
     *            The value of the {@link org.promasi.core.ISdObject}.
     */
    public void sendValue ( String sdObjectKey, Double value )
    {
        _mainDispatcher.sendValue( sdObjectKey, value );
        for ( IMessageDispatcher dispatcher : _additionalDispatchers )
        {
            dispatcher.sendValue( sdObjectKey, value );
        }
    }

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
    public void raiseEvent ( String sdObjectKey, String eventName )
    {
        _mainDispatcher.raiseEvent( sdObjectKey, eventName );
        for ( IMessageDispatcher dispatcher : _additionalDispatchers )
        {
            dispatcher.raiseEvent( sdObjectKey, eventName );
        }
    }

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
    public Double requestValue ( String sdObjectKey )
    {
        return _mainDispatcher.requestValue( sdObjectKey );
    }

    /**
     * Adds an {@link IMessageDispatcher} to the {@link #_additionalDispatchers}
     * if it doesn't exist.
     * 
     * @param dispatcher
     *            The {@link IMessageDispatcher} to add.
     */
    public void addAdditionalDispatcher ( IMessageDispatcher dispatcher )
    {
        if ( !_additionalDispatchers.contains( dispatcher ) )
        {
            _additionalDispatchers.add( dispatcher );
        }
    }

    /**
     * Removes an {@link IMessageDispatcher} to the
     * {@link #_additionalDispatchers}.
     * 
     * @param dispatcher
     *            The {@link IMessageDispatcher} to remove.
     */
    public void removeAdditionalDispatcher ( IMessageDispatcher dispatcher )
    {
        if ( _additionalDispatchers.contains( dispatcher ) )
        {
            _additionalDispatchers.remove( dispatcher );
        }
    }

    /**
     *@param receiver
     *            The {@link IMessageReceiver} to set.
     */
    public void setMainReceiver ( IMessageReceiver receiver )
    {
        _mainDispatcher.setReceiver( receiver );
    }

    /**
     * @return The singleton instance.
     */
    public static Communicator getInstance ( )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new Communicator( );
        }
        return INSTANCE;
    }
}
