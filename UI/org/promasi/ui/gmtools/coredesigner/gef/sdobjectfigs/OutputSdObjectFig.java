package org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs;


import java.awt.Color;
import java.awt.Graphics;

import org.promasi.core.sdobjects.OutputSdObject;
import org.promasi.ui.gmtools.coredesigner.gef.AbstractSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * The graphical representation of an {@link OutputSdObject} in the designer.
 * 
 * @author eddiefullmetal
 * 
 */
public class OutputSdObjectFig
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
    public OutputSdObjectFig( SdObjectDecorator sdObject )
    {
        super( sdObject );
    }

    @Override
    public void paint ( Graphics g )
    {
        super.paint( g );
    }

    @Override
    public Color getBackgroundColor ( )
    {
        return Color.decode( "#dddddd" );
    }
}
