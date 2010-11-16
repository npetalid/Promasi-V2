package org.promasi.ui.promasiui.promasidesktop.programs.marketplace;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.IGameEventHandler;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.project.SerializableProject;
import org.promasi.sdsystem.serialization.SerializationException;

import com.jidesoft.swing.JideButton;


/**
 *
 * Program for browsing and hiring available employees.
 *
 * @author eddiefullmetal
 *
 */
public class MarketPlaceFrame extends JInternalFrame implements IGameEventHandler
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The {@link JList} that contains all the available employees.
     */
    private JList _employeeList;

    /**
     * Button that when clicked it hires the selected employee.
     */
    private JideButton _hireButton;

    /**
     * 
     */
    private IGame _game;

    /**
     * Initializes the object.
     */
    public MarketPlaceFrame(IGame game )throws NullArgumentException
    {
    	super("MarketPlace", true, true, true, true);
        if(game==null)
        {
        	throw new NullArgumentException("Wrong argument shell==null");
        }
        
        _game=game;
        
    	List<SerializableEmployee> employees=new LinkedList<SerializableEmployee>();
    	try{
    		employees=_game.getAllEmployees();
    	}catch(SerializationException e){
    		
    	}
    	
        _employeeList = new JList(employees.toArray());
        _employeeList.setCellRenderer( new MarketPlaceEmployeeListRenderer( _game  ) );
        // Due to nimbus look and feel if this is not done the empty space from
        // the list will be white.
        Color backgroundColor = getBackground( );
        _employeeList.setBackground( new Color( backgroundColor.getRed( ), backgroundColor.getGreen( ), backgroundColor.getBlue( ) ) );
        // Create the hireButton.
        _hireButton = new JideButton( "Hire employee" );
        _hireButton.addActionListener( new ActionListener( ){
            @Override
            public void actionPerformed ( ActionEvent e ){
                hireEmployee( );
            }

        } );
        
        setLayout( new MigLayout( new LC( ).fillX( ) ) );
        JScrollPane scrollPane = new JScrollPane( _employeeList );
        scrollPane.setBorder( BorderFactory.createEmptyBorder( ) );
        add( _hireButton, new CC( ).alignX( "center" ).wrap( ) );
        add( scrollPane, new CC( ).growX( ).alignX( "center" ) );
        
        game.registerGameEventHandler(this);
    }

    /**
     * Hires the selected employee from the {@link #_employeeList}.
     */
    private void hireEmployee ( ){
    	SerializableEmployee employee = (SerializableEmployee) _employeeList.getSelectedValue( );
        if ( employee != null ){
            if ( _game.isEmployeeAvailable(employee)){
            	try {
					_game.hireEmployee( employee );
				} catch (NullArgumentException e) {
					//Logger
				} catch (IllegalArgumentException e) {
					//Logger
				} catch (SerializationException e) {
					//Logger
				}
            }else{
                JOptionPane.showMessageDialog( this,"Employee already hired", "Warning", JOptionPane.WARNING_MESSAGE );
            }
        }else{
            JOptionPane.showMessageDialog( this, "Select employee first", "Warning" , JOptionPane.WARNING_MESSAGE );
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
	public void employeeTaskAttached(SerializableCompany company,
			SerializableEmployee employee, SerializableEmployeeTask employeeTask) {
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

	@Override
	public void employeeHired(
			org.promasi.game.company.SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			DateTime dateTime) {
		

        EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
		        invalidate( );
		        repaint( );
		        validate( );	    
			}
		});
	}

	@Override
	public void employeeTaskDetached(
			org.promasi.game.company.SerializableMarketPlace marketPlace,
			SerializableCompany company, SerializableEmployee employee,
			SerializableEmployeeTask employeeTask) {
		// TODO Auto-generated method stub
	}
}
