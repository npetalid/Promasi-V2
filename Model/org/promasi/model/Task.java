package org.promasi.model;


import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;


/**
 * Represents a task of a project. A task can be for example :
 * <ul>
 * <li>develop(start writing code)</li>
 * <li>review(review the code in order to find bugs)</li>
 * </ul>
 * etc.
 * 
 * @author eddiefullmetal
 * 
 */
public class Task
{

    /**
     * The name of the task.
     */
    private String _name;

    /**
     * The description of the task.
     */
    private String _description;

    /**
     * The completed percentage.
     */
    private double _percentageCompleted;

    /**
     * All the schedules for this task.
     */
    private List<TaskSchedule> _taskSchedules;

    /**
     * The {@link Project} that this {@link Task} belongs to.
     */
    private Project _project;

    /**
     * Initializes the object.
     */
    public Task( )
    {
        _taskSchedules = new Vector<TaskSchedule>( );
    }

    /**
     * @return The {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * @param name
     *            The {@link #_name} to set.
     */
    public void setName ( String name )
    {
        _name = name;
    }

    /**
     * @return The {@link #_description}.
     */
    public String getDescription ( )
    {
        return _description;
    }

    /**
     * @param description
     *            The {@link #_description} to set.
     */
    public void setDescription ( String description )
    {
        _description = description;
    }

    /**
     * @return The {@link #_percentageCompleted}.
     */
    public double getPercentageCompleted ( )
    {
        return _percentageCompleted;
    }

    /**
     * @param percentageCompleted
     *            The {@link #_percentageCompleted} to set.
     */
    public void setPercentageCompleted ( double percentageCompleted )
    {
        _percentageCompleted = percentageCompleted;
    }

    /**
     * @return the {@link #_project}.
     */
    public Project getProject ( )
    {
        return _project;
    }

    /**
     * @param project
     *            the {@link #_project} to set.
     */
    public void setProject ( Project project )
    {
        _project = project;
    }

    /**
     * @return The {@link #_taskSchedules}.
     */
    public List<TaskSchedule> getTaskSchedules ( )
    {
        return Collections.unmodifiableList( _taskSchedules );
    }

    /**
     * @param taskSchedule
     *            The {@link TaskSchedule} to add to the {@link #_taskSchedules}
     *            if it doesn't exist.
     */
    public void addTaskSchedule ( TaskSchedule taskSchedule )
    {
        if ( !_taskSchedules.contains( taskSchedule ) )
        {
            _taskSchedules.add( taskSchedule );
        }
    }

    /**
     * @param taskSchedule
     *            The {@link TaskSchedule} to remove from the
     *            {@link #_taskSchedules}
     */
    public void removeTaskSchedule ( TaskSchedule taskSchedule )
    {
        if ( _taskSchedules.contains( taskSchedule ) )
        {
            _taskSchedules.remove( taskSchedule );
        }
    }

    /**
     * @return The current schedule according to the current {@link Clock} time.
     */
    public TaskSchedule getCurrentSchedule ( )
    {
        DateTime currentDataTime = Clock.getInstance( ).getCurrentDateTime( );
        for ( TaskSchedule schedule : _taskSchedules )
        {
            if ( ( schedule.getStartDate( ).isBefore( currentDataTime ) || schedule.getStartDate( ).isEqual( currentDataTime ) )
                    && schedule.getEndDate( ).isAfter( currentDataTime ) )
            {
                return schedule;
            }
        }

        return null;
    }

    @Override
    public String toString ( )
    {
        return _name;
    }

}
