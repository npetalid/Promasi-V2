package org.promasi.utilities.ui;


import java.awt.Font;

import javax.swing.JLabel;


/**
 * 
 * Class for quick creation of swing classes.
 * 
 * @author eddiefullmetal
 * 
 */
public class SwingCreator
{

    /**
     * Creates a label with the specified text, fontSize and
     * type(Font.BOLD,Font.ITALIC,Font.PLAIN).
     * 
     */
    public static JLabel createLabel ( String text, int fontSize, int fontType )
    {
        JLabel label = new JLabel( text );
        label.setFont( new Font( label.getFont( ).getFontName( ), fontType, fontSize ) );
        return label;
    }

    /**
     * Creates a label with the specified text and
     * type(Font.BOLD,Font.ITALIC,Font.PLAIN).
     * 
     */
    public static JLabel createLabel ( String text, int fontType )
    {
        JLabel label = new JLabel( text );
        label.setFont( new Font( label.getFont( ).getFontName( ), fontType, label.getFont( ).getSize( ) ) );
        return label;
    }

}
