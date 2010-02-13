package org.promasi.model;


/**
 * 
 * 
 * @author eddiefullmetal
 * 
 */
public class EmployeeTeamData
{

    /**
     * The {@link Employee} that the Team data refer to.
     */
    private Employee _employee;

    /**
     * The hours that the employee will work on this team per day.
     */
    private int _hoursPerDay;

    /**
     * The hours that the employee worked for this day.
     */
    private int _hoursWorked;

    /**
     * Initializes the object.
     * 
     * @param employee
     *            The {@link #_employee}.
     */
    public EmployeeTeamData( Employee employee )
    {
        _hoursWorked = 0;
        _hoursPerDay = 0;
        _employee = employee;
    }

    /**
     * Initializes the object.
     * 
     * @param employee
     *            The {@link #_employee}.
     * @param hoursPerDay
     *            The {@link #_hoursPerDay}.
     */
    public EmployeeTeamData( Employee employee, int hoursPerDay )
    {
        _hoursWorked = 0;
        _hoursPerDay = hoursPerDay;
        _employee = employee;
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
     * @return The {@link #_hoursPerDay}.
     */
    public int getHoursPerDay ( )
    {
        return _hoursPerDay;
    }

    /**
     * @param hours
     *            The {@link #_hoursPerDay} to set.
     */
    public void setHoursPerDay ( int hours )
    {
        _hoursPerDay = hours;
    }

    /**
     * @return The {@link #_hoursWorked}.
     */
    public int getHoursWorked ( )
    {
        return _hoursWorked;
    }

    /**
     * @param hours
     *            The {@link #_hoursWorked} to set.
     */
    public void setHoursWorked ( int hours )
    {
        _hoursWorked = hours;
    }

    /**
     * Adds an hour to the {@link #_hoursWorked}.
     */
    public void advanceHoursWorked ( )
    {
        _hoursWorked++;
    }

    /**
     * Called when a working day has started. It makes the {@link #_hoursWorked}
     * to 0.
     */
    public void workingDayStarted ( )
    {
        _hoursWorked = 0;
    }

    /**
     * @return If the employee has finished working for the day.
     */
    public boolean finishedWorking ( )
    {
        return _hoursWorked >= _hoursPerDay;
    }
}
