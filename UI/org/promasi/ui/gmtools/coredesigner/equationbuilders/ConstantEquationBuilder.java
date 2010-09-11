package org.promasi.ui.gmtools.coredesigner.equationbuilders;


import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.equations.ConstantEquation;
import org.promasi.ui.gmtools.coredesigner.IEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * 
 * Builder for the {@link ConstantEquation}.
 * 
 * @author eddiefullmetal
 * 
 */
public class ConstantEquationBuilder
        extends JXPanel
        implements IEquationBuilder
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The value of the {@link ConstantEquation}.
     */
    private JSpinner _valueSpinner;

    /**
     * The value of the {@link ConstantEquation}.
     */
    private JLabel _valueLabel;

    /**
     * Initializes the object.
     */
    public ConstantEquationBuilder( )
    {
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        final double step = 0.001d;
        _valueLabel = new JLabel( ResourceManager.getString( ConstantEquationBuilder.class, "valueText" ) );
        _valueSpinner = new JSpinner( new SpinnerNumberModel( 0.0d, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, step ) );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fillX( ) ) );
        add( _valueLabel, new CC( ) );
        add( _valueSpinner, new CC( ).growX( ) );
    }

    @Override
    public IEquation buildEquation ( )
    {
        return new ConstantEquation( (Double) _valueSpinner.getValue( ) );
    }

    @Override
    public void setCurrentEquation ( IEquation equation )
    {
        if ( equation.getType( ).equals( EquationType.Constant ) )
        {
            ConstantEquation constantEquation = (ConstantEquation) equation;
            _valueSpinner.setValue( constantEquation.getValue( ) );
        }
    }
}
