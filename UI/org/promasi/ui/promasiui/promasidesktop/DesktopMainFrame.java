package org.promasi.ui.promasiui.promasidesktop;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.beans.PropertyVetoException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.communication.ICommunicator;
import org.promasi.shell.Shell;
import org.promasi.shell.ui.IMainFrame;
import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;
import org.promasi.utilities.ui.ScreenUtils;

import com.sun.java.swing.Painter;


/**
 * The main frame of the Promasi application.
 *
 * @author eddiefullmetal
 *
 */
public class DesktopMainFrame
        extends JFrame
        implements IMainFrame, IToolbarListener
{

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( DesktopMainFrame.class );

    /**
     * The main toolbar.
     */
    private DesktopToolbar _toolbar;

    /**
     *
     */
    private ICommunicator _communicator;

    /**
     * The desktop pane to show the program windows.
     */
    private JDesktopPane _desktopPane;

    /**
     *	System shell.
     */
    private Shell _shell;

    /**
     * Singleton implementation.
     */
    private static DesktopMainFrame INSTANCE;

    /**
     * Initializes the object.
     */
    private DesktopMainFrame(Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	_shell=shell;
        // Set the painter
        // if ( UIManager.getLookAndFeel( ).getClass( ).getName( ).equals(
        // NimbusLookAndFeel.class.getName( ) ) )
        // {
        // UIManager.getLookAndFeelDefaults( ).put(
        // "DesktopPane[Enabled].backgroundPainter", new
        // DesktopMainFrame.WallPaperPainter( ) );
        // }
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        _toolbar = new DesktopToolbar(_shell );
        _toolbar.addListener( this );
        _desktopPane = new JDesktopPane( );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _toolbar, new CC( ).dockNorth( ) );
        add( _desktopPane, new CC( ).grow( ) );
    }

    @Override
    public void showMainFrame ( )
    {
        // Make sure that the show method is run in the event queue.
        EventQueue.invokeLater( new Runnable( )
        {

            @Override
            public void run ( )
            {
                LOGGER.info( "Starting main frame..." );
                setVisible( true );
            }

        } );

    }

    @Override
    public void quickLauchButtonClicked ( AbstractProgram program )
    {
        JInternalFrame frame = program.getInternalFrame( );
        if ( showWindow( frame ) )
        {
            program.opened( );
        }
    }

    /**
     * Shows a {@link JInternalFrame} to the screen.
     *
     * @param frame
     * @return True if the frame did not exist on the desktop,False if the frame
     *         already exists and was just selected.
     */
    public boolean showWindow ( JInternalFrame frame )
    {
        List<JInternalFrame> internalFrames = Arrays.asList( _desktopPane.getAllFrames( ) );
        if ( !internalFrames.contains( frame ) )
        {
            _desktopPane.add( frame );
            frame.setVisible( true );
            return true;
        }
        else
        {
            try
            {
                frame.setSelected( true );
                return false;
            }
            catch ( PropertyVetoException e )
            {
                return false;
            }
        }
    }

    @Override
    public void initializeMainFrame ( )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "PRO.MA.SI" );
        setSize( ScreenUtils.sizeForPercentage( 1, 1 ) );
        setUndecorated( true );
        ScreenUtils.centerInScreen( this );
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Singleton implementation.
     */
    public static DesktopMainFrame getInstance (Shell shell )
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new DesktopMainFrame(shell );
        }
        return INSTANCE;
    }

    /**
     * A Painter for painting the wallpaper of the {@link DesktopMainFrame}.
     *
     * @author eddiefullmetal
     *
     */
    private class WallPaperPainter
            implements Painter<JDesktopPane>
    {

        /**
         * The filter to use.
         */
        private BufferedImageOp _filter;

        /**
         * Initializes the object.
         */
        public WallPaperPainter( )
        {
            _filter = new com.jhlabs.image.KaleidoscopeFilter( );
        }

        @Override
        public void paint ( Graphics2D g, JDesktopPane object, int width, int height )
        {
            BufferedImage image = new BufferedImage( width, height, Transparency.OPAQUE );
            Graphics2D imageG = (Graphics2D) image.getGraphics( );
            imageG.setColor( Color.BLUE );
            imageG.fillRect( 0, 0, width, height );
            g.drawImage( image, _filter, 0, 0 );
        }
    }

	@Override
	public void registerCommunicator(ICommunicator communicator) {
		synchronized(this)
		{
			_communicator=communicator;
			_shell.registerCommunicator(communicator);
		}
	}
}
