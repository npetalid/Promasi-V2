package org.promasi.ui.promasiui.promasidesktop.resources;


import java.awt.Color;

import javax.swing.Icon;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.promasi.ui.promasiui.promasidesktop.resources.icons.IconResourceManager;


/**
 * 
 * Class that handles resources.
 * 
 * @author eddiefullmetal
 * 
 */
public final class ResourceManager
{

    /**
     * The {@link XMLConfiguration} to get the resources from.
     */
    private static XMLConfiguration CONFIGURATION_READER;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( ResourceManager.class );

    /**
     * Initializes the object.
     */
    private ResourceManager( )
    {

    }

    static
    {
        try
        {
            CONFIGURATION_READER = new XMLConfiguration( ResourceManager.class.getResource( "Resources.xml" ) );
        }
        catch ( ConfigurationException e )
        {
            LOGGER.fatal( "Could not load configuration." );
        }
    }

    /**
     * 
     * Constructs a key based on the class name , the objectName and the
     * propertyName and calls the {@link #getString(String)}.
     * 
     * @param clazz
     * @param objectName
     * @param propertyName
     * 
     * @return
     */
    public static String getString ( Class clazz, String objectName, String propertyName )
    {
        String key = clazz.getName( ) + "." + objectName + "." + propertyName;
        return getString( key );
    }

    /**
     * 
     * Constructs a key based on the class name and the propertyName and calls
     * the {@link #getString(String)}.
     * 
     * @param clazz
     * @param objectName
     * @param propertyName
     * 
     * @return
     */
    public static String getString ( Class clazz, String propertyName )
    {
        String key = clazz.getName( ) + "." + propertyName;
        return getString( key );
    }

    /**
     * Gets a string using the {@link #CONFIGURATION_READER} with the specified
     * key.
     * 
     * @param key
     *            The key to get the string for.
     * @return A string that corresponds to the key.
     */
    private static String getString ( String key )
    {
        if ( CONFIGURATION_READER.containsKey( key ) )
        {
            return CONFIGURATION_READER.getString( key );
        }
        else
        {
            return key;
        }
    }

    /**
     * 
     * Gets an icon, using the {@link IconResourceManager}.
     * 
     * @param key
     *            The key to get the icon name.
     * @return The icon corresponding to the key.
     */
    public static Icon getIcon ( String key )
    {
        return IconResourceManager.getIcon( key + ".png" );
    }

    public static Color getColor ( Class clazz, String propertyName )
    {
        String colorHexCode = getString( clazz, propertyName );
        return Color.decode( colorHexCode );
    }

    public static String formatDateOnly ( DateTime date )
    {
        return date.toString( getString( "dateOnlyFormat" ) );
    }

    public static String formatDateOnly ( LocalDate date )
    {
        return date.toString( getString( "dateOnlyFormat" ) );
    }

    public static String formatDateAndTime ( DateTime date )
    {
        return date.toString( getString( "dateAndTimeFormat" ) );
    }

    public static String formatDetailedDateAndTime ( DateTime date )
    {
        return date.toString( getString( "dateAndTimeDetailedFormat" ) );
    }
}
