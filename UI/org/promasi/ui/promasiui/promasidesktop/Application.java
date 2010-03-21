package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;
import java.io.File;

import javax.naming.ConfigurationException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.promasi.shell.Shell;
import org.promasi.shell.UiManager;
import org.promasi.shell.playmodes.singleplayerscoremode.SinglePlayerScorePlayMode;
import org.promasi.shell.ui.IUiInitializer;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;
import org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.SinglePlayerScoreModeUiInitializer;

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
        UiManager.getInstance( ).setUiInitializer( new DesktopUiInitializer( ) );
        UiManager.getInstance( ).registerPlayModeInitializer( SinglePlayerScorePlayMode.class, new SinglePlayerScoreModeUiInitializer( ) );
        try
        {
            LOGGER.info( "Initializing UI..." );
            IUiInitializer uiInitializer = UiManager.getInstance( ).getUiInitializer( );
            if ( uiInitializer == null )
            {
                LOGGER.error( "No registered UI initializer." );
                throw new ConfigurationException( "No registered UI initializer." );
            }
            uiInitializer.registerMainFrame( );
        }
        catch ( ConfigurationException e )
        {
            JOptionPane.showMessageDialog( null, ResourceManager.getString( Application.class, "invalidConfiguration", "text" ), ResourceManager
                    .getString( Application.class, "invalidConfiguration", "title" ), JOptionPane.ERROR_MESSAGE );
            System.exit( -1 );
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
         * Shows the {@link PlayModeSelectorFrame}.
         */
        @Override
        public void run ( )
        {
            PlayModeSelectorFrame dialog = new PlayModeSelectorFrame( );
            dialog.setVisible( true );
        }

    }
}
