package org.promasi.model;


import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;


/**
 * 
 * The messaging server is responsible for sending and keeping {@link Message}s.
 * 
 * @author eddiefullmetal
 * 
 */
public class MessagingServer
{

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( MessagingServer.class );

    /**
     * All the messages.
     */
    private Hashtable<Person, List<Message>> _messages;

    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * Initializes the object.
     */
    public MessagingServer( )
    {
        _lockObject = new Object( );
        _messages = new Hashtable<Person, List<Message>>( );
    }

    /**
     * 
     * Sends a message by adding it to the {@link #_messages}.
     * 
     * @param message
     *            The {@link Message} to add.
     * @throws IllegalStateException
     *             If the message the sender or the recipient is null.
     */
    public void sendMessage ( Message message )
            throws IllegalStateException
    {
        synchronized ( _lockObject )
        {
            if ( message == null || message.getSender( ) == null || message.getRecipient( ) == null )
            {
                LOGGER.error( "Illegal message state" );
                throw new IllegalStateException( "Illegal message state" );
            }
            message.setDateSent( Clock.getInstance( ).getCurrentDateTime( ) );
            List<Message> messages = _messages.get( message.getRecipient( ) );
            if ( messages == null )
            {
                messages = new Vector<Message>( );
                _messages.put( message.getRecipient( ), messages );
            }
            messages.add( message );
        }
    }

    /**
     * @param person
     *            The {@link Person} to get the {@link Message}s for.
     * @return The {@link Message}s for the specified person.
     */
    public List<Message> getMessages ( Person person )
    {
        synchronized ( _lockObject )
        {
            List<Message> messages = _messages.get( person );
            if ( messages == null )
            {
                messages = new Vector<Message>( );
                _messages.put( person, messages );
            }
            return messages;
        }
    }
}
