package org.promasi.model;


import java.util.Hashtable;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;


/**
 * 
 * The accounting data of an {@link Employee}.
 * 
 * @author eddiefullmetal
 * 
 */
public class EmployeeAccountingData
{

    /**
     * The {@link Employee} that the accounting data refer to.
     */
    private Employee _employee;

    /**
     * The date that the {@link Employee} was hired.
     */
    private DateTime _hiredDate;

    /**
     * Keeps how much money the employee was paid for each day.
     */
    private Hashtable<LocalDate, Double> _moneyPaid;

    /**
     * Initializes the object.
     */
    public EmployeeAccountingData( )
    {
        _moneyPaid = new Hashtable<LocalDate, Double>( );
    }

    /**
     * Initializes the object.
     * 
     * @param employee
     *            The {@link #_employee} to set.
     * @param hiredDate
     *            The {@link #_hiredDate} to set.
     */
    public EmployeeAccountingData( Employee employee, DateTime hiredDate )
    {
        this( );
        _employee = employee;
        _hiredDate = hiredDate;
    }

    /**
     * @return The {@link #_employee}.
     */
    public Employee getEmployee ( )
    {
        return _employee;
    }

    /**
     * @param employee
     *            The {@link #_employee} to set.
     */
    public void setEmployee ( Employee employee )
    {
        _employee = employee;
    }

    /**
     * @return The {@link #_hiredDate}.
     */
    public DateTime getHiredDate ( )
    {
        return _hiredDate;
    }

    /**
     * @param hiredDate
     *            The {@link #_hiredDate} to set.
     */
    public void setHiredDate ( DateTime hiredDate )
    {
        _hiredDate = hiredDate;
    }

    /**
     * @return The {@link #_moneyPaid}.
     */
    public Hashtable<LocalDate, Double> getMoneyPaid ( )
    {
        return _moneyPaid;
    }

    /**
     * @param date
     *            The {@link LocalDate} to add to the {@link #_moneyPaid} as a
     *            key.
     * @param money
     *            The {@link LocalDate} to add to the {@link #_moneyPaid} as a
     *            value.
     */
    public void addMoneyPaid ( LocalDate date, Double money )
    {
        if ( !_moneyPaid.containsKey( date ) )
        {
            _moneyPaid.put( date, money );
        }
    }

    /**
     * @param moneyPaid
     *            The {@link #_moneyPaid} to set.
     */
    public void setMoneyPaid ( Hashtable<LocalDate, Double> moneyPaid )
    {
        _moneyPaid = moneyPaid;
    }
}
