package org.promasi.ui.promasiui.promasidesktop.programs.evolutionbird;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.promasi.ui.promasiui.promasidesktop.Message;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * The button for the {@link EvolutionBirdProgram}. Notifies when a new message
 * comes in.
 * 
 * @author eddiefullmetal
 * 
 */
public class EvolutionBirdButton extends JButton implements Runnable
{

    /**
     * The {@link EvolutionBirdProgram} that is related with this button.
     */
    private EvolutionBirdProgram _parentProgram;

    /**
     * Thread that checks for unread messages.
     */
    private Thread _messageThread;

    /**
     * Indicates if the button must stop checking for new mails.
     */
    private volatile boolean _stop;

    /**
     * The name of the {@link #_messageThread}.
     */
    private static final String THREAD_NAME = "MailNotifier";

    /**
     * The milliseconds for the thread to wait.
     */
    private static final int THREAD_SLEEP_TIME_MILLS = 1000;

    /**
     * Keeps for which messages the user got a notification.
     */
    private List<Message> _messagesNotified;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( EvolutionBirdButton.class );

    /**
     * Initializes the object.
     * 
     * @param parentProgram
     *            The {@link #_parentProgram}.
     * 
     */
    public EvolutionBirdButton( EvolutionBirdProgram parentProgram )
    {
        _parentProgram = parentProgram;
        _messagesNotified = new Vector<Message>( );
        setIcon( parentProgram.getIcon( ) );
        setToolTipText( parentProgram.getExtendedName( ) );
    }

    /**
     * @return the {@link #_parentProgram}.
     */
    public EvolutionBirdProgram getParentProgram ( )
    {
        return _parentProgram;
    }

    /**
     * Starts checking for messages.
     */
    public void start ( )
    {
        _stop = false;
        _messageThread = new Thread( this, THREAD_NAME );
        _messageThread.start( );
    }

    /**
     * Stops checking for messages.
     */
    public void stop ( )
    {
        _stop = true;
    }

    @Override
    public void run ( )
    {
        while ( !_stop )
        {
            try
            {
                _parentProgram.readMessages( );
                List<Message> unreadMessages = _parentProgram.getUnreadMessages( );
                final String notificationMessage = ResourceManager.getString( EvolutionBirdButton.class, "notificationText", "before" ) + " "
                        + +unreadMessages.size( ) + " " + ResourceManager.getString( EvolutionBirdButton.class, "notificationText", "after" );
                boolean show = false;
                for ( Message message : unreadMessages )
                {
                    if ( !_messagesNotified.contains( message ) )
                    {
                        _messagesNotified.add( message );
                        show = true;
                    }
                }
                if ( show )
                {
                    EventQueue.invokeLater( new Runnable( )
                    {

                        @Override
                        public void run ( )
                        {
                            showNotificationPopup( notificationMessage );
                        }

                    } );
                }
                Thread.sleep( THREAD_SLEEP_TIME_MILLS );
            }
            catch ( InterruptedException e )
            {
                LOGGER.warn( THREAD_NAME + " Interrupted.", e );
            }
        }
    }

    /**
     * Shows the notification pop up.
     * 
     * @param message
     *            The message to display.
     */
    private void showNotificationPopup ( String message )
    {
        final JPopupMenu menu = new JPopupMenu( );
        JLabel label = new JLabel( message, getIcon( ), SwingConstants.CENTER );
        label.addMouseListener( new MouseAdapter( )
        {

            @Override
            public void mouseClicked ( MouseEvent e )
            {
                fireActionPerformed( new ActionEvent( this, 0, StringUtils.EMPTY ) );
                menu.setVisible( false );
            }

        } );
        menu.add( label );
        menu.show( this, 0, getHeight( ) );
    }
}
