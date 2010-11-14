package org.promasi.ui.utilities;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;


/**
 * Class contains various methods relative to screen.
 * 
 * @author eddiefullmetal
 * 
 */
public final class ScreenUtils
{

    /**
     * Initializes the object.
     */
    private ScreenUtils( )
    {

    }

    /**
     * Sets the location of the component to the center of the primary screen.
     * 
     * @param component
     *            The {@link Window} to set the location.
     */
    public static void centerInScreen ( Window component )
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
        GraphicsDevice[] gs = ge.getScreenDevices( );
        // Get the first device(screen).
        int width = gs[0].getDisplayMode( ).getWidth( );
        int height = gs[0].getDisplayMode( ).getHeight( );

        Rectangle abounds = component.getBounds( );
        component.setLocation( ( width - abounds.width ) / 2, ( height - abounds.height ) / 2 );
    }

    /**
     * 
     * Calculates a dimension from given percentages.
     * 
     * @param heightPercentage
     *            The percentage on the height.
     * @param widthPercentage
     *            The percentage on the width.
     * 
     * @return The size depending on the primary screen size.
     */
    public static Dimension sizeForPercentage ( double heightPercentage, double widthPercentage )
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment( );
        GraphicsDevice[] gs = ge.getScreenDevices( );
        // Get the first device(screen).
        int width = gs[0].getDisplayMode( ).getWidth( );
        int height = gs[0].getDisplayMode( ).getHeight( );

        // The width and height of the main frame are 80% of the screen.

        Dimension size = new Dimension( );
        size.height = (int) ( height * heightPercentage );
        size.width = (int) ( width * widthPercentage );

        return size;
    }
}