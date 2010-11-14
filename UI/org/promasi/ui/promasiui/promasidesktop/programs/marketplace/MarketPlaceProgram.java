package org.promasi.ui.promasiui.promasidesktop.programs.marketplace;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
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
import org.promasi.game.marketplace.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
import org.promasi.sdsystem.serialization.SerializationException;
import org.promasi.ui.promasiui.promasidesktop.programs.AbstractProgram;
import org.promasi.ui.promasiui.promasidesktop.resources.ResourceManager;

import com.jidesoft.swing.JideButton;


/**
 *
 * Program for browsing and hiring available employees.
 *
 * @author eddiefullmetal
 *
 */
public class MarketPlaceProgram extends AbstractProgram implements IGameEventHandler
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
    public MarketPlaceProgram(IGame game )throws NullArgumentException
    {
        super( "marketplace", "Marketplace, browse and hire employees" );
        if(game==null)
        {
        	throw new NullArgumentException("Wrong argument shell==null");
        }
        _game=game;
        initializeComponents( );
        initializeLayout( );
        game.registerGameEventHandler(this);
    }

    /**
     * Initializes the components.
     * @throws SerializationException 
     */
    private void initializeComponents ( )
    {
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
        _hireButton = new JideButton( ResourceManager.getString( MarketPlaceProgram.class, "hireButton", "text" ) );
        _hireButton.addActionListener( new ActionListener( )
        {

            @Override
            public void actionPerformed ( ActionEvent e )
            {
                hireEmployee( );
            }

        } );
    }

    /**
     * Initializes the layout.
     */
    private void initializeLayout ( )
    {
        setLayout( new MigLayout( new LC( ).fillX( ) ) );
        JScrollPane scrollPane = new JScrollPane( _employeeList );
        scrollPane.setBorder( BorderFactory.createEmptyBorder( ) );
        add( _hireButton, new CC( ).alignX( "center" ).wrap( ) );
        add( scrollPane, new CC( ).growX( ).alignX( "center" ) );
    }

    /**
     * Hires the selected employee from the {@link #_employeeList}.
     */
    private void hireEmployee ( )
    {
    	SerializableEmployee employee = (SerializableEmployee) _employeeList.getSelectedValue( );
        if ( employee != null )
        {
            if ( _game.isEmployeeAvailable(employee))
            {
            	try {
					_game.hireEmployee( employee );
				} catch (NullArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SerializationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else
            {
                JOptionPane.showMessageDialog( this, ResourceManager.getString( MarketPlaceProgram.class, "employeeAlreadyHired", "text" ),
                        ResourceManager.getString( MarketPlaceProgram.class, "employeeAlreadyHired", "title" ), JOptionPane.WARNING_MESSAGE );
            }
        }
        else
        {
            JOptionPane.showMessageDialog( this, ResourceManager.getString( MarketPlaceProgram.class, "noSelectedEmployee", "text" ), ResourceManager
                    .getString( MarketPlaceProgram.class, "noSelectedEmployee", "title" ), JOptionPane.WARNING_MESSAGE );
        }
    }

    @Override
    public void closed ( )
    {

    }

    @Override
    public Icon getIcon ( )
    {
        return ResourceManager.getIcon( getName( ) );
    }

    @Override
    public void opened ( )
    {

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
