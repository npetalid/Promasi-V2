package org.promasi.model;


import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;


/**
 * 
 * Represents a schedule of a {@link Task}.
 * 
 * @author eddiefullmetal
 * 
 */
public class TaskSchedule
{

    /**
     * The date that the task will start.
     */
    private DateTime _startDate;

    /**
     * The date that the task will end.
     */
    private DateTime _endDate;

    /**
     * The {@link Team} of the {@link TaskSchedule}.
     */
    private Team _team;

    /**
     * The predecessors of this {@link TaskSchedule}.
     */
    private List<TaskSchedule> _predecessors;

    /**
     * Initializes the object.
     */
    public TaskSchedule( )
    {
        _predecessors = new Vector<TaskSchedule>( );
    }

    /**
     * Initializes the object.
     * 
     * @param startDate
     *            The {@link #_startDate} to set.
     * @param endDate
     *            The {@link #_endDate} to set.
     */
    public TaskSchedule( DateTime startDate, DateTime endDate )
    {
        this( );
        _startDate = startDate;
        _endDate = endDate;
    }

    /**
     * @return The {@link #_startDate}.
     */
    public DateTime getStartDate ( )
    {
        return _startDate;
    }

    /**
     * @param startDate
     *            The {@link #_startDate} to set.
     */
    public void setStartDate ( DateTime startDate )
    {
        _startDate = startDate;
    }

    /**
     * @return The {@link #_endDate}.
     */
    public DateTime getEndDate ( )
    {
        return _endDate;
    }

    /**
     * @param endDate
     *            The {@link #_endDate} to set.
     */
    public void setEndDate ( DateTime endDate )
    {
        _endDate = endDate;
    }

    /**
     * @return The {@link #_team}.
     */
    public Team getTeam ( )
    {
        return _team;
    }

    /**
     * @param team
     *            The {@link #_team} to set.
     */
    public void setTeam ( Team team )
    {
        _team = team;
    }

    /**
     * @param taskSchedule
     *            The {@link TaskSchedule} to add to the {@link #_predecessors}
     *            if it doesn't exist.
     */
    public void addPredecessor ( TaskSchedule taskSchedule )
    {
        if ( !_predecessors.contains( taskSchedule ) )
        {
            _predecessors.add( taskSchedule );
        }
    }

    /**
     * @param taskSchedule
     *            The {@link TaskSchedule} to remove from the
     *            {@link #_predecessors}.
     */
    public void removePredecessor ( TaskSchedule taskSchedule )
    {
        if ( _predecessors.contains( taskSchedule ) )
        {
            _predecessors.remove( taskSchedule );
        }
    }

    /**
     * @return the {@link #_predecessors}.
     */
    public List<TaskSchedule> getPredecessors ( )
    {
        return Collections.unmodifiableList( _predecessors );
    }

    @Override
    public String toString ( )
    {
        return "Task Schedule from " + _startDate + " to " + _endDate;
    }
}
