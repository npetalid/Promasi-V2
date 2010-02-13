package org.promasi.shell.model.actions;


import org.apache.commons.lang.StringUtils;
import org.promasi.model.Company;
import org.promasi.model.Message;
import org.promasi.shell.Shell;


/**
 * 
 * An {@link IModelAction} that sends a mail from the boss with the specified
 * message and title.
 * 
 * @author eddiefullmetal
 * 
 */
public class BossMailModelAction
        implements IModelAction
{

    /**
     * The message to send.
     */
    private String _message;

    /**
     * The title of the message.
     */
    private String _title;

    /**
     * Initializes the object.
     * 
     */
    public BossMailModelAction( )
    {

    }

    @Override
    public void runAction ( )
    {
        Company company = Shell.getInstance( ).getCompany( );
        Message message = new Message( );
        message.setBody( _message );
        message.setTitle( _title );
        message.setRecipient( company.getProjectManager( ) );
        message.setSender( company.getBoss( ) );

        Shell.getInstance( ).sendMail( message );
    }

    @Override
    public boolean isValid ( )
    {
        return StringUtils.isBlank( _message ) || StringUtils.isBlank( _title );
    }

    /**
     * @return the {@link #_message}.
     */
    public String getMessage ( )
    {
        return _message;
    }

    /**
     * @param message
     *            the {@link #_message} to set.
     */
    public void setMessage ( String message )
    {
        _message = message;
    }

    /**
     * @return the {@link #_title}.
     */
    public String getTitle ( )
    {
        return _title;
    }

    /**
     * @param title
     *            the {@link #_title} to set.
     */
    public void setTitle ( String title )
    {
        _title = title;
    }

}
