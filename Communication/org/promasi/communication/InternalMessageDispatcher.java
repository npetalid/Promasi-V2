package org.promasi.communication;


import org.apache.log4j.Logger;


/**
 * 
 * An {@link IMessageDispatcher} used as the main dispatcher in the
 * communicator. It sends messages inside the application.
 * 
 * @author eddiefullmetal
 * 
 */
public class InternalMessageDispatcher
        implements IMessageDispatcher
{

    /**
     * The {@link IMessageReceiver} that will receive the messages of the core.
     */
    private IMessageReceiver _receiver;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( InternalMessageDispatcher.class );

    @Override
    public void raiseEvent ( String sdObjectKey, String eventName )
    {
        if ( _receiver != null )
        {
            _receiver.eventRaised( sdObjectKey, eventName );
        }
        else
        {
            LOGGER.warn( "No registered receiver." );
        }
    }

    @Override
    public Double requestValue ( String sdObjectKey )
            throws NullPointerException
    {
        if ( _receiver != null )
        {
            return _receiver.valueRequested( sdObjectKey );
        }
        else
        {
            LOGGER.warn( "No registered receiver." );
            throw new NullPointerException( "No registered receiver." );
        }
    }

    @Override
    public void sendValue ( String sdObjectKey, Double value )
    {
        if ( _receiver != null )
        {
            _receiver.valueSent( sdObjectKey, value );
        }
        else
        {
            LOGGER.warn( "No registered receiver." );
        }
    }

    /**
     * @param receiver
     *            The {@link #_receiver} to set.
     */
    public void setReceiver ( IMessageReceiver receiver )
    {
        _receiver = receiver;
    }

    /**
     * @return The {@link #_receiver}.
     */
    public IMessageReceiver getReceiver ( )
    {
        return _receiver;
    }
}
