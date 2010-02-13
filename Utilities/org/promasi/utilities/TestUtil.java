package org.promasi.utilities;


import org.apache.log4j.BasicConfigurator;


/**
 * Class that contains testing utilities.
 * 
 * @author eddiefullmetal
 * 
 */
public final class TestUtil
{

    /**
     * A flag that checks if the logging is already initialized.
     */
    private static boolean IS_LOGGING_INITIALIZED;

    /**
     * Initializes the object.
     */
    private TestUtil( )
    {

    }

    static
    {
        IS_LOGGING_INITIALIZED = false;
    }

    /**
     * Initializes the logger for all tests.
     */
    public static void initializeLogging ( )
    {
        if ( !IS_LOGGING_INITIALIZED )
        {
            BasicConfigurator.configure( );
            IS_LOGGING_INITIALIZED = true;
        }
    }

}
