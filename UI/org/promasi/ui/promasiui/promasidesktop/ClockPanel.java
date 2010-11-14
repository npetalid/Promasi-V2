package org.promasi.ui.promasiui.promasidesktop;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.jdesktop.swingx.JXMonthView;
import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.IGameEventHandler;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.marketplace.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;


/**
 * 
 * Panel that configures the clock speed and shows a calendar.
 * 
 * @author eddiefullmetal
 * 
 */
public class ClockPanel extends JPanel implements ActionListener, IGameEventHandler
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JXMonthView _calendar;
    private JButton _slowButton;
    private JButton _normalButton;
    private JButton _fastButton;
    private IGame _game;

    /**
     * Initializes the object.
     */
    public ClockPanel( IGame game )throws NullArgumentException
    {
    	if(game==null){
    		throw new NullArgumentException("Wrong argument game==null");
    	}
    	
    	_game=game;
    	_game.registerGameEventHandler(this);
        initializeComponents( );
        initializeLayout( );
    }

    /**
     * Initializes the components.
     */
    private void initializeComponents ( )
    {
        Date currentDate = _game.getSystemDateTime().toDate();
        _calendar = new JXMonthView( );
        _calendar.setFirstDisplayedDay( currentDate );
        _calendar.setTraversable( true );
        _calendar.setTodayBackground( Color.white );
        _calendar.setFlaggedDates( currentDate );
        _calendar.setFlaggedDayForeground( Color.RED );

        _slowButton = new JButton( ResourceManager.getString( ClockPanel.class, "slowButtonText" ) );
        _slowButton.addActionListener( this );
        _normalButton = new JButton( ResourceManager.getString( ClockPanel.class, "normalButtonText" ) );
        _normalButton.addActionListener( this );
        _fastButton = new JButton( ResourceManager.getString( ClockPanel.class, "fastButtonText" ) );
        _fastButton.addActionListener( this );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( _calendar, new CC( ).grow( ).spanX( ).wrap( ) );
        add( new JLabel( ResourceManager.getString( ClockPanel.class, "speedText" ) ), new CC( ) );
        add( _slowButton, new CC( ) );
        add( _normalButton, new CC( ) );
        add( _fastButton, new CC( ).wrap( ) );
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        if ( e.getSource( ).equals( _slowButton ) )
        {
        	_game.setGameSpeed(3000);
        }
        else if ( e.getSource( ).equals( _normalButton ) )
        {
            _game.setGameSpeed(1000);
        }
        else if ( e.getSource( ).equals( _fastButton ) )
        {
            _game.setGameSpeed(100);
        }
    }

    @Override
    public void removeNotify ( )
    {
        super.removeNotify( );
        _game.removeGameEventHandler(this);
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
		 _calendar.setFlaggedDates( dateTime.toDate( ) );
	}
}
