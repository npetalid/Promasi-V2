package org.promasi.ui.gmtools.coredesigner.propertypanels;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.promasi.core.Event;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.ui.gmtools.coredesigner.IModelDesignerListener;
import org.promasi.ui.gmtools.coredesigner.editors.EventEditor;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * A panel that can edit the events of an {@link AbstractSdObject}.
 * 
 * @author eddiefullmetal
 * 
 */
public class EventPanel
        extends JXPanel
        implements IModelDesignerListener
{

    /**
     * The {@link SdObjectDecorator} that this properties panel is displaying.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The {@link JList} that contains the events of the {@link #_sdObject}.
     */
    private JList _eventList;

    /**
     * A {@link JButton} that when clicked it adds an event to the object.
     */
    private JButton _addButton;

    /**
     * A {@link JButton} that when clicked it edits the selected event of the
     * object.
     */
    private JButton _editButton;

    /**
     * A {@link JButton} that when clicked it removes the selected event from
     * the object.
     */
    private JButton _removeButton;

    /**
     * Initializes the object.
     */
    public EventPanel( )
    {
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        _eventList = new JList( );
        _addButton = new JButton( ResourceManager.getIcon( "add" ) );
        _addButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                EventEditor eventEditor = new EventEditor( _sdObject );
                eventEditor.setModal( true );
                ScreenUtils.centerInScreen( eventEditor );
                eventEditor.setVisible( true );
                populateEventList( );
            }

        } );
        _removeButton = new JButton( ResourceManager.getIcon( "remove" ) );
        _removeButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                Event event = (Event) _eventList.getSelectedValue( );
                if ( event != null )
                {
                    _sdObject.removeEvent( event );
                    populateEventList( );
                }
            }

        } );
        _editButton = new JButton( ResourceManager.getIcon( "edit" ) );
        _editButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                Event event = (Event) _eventList.getSelectedValue( );
                if ( event != null )
                {
                    EventEditor eventEditor = new EventEditor( _sdObject, event );
                    eventEditor.setModal( true );
                    ScreenUtils.centerInScreen( eventEditor );
                    eventEditor.setVisible( true );
                    populateEventList( );
                }
            }

        } );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        final int numberOfButtons = 3;
        add( _addButton, new CC( ).split( numberOfButtons ) );
        add( _editButton, new CC( ) );
        add( _removeButton, new CC( ).wrap( ) );
        add( new JScrollPane( _eventList ), new CC( ).grow( ) );
    }

    @Override
    public void sdObjectSelected ( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        if ( sdObject == null )
        {
            _eventList.setVisible( false );
            _addButton.setVisible( false );
            _editButton.setVisible( false );
            _removeButton.setVisible( false );
        }
        else
        {
            _eventList.setVisible( true );
            _addButton.setVisible( true );
            _editButton.setVisible( true );
            _removeButton.setVisible( true );
            populateEventList( );
        }
    }

    /**
     * Populates the {@link #_eventList} from the {@link #_sdObject}.
     */
    private void populateEventList ( )
    {
        DefaultListModel model = new DefaultListModel( );
        for ( Event event : _sdObject.getEvents( ) )
        {
            model.addElement( event );
        }
        _eventList.setModel( model );
    }
}
