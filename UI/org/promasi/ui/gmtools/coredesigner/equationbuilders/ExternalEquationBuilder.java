package org.promasi.ui.gmtools.coredesigner.equationbuilders;


import javax.swing.JLabel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.promasi.core.EquationType;
import org.promasi.core.IEquation;
import org.promasi.core.ISdObject;
import org.promasi.core.equations.ExternalEquation;
import org.promasi.ui.gmtools.coredesigner.IEquationBuilder;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * 
 * Builder for the {@link ExternalEquation}.
 * 
 * @author eddiefullmetal
 * 
 */
public class ExternalEquationBuilder
        extends JXPanel
        implements IEquationBuilder
{

    /**
     * The {@link ISdObject} to use as a context for the
     * {@link ExternalEquation}.
     */
    private SdObjectDecorator _sdObject;

    /**
     * A label that displays the key of the {@link #_sdObject}.
     */
    private JLabel _sdObjectLabel;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link #_sdObject}.
     */
    public ExternalEquationBuilder( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes all the ui components.
     */
    protected void initializeComponents ( )
    {
        _sdObjectLabel = new JLabel( _sdObject.getKey( ) );
    }

    /**
     * Places the ui components to the panel.
     */
    protected void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ) ) );
        add( _sdObjectLabel, new CC( ).alignX( "center" ) );
    }

    @Override
    public IEquation buildEquation ( )
    {
        return new ExternalEquation( _sdObject.getActualSdObject( ) );
    }

    @Override
    public void setCurrentEquation ( IEquation equation )
    {
        if ( equation.getType( ).equals( EquationType.External ) )
        {
            ExternalEquation externalEquation = (ExternalEquation) equation;
            _sdObjectLabel.setText( externalEquation.getContext( ).getKey( ) );
        }
    }

}
