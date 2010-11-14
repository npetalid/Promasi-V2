package org.promasi.ui.promasiui.promasidesktop;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.apache.commons.lang.NullArgumentException;
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
 * A {@link JButton} that is used as a clock.
 * 
 * @author eddiefullmetal
 * 
 */
public class ClockButton extends JButton implements IGameEventHandler, ActionListener, Runnable
{
	/**
	 * 
	 */
	private IGame _game;
	
	private DateTime _currentDateTime;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Initializes the object.
     */
    public ClockButton( IGame game )throws NullArgumentException
    {
    	if(game==null){
    		throw new NullArgumentException("Wrong argument game==null");
    	}
    	
        setIcon( ResourceManager.getIcon( "clock" ) );
        addActionListener( this );
        setFocusPainted( false );
        _game=game;
        _game.registerGameEventHandler(this);
    }

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        JPopupMenu menu = new JPopupMenu( );
        menu.add( new ClockPanel( _game ) );
        menu.show( this, getWidth( ) - menu.getPreferredSize( ).width, getHeight( ) );
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
		EventQueue.invokeLater( this );  
	}

	@Override
	public void run() {
		if(_currentDateTime!=null){
			setText( _currentDateTime.toString() );	
		}
	}
}
