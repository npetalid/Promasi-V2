package org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs;


import java.awt.Color;
import java.awt.Graphics;

import org.promasi.core.sdobjects.VariableSdObject;
import org.promasi.ui.gmtools.coredesigner.gef.AbstractSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * The graphical representation of an {@link VariableSdObject} in the designer.
 * 
 * @author eddiefullmetal
 * 
 */
public class VariableSdObjectFig
        extends AbstractSdObjectFig
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator}.
     */
    public VariableSdObjectFig( SdObjectDecorator sdObject )
    {
        super( sdObject );
    }

    @Override
    public void appendSvg ( StringBuffer sb )
    {
    }

    @Override
    public void paint ( Graphics g )
    {
        super.paint( g );
    }

    @Override
    public Color getBackgroundColor ( )
    {
        return Color.decode( "#c0d4c3" );
    }
}
