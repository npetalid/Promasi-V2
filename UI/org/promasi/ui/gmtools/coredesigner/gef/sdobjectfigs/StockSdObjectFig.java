package org.promasi.ui.gmtools.coredesigner.gef.sdobjectfigs;


import java.awt.Color;
import java.awt.Graphics;

import org.promasi.core.sdobjects.StockSdObject;
import org.promasi.ui.gmtools.coredesigner.gef.AbstractSdObjectFig;
import org.promasi.ui.gmtools.coredesigner.model.SdObjectDecorator;


/**
 * The graphical representation of an {@link StockSdObject} in the designer.
 * 
 * @author eddiefullmetal
 * 
 */
public class StockSdObjectFig
        extends AbstractSdObjectFig
{

    /**
     * Initializes the object.
     * 
     * @param sdObject
     *            The {@link SdObjectDecorator}.
     */
    public StockSdObjectFig( SdObjectDecorator sdObject )
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
        return Color.decode( "#ffcf9e" );
    }
}
