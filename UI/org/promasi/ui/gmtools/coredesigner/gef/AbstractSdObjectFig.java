package org.promasi.ui.gmtools.coredesigner.gef;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.lang.StringUtils;
import org.promasi.core.IEquation;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;
import org.tigris.gef.presentation.FigNode;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;


/**
 * The graphical representation of an {@link SdObjectDecorator} in the designer.
 * 
 * @author eddiefullmetal
 * 
 */
public abstract class AbstractSdObjectFig
        extends FigNode
        implements PropertyChangeListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The {@link SdObjectDecorator} that this panel represents.
     */
    private SdObjectDecorator _sdObject;

    /**
     * The rectangle fig.
     */
    private FigRect _rectangleFig;

    /**
     * The {@link FigText} that represents the key of the
     * {@link SdObjectDecorator}.
     */
    private FigText _keyFig;

    /**
     * The {@link FigText} that represents the {@link IEquation} of the
     * {@link SdObjectDecorator}.
     */
    private FigText _equationFig;

    /**
     * The min size of this {@link FigNode}.
     */
    private static final Dimension MIN_SIZE = new Dimension( 80, 60 );

    /**
     * This is the y location of the equation fig.
     */
    private static final int EQUATION_Y_LOCATION = 15;

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            the {@link #_sdObject}.
     */
    public AbstractSdObjectFig( SdObjectDecorator sdObject )
    {
        _sdObject = sdObject;
        _sdObject.addPropertyChangeListener( this );
        initializeComponents( );
        initializeLayout( );
        propertyChange( new PropertyChangeEvent( _sdObject, SdObjectDecorator.KEY_PROPERTY, StringUtils.EMPTY, _sdObject.getKey( ) ) );
        propertyChange( new PropertyChangeEvent( _sdObject, SdObjectDecorator.EQUATION_PROPERTY, null, _sdObject.getEquation( ) ) );
    }

    /**
     * Initializes all the ui components.
     */
    private void initializeComponents ( )
    {
        buildKeyFig( );
        buildEquationFig( );
        buildRectangleFig( );
        setOwner( _rectangleFig );
    }

    /**
     * Places the ui components to the panel.
     */
    private void initializeLayout ( )
    {
        addFig( _rectangleFig );
        addFig( _equationFig );
        addFig( _keyFig );
    }

    /**
     * Creates the {@link #_equationFig}.
     */
    private void buildEquationFig ( )
    {
        _equationFig = new FigText( getLocation( ).x, getLocation( ).y, getWidth( ), getHeight( ) );
        _equationFig.setLineWidth( 0 );
        _equationFig.setFontSize( 12 );
        _equationFig.setWordWrap( true );
        _equationFig.setJustification( FigText.JUSTIFY_CENTER );
        _equationFig.setFilled( false );
        _equationFig.setText( StringUtils.EMPTY );
    }

    /**
     * Creates the {@link #_keyFig}.
     */
    private void buildKeyFig ( )
    {
        _keyFig = new FigText( getLocation( ).x, getLocation( ).y, getWidth( ), getHeight( ) );
        _keyFig.setBold( true );
        _keyFig.setLineWidth( 0 );
        _keyFig.setFontSize( 12 );
        _keyFig.setFilled( false );
        _keyFig.setJustification( FigText.JUSTIFY_CENTER );
        _keyFig.setText( StringUtils.EMPTY );
    }

    /**
     * Creates the {@link #_keyFig}.
     */
    private void buildRectangleFig ( )
    {
        _rectangleFig = new FigRect( getX( ), getY( ), getWidth( ), getHeight( ) );
        _rectangleFig.setFilled( true );
        _rectangleFig.setFillColor( getBackgroundColor( ) );
        _rectangleFig.setOwner( this );
    }

    /**
     * Gets the {@link #_sdObject}.
     * 
     * @return The {@link #_sdObject}.
     */
    public SdObjectDecorator getSdObject ( )
    {
        return _sdObject;
    }

    @Override
    public void propertyChange ( PropertyChangeEvent evt )
    {
        if ( evt.getSource( ).equals( _sdObject ) )
        {
            if ( evt.getPropertyName( ).equals( SdObjectDecorator.KEY_PROPERTY ) )
            {
                if ( _sdObject.getKey( ) != null )
                {
                    _keyFig.setText( _sdObject.getKey( ) );
                    // When the setText is called the fig recalculates its
                    // bounds
                    // according to the text.
                    if ( _keyFig.getSize( ).width > getWidth( ) )
                    {
                        setWidth( _keyFig.getSize( ).width );
                    }
                    else
                    {
                        _keyFig.setBounds( getBounds( ) );
                    }
                    _keyFig.redraw( );
                }
            }
            else if ( evt.getPropertyName( ).equals( SdObjectDecorator.EQUATION_PROPERTY ) )
            {
                if ( _sdObject.getEquation( ) != null )
                {
                    _equationFig.setText( _sdObject.getEquation( ).getType( ).toString( ) );
                    _equationFig.setBounds( _x, _y + EQUATION_Y_LOCATION, _w, _h - EQUATION_Y_LOCATION );
                    _equationFig.redraw( );
                }
            }
        }
    }

    @Override
    protected void setBoundsImpl ( int x, int y, int w, int h )
    {
        _x = x;
        _y = y;
        _w = w < MIN_SIZE.width ? MIN_SIZE.width : w;
        _h = h < MIN_SIZE.height ? MIN_SIZE.height : h;
        _rectangleFig.setBounds( getBounds( ) );
        _keyFig.setBounds( getBounds( ) );
        _equationFig.setBounds( _x, _y + EQUATION_Y_LOCATION, _w, _h - EQUATION_Y_LOCATION );
    }

    /**
     * Gets the background color to draw.
     * 
     * @return The {@link Paint} to draw as a background.
     */
    public abstract Color getBackgroundColor ( );

    @Override
    public String toString ( )
    {
        return _sdObject.getKey( );
    }
}
