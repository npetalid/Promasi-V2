package org.promasi.ui.promasiui.promasidesktop;

import java.awt.EventQueue;
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
import org.promasi.model.Employee;
import org.promasi.model.Project;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;
import org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.projectFinishedUi.SinglePlayerScoreModeProjectFinishedUi;
import org.promasi.utilities.ui.ScreenUtils;


/**
 * The main frame of the Promasi application.
 *
 * @author eddiefullmetal
 *
 */
public class DesktopMainFrame extends JFrame implements IToolbarListener,IShellListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( DesktopMainFrame.class );

    /**
     * The main toolbar.
     */
    private DesktopToolbar _toolbar;

    /**
     * The desktop pane to show the program windows.
     */
    private JDesktopPane _desktopPane;

    /**
     *	System shell.
     */
    private Shell _shell;

    /**
     * Initializes the object.
     */
    public DesktopMainFrame(Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
    	
    	_shell=shell;
    	_shell.addListener(this);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "PRO.MA.SI" );
        setSize( ScreenUtils.sizeForPercentage( 1, 1 ) );
        setUndecorated( true );
        ScreenUtils.centerInScreen( this );
        
        _toolbar = new DesktopToolbar(this,_shell );
        _toolbar.addListener( this );
        _desktopPane = new JDesktopPane( );
        
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _toolbar, new CC( ).dockNorth( ) );
        add( _desktopPane, new CC( ).grow( ) );
    }

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
	public void projectStarted(Project project) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(Project project) {
		SinglePlayerScoreModeProjectFinishedUi ui = new SinglePlayerScoreModeProjectFinishedUi(this,_shell);
		ui.showUi( project );
	}

	@Override
	public void projectAssigned(Project project) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(Employee employee) {
		// TODO Auto-generated method stub
		
	}
}
