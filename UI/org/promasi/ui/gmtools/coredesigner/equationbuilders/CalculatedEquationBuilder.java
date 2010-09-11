package org.promasi.ui.gmtools.coredesigner.equationbuilders;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXPanel;
import org.nfunk.jep.JEP;
import org.nfunk.jep.function.PostfixMathCommandI;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.equations.CalculatedEquation;
import org.promasi.core.sdobjects.system.TimeSdObject;
import org.promasi.core.utilities.jep.JepInitializer;
import org.promasi.ui.gmtools.coredesigner.IEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * 
 * Builder for the {@link CalculatedEquation}.
 * 
 * @author eddiefullmetal
 * 
 */
public class CalculatedEquationBuilder
        extends JXPanel
        implements IEquationBuilder
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The Context of the {@link CalculatedEquation}.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The {@link JEP} object that the builder uses to get the available
     * equations.
     */
    private JEP _jep;

    /**
     * The text area that contains the string of the equation.
     */
    private JTextArea _equationTextArea;

    /**
     * A {@link JList} that displays the dependencies of the {@link #_sdObject}.
     */
    private JList _dependenciesList;

    /**
     * A {@link JList} that displays all the available equations of {@link JEP}.
     */
    private JList _equationList;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public CalculatedEquationBuilder( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        _jep = JepInitializer.getFullJep( );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        _equationTextArea = new JTextArea( );
        buildDependenciesList( );
        buildEquationList( );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( _equationTextArea ), new CC( ).grow( ).spanX( ).wrap( ) );

        JScrollPane dependenciesScrollPane = new JScrollPane( _dependenciesList );
        dependenciesScrollPane.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( CalculatedEquationBuilder.class,
                "equationListTitle" ) ) );
        add( dependenciesScrollPane, new CC( ).grow( ) );

        JScrollPane equationScrollPane = new JScrollPane( _equationList );
        equationScrollPane.setBorder( BorderFactory.createTitledBorder( ResourceManager.getString( CalculatedEquationBuilder.class,
                "dependenciesListTitle" ) ) );
        add( equationScrollPane, new CC( ).grow( ) );
    }

    /**
     * Creates the {@link #_equationList}.
     */
    private void buildEquationList ( )
    {

        _equationList = new JList( _jep.getFunctionTable( ).keySet( ).toArray( ) );
        _equationList.addMouseListener( new MouseAdapter( )
        {

            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( e.getClickCount( ) == 2 )
                {
                    if ( _equationList.getSelectedValue( ) != null )
                    {
                        _equationTextArea.replaceSelection( buildFunction( _equationList.getSelectedValue( ).toString( ) ) );
                    }
                }
            }

        } );
    }

    /**
     * Creates the {@link #_dependenciesList}.
     */
    private void buildDependenciesList ( )
    {
        List<ISdObject> objects = new Vector<ISdObject>( _sdObject.getActualSdObject( ).getDependencies( ) );
        objects.add( _sdObject.getActualSdObject( ) );
        objects.add( new TimeSdObject( ) );
        _dependenciesList = new JList( objects.toArray( ) );
        _dependenciesList.addMouseListener( new MouseAdapter( )
        {

            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( e.getClickCount( ) == 2 )
                {
                    if ( _dependenciesList.getSelectedValue( ) != null )
                    {
                        _equationTextArea.replaceSelection( _dependenciesList.getSelectedValue( ).toString( ) );
                    }
                }
            }

        } );
    }

    /**
     * 
     * Creates a function string depending on the name and the arguments of the
     * function.
     * 
     * @param functionName
     *            The name of the function to create the string for.
     * @return The full function exp sin(arg0) if the
     *         {@link PostfixMathCommandI#getNumberOfParameters()} returns a
     *         number lower than zero the arguments are filled with a ? exp
     *         if(?)
     */
    private String buildFunction ( String functionName )
    {
        // Get the PostfixMathCommandI from the functionName if this doesn't
        // exist return an empty string.
        PostfixMathCommandI function = _jep.getFunctionTable( ).get( functionName );
        if ( function == null )
        {
            return StringUtils.EMPTY;
        }

        // First create a string like 'sin('
        StringBuilder builder = new StringBuilder( );
        builder.append( functionName );
        builder.append( "(" );
        if ( function.getNumberOfParameters( ) > 0 )
        {
            // Depending on the number of parameters add an argument string
            // like arg0 so the string will look like 'sin(arg0,'
            for ( int i = 0; i < function.getNumberOfParameters( ); i++ )
            {
                builder.append( "arg" );
                builder.append( i );
                builder.append( "," );
            }
            // Remove the last char , from the string so the string will
            // look like 'sin(arg0'
            builder.deleteCharAt( builder.length( ) - 1 );
        }
        else
        {
            // If the parameter are lower than 0 then
            // the number of parameters that the function takes is unknown
            // (cannot be calculated dynamically) so add a ? so the string
            // will look like 'sin(?'
            builder.append( "?" );
        }
        // finish the string by adding a ) so the string will look like
        // 'sin(arg0)'
        builder.append( ")" );

        return builder.toString( );
    }

    @Override
    public IEquation buildEquation ( )
    {
        return new CalculatedEquation( _sdObject.getActualSdObject( ), _equationTextArea.getText( ) );
    }

    @Override
    public void setCurrentEquation ( IEquation equation )
    {
        if ( equation.getType( ).equals( EquationType.Calculated ) )
        {
            CalculatedEquation calculatedEquation = (CalculatedEquation) equation;
            _equationTextArea.setText( calculatedEquation.getEquationString( ) );
        }
    }
}
