package org.promasi.model;


import org.joda.time.DateTime;


/**
 * 
 * Represents a message.
 * 
 * @author eddiefullmetal
 * 
 */
public class Message
{

    /**
     * The title of the message(short description).
     */
    private String _title;

    /**
     * The actual message.
     */
    private String _body;

    /**
     * The date time that the message was sent.
     */
    private DateTime _dateSent;

    /**
     * The {@link Person} that sent the message.
     */
    private Person _sender;

    /**
     * The {@link Person} that will receive the message.
     */
    private Person _recipient;

    /**
     * Initializes the object.
     */
    public Message( )
    {

    }

    /**
     * @return The {@link #_title}.
     */
    public String getTitle ( )
    {
        return _title;
    }

    /**
     * @param title
     *            the {@link #_title} to set
     */
    public void setTitle ( String title )
    {
        _title = title;
    }

    /**
     * @return the {@link #_body}
     */
    public String getBody ( )
    {
        return _body;
    }

    /**
     * @param body
     *            the {@link #_body} to set
     */
    public void setBody ( String body )
    {
        _body = body;
    }

    /**
     * @return the {@link #_dateSent}
     */
    public DateTime getDateSent ( )
    {
        return _dateSent;
    }

    /**
     * @param dateSent
     *            the {@link #_dateSent} to set
     */
    public void setDateSent ( DateTime dateSent )
    {
        _dateSent = dateSent;
    }

    /**
     * @return the {@link #_sender}
     */
    public Person getSender ( )
    {
        return _sender;
    }

    /**
     * @param sender
     *            the {@link #_sender} to set
     */
    public void setSender ( Person sender )
    {
        _sender = sender;
    }

    /**
     * @return the {@link #_recipient}
     */
    public Person getRecipient ( )
    {
        return _recipient;
    }

    /**
     * @param recipient
     *            the {@link #_recipient} to set
     */
    public void setRecipient ( Person recipient )
    {
        _recipient = recipient;
    }
}
