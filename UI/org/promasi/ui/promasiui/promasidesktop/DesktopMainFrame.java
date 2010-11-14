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
import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.IGameEventHandler;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.marketplace.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;
import org.promasi.ui.promasiui.promasidesktop.singleplayerscoremode.projectFinishedUi.SinglePlayerScoreModeProjectFinishedUi;
import org.promasi.ui.utilities.ScreenUtils;



/**
 * The main frame of the Promasi application.
 *
 * @author eddiefullmetal
 *
 */
public class DesktopMainFrame extends JFrame implements IToolbarListener, IGameEventHandler
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
    private IGame _game;

    /**
     * Initializes the object.
     */
    public DesktopMainFrame(IGame game )throws NullArgumentException
    {
    	if(game==null)
    	{
    		throw new NullArgumentException("Wrong argument game==null");
    	}
    	
    	_game=game;
    	_game.registerGameEventHandler(this);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setTitle( "PRO.MA.SI" );
        setSize( ScreenUtils.sizeForPercentage( 1, 1 ) );
        setUndecorated( true );
        ScreenUtils.centerInScreen( this );
        
        _toolbar = new DesktopToolbar(this,_game );
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
	public void projectAssigned(SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(SerializableCompany company,
			SerializableProject project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskAttached(SerializableCompany company,
			SerializableEmployee employee, SerializableEmployeeTask employeeTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskDetached(SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			SerializableEmployeeTask employeeTask) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPay(SerializableCompany company,
			SerializableEmployee employee, Double salary, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(SerializableCompany company,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(SerializableCompany company,
			SerializableProject assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}
}
