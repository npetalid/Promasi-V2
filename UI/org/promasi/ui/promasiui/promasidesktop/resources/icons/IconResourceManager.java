package org.promasi.ui.promasiui.promasidesktop.resources.icons;


import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * Class that provides methods to get icons.
 * 
 * @author eddiefullmetal
 * 
 */
public final class IconResourceManager
{

    /**
     * Initializes the object.
     */
    private IconResourceManager( )
    {

    }

    /**
     * Gets the icon from the package.
     * 
     * @param key
     *            The name of the icon.
     * @return The requested icon.
     */
    public static Icon getIcon ( String key )
    {
        ImageIcon icon = new ImageIcon( IconResourceManager.class.getResource( key ) );
        return icon;
    }
}
