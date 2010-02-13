package org.promasi.ui.gmtools.coredesigner.editors;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.ui.gmtools.coredesigner.IEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.equationbuilders.CalculatedEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.equationbuilders.ConstantEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.equationbuilders.ExternalEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.equationbuilders.LookupEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * 
 * Editor for creating or editing {@link IEquation}s.
 * 
 * @author eddiefullmetal
 * 
 */
public class EquationEditor
        extends JDialog
{

    /**
     * The {@link SdObjectDecorator} to edit the equation for.
     */
    private SdObjectDecorator _sdObject;

    /**
     * Apply button that assigns the equation to the object.
     */
    private JButton _applyButton;

    /**
     * The type of the equation.
     */
    private JComboBox _equationTypeCombo;

    /**
     * The label of the {@link #_equationTypeCombo}.
     */
    private JLabel _equationTypeLabel;

    /**
     * The {@link JXPanel} that contains an equation editor.
     */
    private JXPanel _editorHolder;

    /**
     * The registered editors for an {@link EquationType}.
     */
    private Hashtable<EquationType, IEquationBuilder> _equationBuilders;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public EquationEditor( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        _equationBuilders = new Hashtable<EquationType, IEquationBuilder>( );
        _equationBuilders.put( EquationType.Constant, new ConstantEquationBuilder( ) );
        _equationBuilders.put( EquationType.External, new ExternalEquationBuilder( sdObject ) );
        _equationBuilders.put( EquationType.Calculated, new CalculatedEquationBuilder( sdObject ) );
        _equationBuilders.put( EquationType.Lookup, new LookupEquationBuilder( sdObject ) );

        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     * @param equationToLoad
     *            The {@link IEquation} to load to the editor.
     */
    public EquationEditor( SdObjectDecorator sdObject, IEquation equationToLoad )
    {
        this( sdObject );
        if ( equationToLoad != null )
        {
            setCurrentBuilder( equationToLoad.getType( ) );
            getCurrentBuilder( ).setCurrentEquation( equationToLoad );
        }
        else
        {
            setCurrentBuilder( EquationType.Calculated );
        }
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        setSize( 800, 600 );
        setTitle( ResourceManager.getString( EquationEditor.class, "title" ) );

        _equationTypeLabel = new JLabel( ResourceManager.getString( EquationEditor.class, "equationTypeText" ) );
        buildEquationTypeCombo( );
        buildEditorHolder( );
        _applyButton = new JButton( ResourceManager.getString( EquationEditor.class, "applyButtonText" ) );
        _applyButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                setVisible( false );
            }

        } );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _equationTypeLabel, new CC( ).split( 2 ) );
        add( _equationTypeCombo, new CC( ).growX( ).alignX( "leading" ).wrap( ) );
        add( _editorHolder, new CC( ).grow( ).spanX( ).wrap( ) );
        add( _applyButton, new CC( ) );
    }

    /**
     * Creates the {@link #_editorHolder}.
     */
    private void buildEditorHolder ( )
    {
        _editorHolder = new JXPanel( );
        _editorHolder.setLayout( new BorderLayout( ) );
    }

    /**
     * Creates the {@link #_equationTypeCombo}.
     */
    private void buildEquationTypeCombo ( )
    {
        if ( _equationTypeCombo == null )
        {
            _equationTypeCombo = new JComboBox( );
            for ( EquationType equationType : EquationType.values( ) )
            {
                _equationTypeCombo.addItem( equationType );
            }
            _equationTypeCombo.addActionListener( new ActionListener( )
            {

                @Override
                public void actionPerformed ( ActionEvent e )
                {
                    setCurrentBuilder( (EquationType) _equationTypeCombo.getSelectedItem( ) );
                }

            } );
        }
    }

    /**
     * 
     * Sets the current {@link IEquationBuilder}.
     * 
     * @param type
     *            The type of the {@link IEquation} to get the builder for.
     */
    private void setCurrentBuilder ( EquationType type )
    {
        _equationTypeCombo.setSelectedItem( type );
        IEquationBuilder equationBuilder = _equationBuilders.get( type );
        _editorHolder.setBorder( BorderFactory.createTitledBorder( type.toString( ) ) );
        IEquationBuilder currentBuilder = getCurrentBuilder( );
        if ( currentBuilder != null && currentBuilder instanceof IEquationBuilder )
        {
            _editorHolder.remove( (JComponent) currentBuilder );
        }
        if ( equationBuilder != null && equationBuilder instanceof JComponent )
        {
            _editorHolder.add( (JComponent) equationBuilder, BorderLayout.CENTER );
        }
        invalidate( );
        repaint( );
        validate( );
    }

    /**
     * Returns the current {@link IEquationBuilder}.
     * 
     * @return The current {@link IEquationBuilder}.
     */
    private IEquationBuilder getCurrentBuilder ( )
    {
        BorderLayout layout = (BorderLayout) _editorHolder.getLayout( );
        JComponent comp = (JComponent) layout.getLayoutComponent( BorderLayout.CENTER );
        if ( comp != null && comp instanceof IEquationBuilder )
        {
            return (IEquationBuilder) comp;
        }
        return null;
    }

    /**
     * Gets the created\edited equation.
     * 
     * @return The {@link IEquation}.
     */
    public IEquation getEquation ( )
    {
        IEquationBuilder currentBuilder = getCurrentBuilder( );
        if ( currentBuilder != null )
        {
            IEquation equation = currentBuilder.buildEquation( );
            return equation;
        }
        // not going to happen unless there is an equation type that has no
        // registered builders.
        return null;
    }
}
