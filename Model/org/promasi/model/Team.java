package org.promasi.model;


import java.util.Collections;
import java.util.List;
import java.util.Vector;


/**
 * 
 * Represents a team of employees(developers,testers etc) that work on a
 * {@link TaskSchedule}.
 * 
 * @author eddiefullmetal
 * 
 */
public class Team
{

    /**
     * All the assigned employees of this team.
     */
    private List<Employee> _assignedEmployees;

    /**
     * The team data for all available employees.
     */
    private List<EmployeeTeamData> _teamData;

    /**
     * Initializes the object.
     */
    public Team( )
    {
        _assignedEmployees = new Vector<Employee>( );
        _teamData = new Vector<EmployeeTeamData>( );
    }

    /**
     * @return The {@link #_assignedEmployees}.
     */
    public List<Employee> getAssignedEmployees ( )
    {
        return Collections.unmodifiableList( _assignedEmployees );
    }

    /**
     * @return The {@link #_teamData}.
     */
    public List<EmployeeTeamData> getTeamData ( )
    {
        return Collections.unmodifiableList( _teamData );
    }

    /**
     * @param employeeTeamData
     *            The {@link EmployeeTeamData} to add to the
     *            {@link #_assignedEmployees}
     */
    public void assignEmployee ( EmployeeTeamData employeeTeamData )
    {
        _assignedEmployees.add( employeeTeamData.getEmployee( ) );
        _teamData.add( employeeTeamData );
    }

    /**
     * @param employee
     *            The {@link Employee} to get the {@link EmployeeTeamData} for.
     * @return The {@link EmployeeTeamData} for the specified employee or null
     *         if the team data for the employee do not exist.
     */
    public EmployeeTeamData getTeamData ( Employee employee )
    {
        for ( EmployeeTeamData teamData : _teamData )
        {
            if ( teamData.getEmployee( ).equals( employee ) )
            {
                return teamData;
            }
        }
        return null;
    }
}
