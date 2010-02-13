package org.promasi.ui.gmtools.coredesigner.propertypanels;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXPanel;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.SdObjectInfo;
import org.promasi.core.SdObjectType;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.core.sdobjects.AbstractSdObject;
import org.promasi.ui.gmtools.coredesigner.IModelDesignerListener;
import org.promasi.ui.gmtools.coredesigner.controllers.Shell;
import org.promasi.ui.gmtools.coredesigner.editors.EquationEditor;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * 
 * A panel that can edit the properties of an {@link AbstractSdObject}.
 * 
 * @author eddiefullmetal
 * 
 */
public class PropertyPanel
        extends JXPanel
        implements IModelDesignerListener, ActionListener, FocusListener
{

    /**
     * The {@link SdObjectDecorator} that this properties panel is displaying.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The name of the {@link ISdObject}.
     */
    private JLabel _nameLabel;

    /**
     * The value of the name of an {@link ISdObject}.
     */
    private JTextField _nameText;

    /**
     * The initial value of the {@link #_sdObject}.
     */
    private JSpinner _initialValueSpinner;

    /**
     * The initial value of sdObject.
     */
    private JLabel _initialValueLabel;

    /**
     * The equation of the {@link #_sdObject}.
     */
    private JLabel _equationLabel;

    /**
     * The value of the {@link IEquation}.
     */
    private JTextField _equationText;

    /**
     * A {@link JButton} that deletes the selected sd object.
     */
    private JButton _deleteButton;

    /**
     * A {@link JButton} that when clicked it creates a {@link SdObjectInfo} for
     * the {@link #_sdObject}.
     */
    private JButton _createInfoButton;

    /**
     * A {@link JButton} that when clicked it removes the {@link SdObjectInfo}
     * from the {@link #_sdObject}.
     */
    private JButton _removeInfoButton;

    /**
     * A {@link JTextArea} that displays the description of the
     * {@link SdObjectInfo}.
     */
    private JTextArea _infoDescriptionArea;

    /**
     * A {@link JTextArea} that displays the hint of the {@link SdObjectInfo}.
     */
    private JTextArea _infoHintArea;

    /**
     * Initializes the object.
     */
    public PropertyPanel( )
    {
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        _nameLabel = new JLabel( ResourceManager.getString( PropertyPanel.class, "nameText" ) );
        buildNameText( );
        // ----------------
        buildInitialValueSpinner( );
        _initialValueLabel = new JLabel( ResourceManager.getString( PropertyPanel.class, "initialValueText" ) );
        // -----------------
        _equationLabel = new JLabel( ResourceManager.getString( PropertyPanel.class, "equationText" ) );
        buildEquationText( );
        _createInfoButton = new JButton( ResourceManager.getString( PropertyPanel.class, "createInfoText" ) );
        _createInfoButton.addActionListener( this );
        _removeInfoButton = new JButton( ResourceManager.getString( PropertyPanel.class, "removeInfoText" ) );
        _removeInfoButton.addActionListener( this );
        _infoDescriptionArea = new JTextArea( );
        _infoDescriptionArea.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( PropertyPanel.class, "infoText" ) ) );
        _infoDescriptionArea.setVisible( false );
        _infoDescriptionArea.addFocusListener( this );
        _infoDescriptionArea.setLineWrap( true );
        _infoHintArea = new JTextArea( );
        _infoHintArea.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( PropertyPanel.class, "hintText" ) ) );
        _infoHintArea.setVisible( false );
        _infoHintArea.addFocusListener( this );
        _infoHintArea.setLineWrap( true );
        _deleteButton = new JButton( ResourceManager.getString( PropertyPanel.class, "deleteText" ) );
        _deleteButton.setVisible( false );
        _deleteButton.addActionListener( this );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _nameLabel, new CC( ) );
        add( _nameText, new CC( ).growX( ).wrap( ) );
        add( _equationLabel, new CC( ) );
        add( _equationText, new CC( ).wrap( ).growX( ) );
        add( _initialValueLabel, new CC( ) );
        add( _initialValueSpinner, new CC( ).growX( ).wrap( ) );
        add( _removeInfoButton, new CC( ).alignX( "trailing" ).spanX( ).split( 2 ) );
        add( _createInfoButton, new CC( ).wrap( ) );
        add( new JScrollPane( _infoDescriptionArea ), new CC( ).grow( ).spanX( ).wrap( ) );
        add( new JScrollPane( _infoHintArea ), new CC( ).grow( ).spanX( ).wrap( ) );
        add( _deleteButton, new CC( ).growX( ).spanX( ) );
    }

    /**
     * Creates the {@link #_nameText}.
     */
    private void buildNameText ( )
    {
        _nameText = new JTextField( );
        _nameText.addActionListener( new ActionListener( )
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                _sdObject.setKey( _nameText.getText( ) );
            }
        } );
    }

    /**
     * Creates the {@link #_initialValueSpinner}.
     */
    private void buildInitialValueSpinner ( )
    {
        final double step = 0.1d;
        _initialValueSpinner = new JSpinner( );
        _initialValueSpinner.setModel( new SpinnerNumberModel( 0.0d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, step ) );
        _initialValueSpinner.addChangeListener( new ChangeListener( )
        {
            @Override
            public void stateChanged ( ChangeEvent e )
            {
                _sdObject.setInitialValue( (Double) _initialValueSpinner.getValue( ) );
            }
        } );
    }

    /**
     * Creates the {@link #_equationText}.
     */
    private void buildEquationText ( )
    {
        _equationText = new JTextField( );
        _equationText.setEditable( false );
        _equationText.addMouseListener( new MouseAdapter( )
        {

            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( e.getClickCount( ) == 2 )
                {
                    EquationEditor equationEditor = new EquationEditor( _sdObject, _sdObject.getEquation( ) );
                    equationEditor.setModal( true );
                    ScreenUtils.centerInScreen( equationEditor );
                    equationEditor.setVisible( true );
                    _sdObject.setEquation( equationEditor.getEquation( ) );
                    viewEquation( );
                }
            }

        } );
    }

    @Override
    public void sdObjectSelected ( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        if ( sdObject == null )
        {
            _nameLabel.setVisible( false );
            _nameText.setVisible( false );
            _initialValueLabel.setVisible( false );
            _initialValueSpinner.setVisible( false );
            _equationLabel.setVisible( false );
            _equationText.setVisible( false );
            _infoHintArea.setVisible( false );
            _infoDescriptionArea.setVisible( false );
            _createInfoButton.setVisible( false );
            _removeInfoButton.setVisible( false );
            _deleteButton.setVisible( false );
        }
        else
        {
            _nameLabel.setVisible( true );
            _nameText.setVisible( true );
            _nameText.setText( sdObject.getKey( ) );
            _createInfoButton.setVisible( true );
            _removeInfoButton.setVisible( true );
            _deleteButton.setVisible( true );
            if ( sdObject.getInfo( ) != null )
            {
                _infoDescriptionArea.setVisible( true );
                _infoDescriptionArea.setText( sdObject.getInfo( ).getDescription( ) );
                _infoHintArea.setVisible( true );
                _infoHintArea.setText( sdObject.getInfo( ).getHint( ) );
                _createInfoButton.setEnabled( false );
                _removeInfoButton.setEnabled( true );
            }
            else
            {
                _infoDescriptionArea.setVisible( false );
                _infoHintArea.setVisible( false );
                _createInfoButton.setEnabled( true );
                _removeInfoButton.setEnabled( false );
            }
            // ------------
            _equationLabel.setVisible( true );
            _equationText.setVisible( true );
            viewEquation( );
            // If the type is stock display the extra initial value field.
            if ( _sdObject.getType( ).equals( SdObjectType.Stock ) )
            {
                _initialValueLabel.setVisible( true );
                _initialValueSpinner.setVisible( true );
                _initialValueSpinner.setValue( _sdObject.getValue( ) );
            }
            else
            {
                _initialValueLabel.setVisible( false );
                _initialValueSpinner.setVisible( false );
            }
        }
    }

    /**
     * Gets the {@link #_sdObject}.
     * 
     * @return The {@link #_sdObject}.
     */
    protected SdObjectDecorator getSdObject ( )
    {
        return _sdObject;
    }

    /**
     * Assigns the value to the {@link #_equationText} depending with the
     * {@link IEquation} of the current {@link #_sdObject}.
     */
    private void viewEquation ( )
    {
        if ( _sdObject != null && _sdObject.getEquation( ) != null )
        {
            String equationType = _sdObject.getEquation( ).getType( ).toString( );
            switch ( _sdObject.getEquation( ).getType( ) )
            {
                case Calculated :
                    _equationText.setText( equationType + ":" + ( (CalculatedEquation) _sdObject.getEquation( ) ).getEquationString( ) );
                    break;
                case Constant :
                    _equationText.setText( equationType + ":" + ( (ConstantEquation) _sdObject.getEquation( ) ).getValue( ) );
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

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource( ).equals( _createInfoButton ) )
        {
            _sdObject.setInfo( new SdObjectInfo( ) );
            _infoDescriptionArea.setVisible( true );
            _infoHintArea.setVisible( true );
            _removeInfoButton.setEnabled( true );
            _createInfoButton.setEnabled( false );
            _infoHintArea.setText( StringUtils.EMPTY );
            _infoDescriptionArea.setText( StringUtils.EMPTY );
        }
        else if ( e.getSource( ).equals( _removeInfoButton ) )
        {
            _sdObject.setInfo( null );
            _infoDescriptionArea.setVisible( false );
            _infoHintArea.setVisible( false );
            _createInfoButton.setEnabled( true );
            _removeInfoButton.setEnabled( false );
        }
        else if ( e.getSource( ).equals( _deleteButton ) )
        {
            Shell.deleteObject( _sdObject );
        }
    }

    @Override
    public void focusGained ( FocusEvent e )
    {
    }

    @Override
    public void focusLost ( FocusEvent e )
    {
        if ( e.getSource( ).equals( _infoDescriptionArea ) )
        {
            _sdObject.getInfo( ).setDescription( _infoDescriptionArea.getText( ) );
        }
        else if ( e.getSource( ).equals( _infoHintArea ) )
        {
            _sdObject.getInfo( ).setHint( _infoHintArea.getText( ) );
        }

    }
}
