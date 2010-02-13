package org.promasi.ui.gmtools.coredesigner.editors;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.promasi.core.Event;
import org.promasi.core.IEquation;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * Editor for editing or creating an {@link Event}.
 * 
 * @author eddiefullmetal
 * 
 */
public class EventEditor
        extends JDialog
{

    /**
     * The {@link SdObjectDecorator} to edit the events.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The {@link Event} that is being edited.
     */
    private Event _currentEvent;

    /**
     * The name of the {@link Event}.
     */
    private JTextField _nameText;

    /**
     * The {@link IEquation} of the {@link Event}.
     */
    private JTextField _equationText;

    /**
     * Adds the event to the {@link #_sdObject}.
     */
    private JButton _okButton;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public EventEditor( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        _currentEvent = new Event( );
        _currentEvent.setContext( _sdObject.getActualSdObject( ) );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     * 
     * @param eventToLoad
     *            The {@link Event} to edit.
     */
    public EventEditor( SdObjectDecorator sdObject, Event eventToLoad )
    {
        this( sdObject );
        _currentEvent = eventToLoad;
        _nameText.setText( eventToLoad.getName( ) );
        viewEquation( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        setTitle( ResourceManager.getString( EventEditor.class, "title" ) );
        setSize( 400, 600 );
        _okButton = new JButton( ResourceManager.getString( EventEditor.class, "okButtonText" ) );
        _okButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                _currentEvent.setName( _nameText.getText( ) );
                _sdObject.addEvent( _currentEvent );
                setVisible( false );
            }

        } );
        _nameText = new JTextField( );
        _equationText = new JTextField( );
        _equationText.setEditable( false );
        _equationText.addMouseListener( new MouseAdapter( )
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( e.getClickCount( ) == 2 )
                {
                    EquationEditor equationEditor = new EquationEditor( _sdObject, _currentEvent.getEquation( ) );
                    equationEditor.setModal( true );
                    ScreenUtils.centerInScreen( equationEditor );
                    equationEditor.setVisible( true );
                    _currentEvent.setEquation( equationEditor.getEquation( ) );
                    viewEquation( );
                }
            }

        } );
    }

    /**
     * Assigns the value to the {@link #_equationText} depending with the
     * {@link IEquation} of the current {@link #_sdObject}.
     */
    private void viewEquation ( )
    {
        if ( _currentEvent != null && _currentEvent.getEquation( ) != null )
        {
            String equationType = _currentEvent.getEquation( ).getType( ).toString( );
            switch ( _currentEvent.getEquation( ).getType( ) )
            {
                case Calculated :
                    _equationText.setText( equationType + ":" + ( (CalculatedEquation) _currentEvent.getEquation( ) ).getEquationString( ) );
                    break;
                case Constant :
                    _equationText.setText( equationType + ":" + ( (ConstantEquation) _currentEvent.getEquation( ) ).getValue( ) );
                    break;
                default :
                    _equationText.setText( equationType );
                    break;
            }
        }
        else
        {
            _equationText.setText( StringUtils.EMPTY );
        }
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ), new AC( ), new AC( ).index( 0 ).grow( 0.0f ).index( 1 ).grow( 0.0f ).index( 2 ).grow( 1.0f ) ) );
        add( new JLabel( ResourceManager.getString( EventEditor.class, "nameText" ) ), new CC( ) );
        add( _nameText, new CC( ).growX( ).wrap( ) );
        add( new JLabel( ResourceManager.getString( EventEditor.class, "equationText" ) ), new CC( ) );
        add( _equationText, new CC( ).growX( ).wrap( ) );
        add( _okButton, new CC( ).alignY( "bottom" ) );
    }

}
