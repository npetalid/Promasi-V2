package org.promasi.utilities.ui;


import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;


/**
 * OceanTheme with the colors of promasi. <b>NOT USED UNDER DEVELOPMENT.</b>
 * 
 * @author eddiefullmetal
 * 
 */
public class PromasiTheme
        extends OceanTheme
{

    @Override
    public String getName ( )
    {
        return "Promasi Theme";
    }

    @Override
    protected ColorUIResource getPrimary1 ( )
    {
        return new ColorUIResource( 72, 74, 71 );
    }

    @Override
    protected ColorUIResource getPrimary2 ( )
    {
        return new ColorUIResource( 194, 207, 150 );
    }

    @Override
    protected ColorUIResource getPrimary3 ( )
    {
        return new ColorUIResource( 236, 235, 240 );
    }

    @Override
    protected ColorUIResource getSecondary1 ( )
    {
        return new ColorUIResource( 104, 125, 119 );
    }

    @Override
    protected ColorUIResource getSecondary2 ( )
    {
        return new ColorUIResource( 104, 125, 119 );
    }

    @Override
    protected ColorUIResource getSecondary3 ( )
    {
        return new ColorUIResource( 236, 235, 240 );
    }
}
