package org.promasi.ui.promasiui.promasidesktop.programs.planner;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.promasi.model.EmployeeTeamData;
import org.promasi.model.Task;
import org.promasi.model.TaskSchedule;


/**
 * 
 * Represents a gantt task.
 * 
 * @author eddiefullmetal
 * 
 */
public class GanttTaskSchedule
        implements PropertyChangeListener
{

    /**
     * The minimum start date.
     */
    private LocalDate _minimumStartDate;

    /**
     * The start date of the schedule.
     */
    private LocalDate _startDate;

    /**
     * The name of the schedule.
     */
    private String _name;

    /**
     * The number of work days.
     */
    private int _workDays;

    /**
     * All the predecessors of this task.
     */
    private List<GanttTaskSchedule> _predecessors;

    /**
     * The resources for this task.
     */
    private List<EmployeeTeamData> _resources;

    /**
     * The {@link PropertyChangeSupport} for this class.
     */
    private PropertyChangeSupport _changeSupport;

    /**
     * The {@link Task} that this {@link GanttTaskSchedule} refers to.
     */
    private Task _parentTask;

    /**
     * The {@link TaskSchedule} that is referenced by this
     * {@link GanttTaskSchedule}.
     */
    private TaskSchedule _referencedScheduled;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( GanttTaskSchedule.class );

    /**
     * The name of the startDate property.
     */
    public static final String START_DATE_PROPERTY = "startDate";

    /**
     * The name of the workDays property.
     */
    public static final String WORK_DAYS_PROPERTY = "workDays";

    /**
     * Initializes the object.
     */
    protected GanttTaskSchedule( )
    {
        _name = StringUtils.EMPTY;
        _predecessors = new Vector<GanttTaskSchedule>( );
        _resources = new Vector<EmployeeTeamData>( );
        _changeSupport = new PropertyChangeSupport( this );
    }

    /**
     * Initializes the object.
     * 
     * @param startDate
     *            The {@link #_startDate}.
     * @param workDays
     *            The {@link #_workDays}.
     */
    public GanttTaskSchedule( Task parentTask, LocalDate minimumStartDate, LocalDate startDate, int workDays )
    {
        this( );
        _minimumStartDate = minimumStartDate;
        _startDate = startDate;
        _workDays = workDays;
        _parentTask = parentTask;
        _name = parentTask.getName( );
    }

    /**
     * @return the {@link #_startDate}.
     */
    public LocalDate getStartDate ( )
    {
        return _startDate;
    }

    /**
     * @param startDate
     *            the {@link #_startDate} to set.
     * @throws IllegalArgumentException
     *             In case the startDate is before the date of a predecessor.
     */
    public void setStartDate ( LocalDate startDate )
            throws IllegalArgumentException
    {
        for ( GanttTaskSchedule taskSchedule : _predecessors )
        {
            if ( startDate.isBefore( taskSchedule.getEndDate( ) ) )
            {
                LOGGER.error( "Invalid start date." );
                throw new IllegalArgumentException( "Invalid start date" );
            }
        }
        LocalDate oldValue = _startDate;
        _startDate = startDate;
        _changeSupport.firePropertyChange( START_DATE_PROPERTY, oldValue, _startDate );
    }

    /**
     * @return the {@link #_endDate}.
     */
    public LocalDate getEndDate ( )
    {
        // Work days is 1 based.The start date of the schedule also counts so
        // its 0 based.
        return _startDate.plusDays( _workDays - 1 );
    }

    /**
     * @return the {@link #_name}.
     */
    public String getName ( )
    {
        return _name;
    }

    /**
     * @param name
     *            the {@link #_name} to set.
     */
    public void setName ( String name )
    {
        _name = name;
    }

    /**
     * @param days
     *            The days to add to the end date.
     * @throws IllegalArgumentException
     *             In case the days in 0 or negative.
     */
    public void setWork ( int days )
            throws IllegalArgumentException
    {
        if ( days < 1 )
        {
            LOGGER.error( "Negativer day number at setWork" );
            throw new IllegalArgumentException( "Negativer day number at setWork" );
        }
        int oldValue = _workDays;
        _workDays = days;
        if ( _parentTask.getProject( ).getEndDate( ).toLocalDate( ).compareTo( getEndDate( ) ) < 0 )
        {
            _workDays = oldValue;
            LOGGER.error( "End date is after project deadline" );
            throw new IllegalArgumentException( "End date is after project deadline" );
        }
        _changeSupport.firePropertyChange( WORK_DAYS_PROPERTY, oldValue, _workDays );
    }

    /**
     * @return The number of work days.
     */
    public int getWork ( )
    {
        return _workDays;
    }

    /**
     * @return the {@link #_predecessors}.
     */
    public List<GanttTaskSchedule> getPredecessors ( )
    {
        return _predecessors;
    }

    /**
     * @param taskSchedule
     *            The {@link GanttTaskSchedule} to add to the
     *            {@link #_predecessors}.
     * @throws IllegalArgumentException
     *             In case a relation already exists.
     */
    public void addPredecessor ( GanttTaskSchedule taskSchedule )
            throws IllegalArgumentException
    {
        if ( !_predecessors.contains( taskSchedule ) )
        {
            if ( taskSchedule.getPredecessors( ).contains( this ) )
            {
                LOGGER.error( "Could not add a predecessor relation, because the tasks are already related." );
                throw new IllegalArgumentException( "Could not add a predecessor relation, because the tasks are already related." );
            }
            if ( taskSchedule.getEndDate( ).isAfter( _startDate.minusDays( 1 ) ) )
            {
                _startDate = taskSchedule.getEndDate( ).plusDays( 1 );
            }
            _predecessors.add( taskSchedule );
            taskSchedule.addPropertyChangeListener( this );
        }
    }

    /**
     * @param taskSchedule
     *            The {@link GanttTaskSchedule} to remove from the
     *            {@link #_predecessors}.
     */
    public void removePredecessor ( GanttTaskSchedule taskSchedule )
    {
        if ( _predecessors.contains( taskSchedule ) )
        {
            _predecessors.remove( taskSchedule );
            taskSchedule.removePropertyChangeListener( this );
            recalculateStartDate( );
        }
    }

    /**
     * @param predecessors
     *            the {@link #_predecessors} to set.
     */
    public void setPredecessors ( List<GanttTaskSchedule> predecessors )
    {
        _predecessors = predecessors;
    }

    /**
     * @return the {@link #_minimumStartDate}.
     */
    public LocalDate getMinimumStartDate ( )
    {
        return _minimumStartDate;
    }

    /**
     * @return the {@link #_resources}.
     */
    public List<EmployeeTeamData> getResources ( )
    {
        return _resources;
    }

    /**
     * @param resource
     *            The {@link EmployeeTeamData} to add to the {@link #_resources}
     * @throws IllegalArgumentException
     *             In case the employee exists as a resource.
     */
    public void addResource ( EmployeeTeamData resource )
            throws IllegalArgumentException
    {
        if ( !_resources.contains( resource ) )
        {
            for ( EmployeeTeamData teamData : _resources )
            {
                if ( teamData.getEmployee( ).equals( resource.getEmployee( ) ) )
                {
                    LOGGER.error( "Employee is already added as a resource" );
                    throw new IllegalArgumentException( "Employee is already added as a resource" );
                }
            }
            _resources.add( resource );
        }
    }

    /**
     * @param resource
     *            The {@link EmployeeTeamData} to remove from the
     *            {@link #_resources}.
     */
    public void removeResource ( EmployeeTeamData resource )
    {
        if ( _resources.contains( resource ) )
        {
            _resources.remove( resource );
        }
    }

    /**
     * @param resources
     *            the {@link #_resources} to set.
     */
    public void setResources ( List<EmployeeTeamData> resources )
    {
        _resources = resources;
    }

    /**
     * @param minimumStartDate
     *            the {@link #_minimumStartDate} to set.
     */
    public void setMinimumStartDate ( LocalDate minimumStartDate )
    {
        _minimumStartDate = minimumStartDate;
    }

    /**
     * @param listener
     *            The {@link PropertyChangeListener} to add to the
     *            {@link #_changeSupport}.
     */
    public void addPropertyChangeListener ( PropertyChangeListener listener )
    {
        _changeSupport.addPropertyChangeListener( listener );
    }

    /**
     * @param listener
     *            The {@link PropertyChangeListener} to remove from the
     *            {@link #_changeSupport}.
     */
    public void removePropertyChangeListener ( PropertyChangeListener listener )
    {
        _changeSupport.removePropertyChangeListener( listener );
    }

    @Override
    public void propertyChange ( PropertyChangeEvent evt )
    {
        if ( evt.getPropertyName( ).equals( WORK_DAYS_PROPERTY ) )
        {
            recalculateStartDate( );
        }
        else if ( evt.getPropertyName( ).equals( START_DATE_PROPERTY ) )
        {
            recalculateStartDate( );
        }
    }

    /**
     * recalculates the start date according to the predecessors.
     */
    private void recalculateStartDate ( )
    {
        LocalDate finalStartDate = _minimumStartDate;
        for ( GanttTaskSchedule predecessorTaskSchedule : _predecessors )
        {
            if ( predecessorTaskSchedule.getEndDate( ).isAfter( finalStartDate ) )
            {
                finalStartDate = predecessorTaskSchedule.getEndDate( );
            }
        }
        setStartDate( finalStartDate.plusDays( 1 ) );
    }

    /**
     * @return the {@link #_parentTask}.
     */
    public Task getParentTask ( )
    {
        return _parentTask;
    }

    /**
     * @return the {@link #_referencedScheduled}.
     */
    public TaskSchedule getReferencedScheduled ( )
    {
        return _referencedScheduled;
    }

    /**
     * @param referencedScheduled
     *            the {@link #_referencedScheduled} to set.
     */
    public void setReferencedScheduled ( TaskSchedule referencedScheduled )
    {
        _referencedScheduled = referencedScheduled;
    }

    /**
     * @param parentTask
     *            the {@link #_parentTask} to set.
     */
    public void setParentTask ( Task parentTask )
    {
        _parentTask = parentTask;
    }

    @Override
    public String toString ( )
    {
        return _name;
    }

}
