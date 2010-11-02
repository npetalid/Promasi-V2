package org.promasi.model;


import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.promasi.utilities.ErrorBuilder;


/**
 * 
 * Represents the accountant of the company. The accountant is responsible for
 * hiring\firing and paying the employees.
 * 
 * @author eddiefullmetal
 * 
 */
public class Accountant extends Person
{
	/**
     * The {@link Company} that the accountant works for.
     */
    private Company _workingCompany;

    /**
     * 
     */
    public static final String CONST_ACCOUNTANT_PERSON_ID="ACCOUNTANT";
    
    /**
     * The {@link EmployeeAccountingData} that are kept for every hired
     * employee.
     */
    private Hashtable<Employee, EmployeeAccountingData> _employeeAccountingData;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Accountant.class );

    /**
     * Initializes the object.
     */
    public Accountant( )
    {
        super("", "", CONST_ACCOUNTANT_PERSON_ID );
        _employeeAccountingData = new Hashtable<Employee, EmployeeAccountingData>( );
    }

    /**
     * Initializes the object. Calls the {@link Person#Person(String, String)}.
     */
    public Accountant( String name, String lastName )throws NullArgumentException
    {
        super( name, lastName,CONST_ACCOUNTANT_PERSON_ID);
    }

    /**
     * @return The {@link #_workingCompany}.
     */
    public Company getWorkingCompany ( )
    {
        return _workingCompany;
    }

    /**
     * @param company
     *            The {@link #_workingCompany} to set.
     */
    public void setWorkingCompany ( Company company )
    {
        _workingCompany = company;
    }

    /**
     * @return The {@link #_employeeAccountingData}.
     */
    public Hashtable<Employee, EmployeeAccountingData> getEmployeeAccountingData ( )
    {
        return _employeeAccountingData;
    }

    /**
     * @param employeeAccountingData
     *            The {@link #_employeeAccountingData} to set.
     */
    public void setEmployeeAccountingData ( Hashtable<Employee, EmployeeAccountingData> employeeAccountingData )
    {
        _employeeAccountingData = employeeAccountingData;
    }

    /**
     * @param employee
     *            The {@link Employee} to get the {@link EmployeeAccountingData}
     *            for.
     * @return The {@link EmployeeAccountingData} for the employee, null if the
     *         {@link EmployeeAccountingData} for the employee does not exist
     */
    public EmployeeAccountingData getAccountingData ( Employee employee )
    {
        return _employeeAccountingData.get( employee );
    }

    /**
     * 
     * @param employee
     * @return
     */
    public boolean isEmployeeHired(Employee employee)
    {
    	return _employeeAccountingData.containsKey(employee);
    }
    
    /**
     * Hires the employee if not already employed.
     * 
     * @param employee
     *            The employee to hire.
     * 
     * @throws NullArgumentException
     */
    public boolean hireEmployee ( Employee employee ) throws NullArgumentException
    {
        if ( employee == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "hireEmployee", "employee" ) );
            throw new NullArgumentException( "Wrong argument employee==null" );
        }

        // Create the EmployeeAccountingData.
        DateTime currentDateTime = Clock.getInstance( ).getCurrentDateTime( ).toDateTime( );
        EmployeeAccountingData accountingData = new EmployeeAccountingData( employee, currentDateTime );
        
        if(!_employeeAccountingData.contains(employee)){
        	_employeeAccountingData.put(employee, accountingData);
        	// Add the employee to the company employees.
        	_workingCompany.addEmployee( employee );
        	// Notify.
        	_workingCompany.getNotifier( ).employeeHired( employee );
        	LOGGER.info( "Employee " + employee + " hired at " + currentDateTime.toString( ) );
        }else{
            return false;
        }
        
        return true;
    }

    /**
     * Calculates the daily salaries of all working employees.
     */
    public double calculateDailySalaries ( )
    {
        List<Employee> employees = new Vector<Employee>( _workingCompany.getEmployees( ) );
        double total = 0;
        for ( Employee employee : employees )
        {
            double salary = employee.getSalary( );
            int workingDays = _workingCompany.getWorkingDays( );
            double dailySalary = salary / workingDays;
            total += dailySalary;
        }
        return Math.round( total );
    }
}
