package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;
import java.io.File;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;


/**
 *
 * Main class that starts the program.
 *
 * @author eddiefullmetal
 *
 */
public final class Application
{
    /**
     * The name of the application.
     */
    private static final String NAME = "PRO.MA.SI";
    
    /**
     * 
     */
    private static final ApplicationRunner RUNNER = new ApplicationRunner();

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Application.class );

    /**
     * Initializes the object.
     */
    private Application( )
    {

    }

    /**
     *
     * Main method that starts the application.
     *
     * @param args
     *            The arguments of the method.
     */
    public static void main ( String[] args )
    {
        DOMConfigurator.configure( "Data" + File.separator + "log4j.xml" );
        LOGGER.info( "Starting " + NAME + "..." );

        // Set the look and feel of the application.
        try
        {
            UIManager.setLookAndFeel( new NimbusLookAndFeel( ) );
        }
        catch ( UnsupportedLookAndFeelException e )
        {
            // In case the look and feel is no supported the application will
            // start with the metal lf.
            LOGGER.warn( "Could not set look and feel" );
        }
        
        // Start the mainframe in the event queue.
        EventQueue.invokeLater( RUNNER );
    }

    /**
     *
     * Class that handles the UI initialization.
     *
     * @author eddiefullmetal
     *
     */
    private static class ApplicationRunner
            implements Runnable
    {
    	/**
    	 * 
    	 * @param shell
    	 * @throws NullArgumentException
    	 */
    	public ApplicationRunner()throws NullArgumentException
    	{
    	}
    	
        /**
         * Shows the {@link PlayModeSelectorFrame}.
         */
        @Override
        public void run ( )
        {
        	try
        	{
                PlayModeSelectorFrame dialog = new PlayModeSelectorFrame();
                dialog.setVisible( true );
        	}
        	catch(IllegalArgumentException e)
        	{
        		System.exit( -1 );
        	}
        }
    }
}
