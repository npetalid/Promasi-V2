package org.promasi.ui.promasiui.promasidesktop.programs.evolutionbird;


import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.model.Message;
import org.promasi.model.MessagingServer;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 *
 * The EvolutionBird is a program for reading messages from the
 * {@link MessagingServer}.
 *
 * @author eddiefullmetal
 *
 */
public class EvolutionBirdProgram
        extends AbstractProgram
{

    /**
     * The table that shows all the messages.
     */
    private JTable _messageTable;

    /**
     * The editor pane is used to display the body of the message.
     */
    private JEditorPane _editorPane;

    /**
     * The button for this program.
     */
    private EvolutionBirdButton _button;

    /**
     * All the messages that are read.
     */
    private List<Message> _readMessages;

    private Shell _shell;

    /**
     * Initializes the object.
     */
    public EvolutionBirdProgram(Shell shell )throws NullArgumentException
    {
    	 super( "evolutionBird", "Evolution bird, mail client" );
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	_shell=shell;
        _readMessages = new Vector<Message>( );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        _messageTable = new JTable( );
        _messageTable.setModel( new MessageTableModel( new Vector<Message>( ) ) );
        _messageTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( )
        {

            @Override
            public void valueChanged ( ListSelectionEvent e )
            {
                selectionChanged( );
            }

        } );
        _editorPane = new JEditorPane( );
        _editorPane.setEditable( false );
        _editorPane.setContentType( "text/html" );
        _button = new EvolutionBirdButton( this );
        _button.start( );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
        splitPane.setTopComponent( new JScrollPane( _messageTable ) );
        splitPane.setBottomComponent( new JScrollPane( _editorPane ) );
        splitPane.setOneTouchExpandable( true );
        add( splitPane, new CC( ).grow( ) );
    }

    /**
     * Displays the messages from the {@link MessagingServer}.
     */
    public void readMessages ( )
    {
        Company company = _shell.getCompany( );
        MessagingServer messagingServer = company.getMessagingServer( );
        List<Message> messages = messagingServer.getMessages( company.getProjectManager( ) );
        _messageTable.setModel( new MessageTableModel( messages ) );
    }

    /**
     * Called when the selection of the message table changes.
     */
    private void selectionChanged ( )
    {
        Message message = ( (MessageTableModel) _messageTable.getModel( ) ).getMessage( _messageTable.getSelectedRow( ) );
        if ( message != null )
        {
            _editorPane.setText( message.getBody( ) );
            _readMessages.add( message );
        }
        else
        {
            _editorPane.setText( "" );
        }
    }

    /**
     * @return A {@link List} with all the unread messages.
     */
    public List<Message> getUnreadMessages ( )
    {
        List<Message> unreadMessages = new Vector<Message>( );
        List<Message> modelMessages = ( (MessageTableModel) _messageTable.getModel( ) ).getMessages( );
        if ( modelMessages != null )
        {
            for ( Message message : modelMessages )
            {
                if ( !_readMessages.contains( message ) )
                {
                    unreadMessages.add( message );
                }
            }
        }
        return unreadMessages;
    }

    @Override
    public Icon getIcon ( )
    {
        return ResourceManager.getIcon( getName( ) );
    }

    @Override
    public JButton getButton ( )
    {
        return _button;
    }

    @Override
    public void closed ( )
    {
        _button.start( );
    }

    @Override
    public void opened ( )
    {
        readMessages( );
        // Stop the button so it doesn't update the UI while its showing.
        _button.stop( );
    }
}
