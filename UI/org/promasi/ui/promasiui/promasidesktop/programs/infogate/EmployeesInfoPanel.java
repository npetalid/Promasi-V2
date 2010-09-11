package org.promasi.ui.promasiui.promasidesktop.programs.infogate;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.shell.Shell;
import org.promasi.ui.promasiui.promasidesktop.programs.marketplace.MarketPlaceEmployeeListRenderer;


/**
 * Panel that shows information on the hired employees.
 *
 * @author eddiefullmetal
 */
public class EmployeesInfoPanel
        extends JPanel
        implements PropertyChangeListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * A {@link JList} that shows all the hired employees.
     */
    private JList _employeeList;

    /**
     * The company.
     */
    private Company _company;

    /**
     * Initializes the object.
     *
     */
    public EmployeesInfoPanel(Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}
        _company = shell.getCompany( );
        _company.addPropertyChangeListener( this );
        setLayout( new MigLayout( new LC( ).fill( ) ) );
        add( new JScrollPane( getEmployeeList( ) ), new CC( ).grow( ) );
    }

    /**
     * @return the {@link #_employeeList}.
     */
    public JList getEmployeeList ( )
    {
        if ( _employeeList == null )
        {
            _employeeList = new JList( _company.getEmployees( ).toArray( ) );
            _employeeList.setCellRenderer( new MarketPlaceEmployeeListRenderer( ) );
        }
        return _employeeList;
    }

    @Override
    public void propertyChange ( PropertyChangeEvent evt )
    {
        if ( evt.getPropertyName( ).equals( Company.EMPLOYEES_PROPERTY ) )
        {
            _employeeList.setListData( _company.getEmployees( ).toArray( ) );
        }
    }

}
