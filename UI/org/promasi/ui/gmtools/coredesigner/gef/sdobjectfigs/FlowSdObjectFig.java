package org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs;


import java.awt.Color;
import java.awt.Graphics;

import org.promasi.core.sdobjects.FlowSdObject;
import org.promasi.ui.gmtools.coredesigner.gef.AbstractSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * The graphical representation of an {@link FlowSdObject} in the designer.
 * 
 * @author eddiefullmetal
 * 
 */
public class FlowSdObjectFig
        extends AbstractSdObjectFig
{

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator}.
     */
    public FlowSdObjectFig( SdObjectDecorator sdObject )
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
        return Color.decode( "#d8e0ff" );
    }

}
