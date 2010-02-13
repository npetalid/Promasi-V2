package org.promasi.ui.gmtools.coredesigner;


import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXFrame.StartPosition;
import org.promasi.utilities.ui.ScreenUtils;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;


/**
 * Main class that handles the initialization of the Core Designer.
 * 
 * @author eddiefullmetal
 * 
 */
public final class Application
{

    /**
     * The name of the application.
     */
    private static final String NAME = "Core Designer";

    /**
     * The {@link ApplicationRunner} that is used.
     */
    private static final ApplicationRunner RUNNER = new ApplicationRunner( );

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
         * The main frame of the application.
         */
        private JXFrame _mainFrame;

        /**
         * Shows the main frame.
         */
        @Override
        public void run ( )
        {
            // Create the main frame and show it.
            buildMainFrame( );
            _mainFrame.setVisible( true );
        }

        /**
         * Creates the {@link #_mainFrame}.
         */
        private void buildMainFrame ( )
        {
            _mainFrame = new JXFrame( NAME );
            _mainFrame.setSize( ScreenUtils.sizeForPercentage( 0.8, 0.8 ) );
            _mainFrame.setDefaultCloseOperation( JXFrame.EXIT_ON_CLOSE );
            _mainFrame.setStartPosition( StartPosition.CenterInScreen );
            _mainFrame.setContentPane( new MainPanel( ) );
        }

    }
}
