package org.promasi.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.joda.time.DateTime;


/**
 * 
 * Represents a project.
 * 
 * @author eddiefullmetal
 * 
 */
public class Project
{

    /**
     * The name of the project.
     */
    private String _name;

    /**
     * The description of the project.
     */
    private String _description;

    /**
     * The budget of the project.
     */
    private double _budget;

    /**
     * The date that the project will start.
     */
    private DateTime _startDate;

    /**
     * The date the the will end(deadline).
     */
    private DateTime _endDate;

    /**
     * The completed percentage.
     */
    private double _percentageCompleted;

    /**
     * The quality of the project.
     */
    private double _quality;

    /**
     * The prestige points represents the score for this project. The score has
     * a range 0.0 - 10.0.
     */
    private double _prestigePoints;

    /**
     * The tasks for this project.
     */
    private List<Task> _tasks;

    /**
     * The {@link PropertyChangeSupport}.
     */
    private PropertyChangeSupport _changeSupport;

    public static String NAME_PROPERTY = "name";
    public static String DESCRIPTION_PROPERTY = "description";
    public static String BUDGET_PROPERTY = "budget";
    public static String START_DATE_PROPERTY = "startDate";
    public static String END_DATE_PROPERTY = "endDate";
    public static String PERCENTAGE_COMPLETED_PROPERTY = "percentageCompleted";
    public static String PRESTIGE_POINTS_PROPERTY = "prestigePoints";
    public static String TASKS_PROPERTY = "tasks";
    public static String QUALITY_PROPERTY = "quality";

    /**
     * Initializes the object.
     */
    public Project( )
    {
        _changeSupport = new PropertyChangeSupport( this );
        _tasks = new Vector<Task>( );
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
        String oldValue = _name;
        _name = name;
        _changeSupport.firePropertyChange( NAME_PROPERTY, oldValue, _name );
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
        String oldValue = _description;
        _description = description;
        _changeSupport.firePropertyChange( DESCRIPTION_PROPERTY, oldValue, _description );
    }

    /**
     * @return The {@link #_budget}.
     */
    public double getBudget ( )
    {
        return _budget;
    }

    /**
     * @param budget
     *            The {@link #_budget} to set.
     */
    public void setBudget ( double budget )
    {
        double oldValue = _budget;
        _budget = budget;
        _changeSupport.firePropertyChange( BUDGET_PROPERTY, oldValue, _budget );
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
        DateTime oldValue = new DateTime( _startDate );
        _startDate = startDate;
        _changeSupport.firePropertyChange( START_DATE_PROPERTY, oldValue, _startDate );
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
     *            The {@link #_endDate}.
     */
    public void setEndDate ( DateTime endDate )
    {
        DateTime oldValue = new DateTime( _endDate );
        _endDate = endDate;
        _changeSupport.firePropertyChange( END_DATE_PROPERTY, oldValue, _endDate );
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
        double oldValue = _percentageCompleted;
        _percentageCompleted = percentageCompleted;
        _changeSupport.firePropertyChange( PERCENTAGE_COMPLETED_PROPERTY, oldValue, _percentageCompleted );
    }

    /**
     * @return the {@link #_quality}.
     */
    public double getQuality ( )
    {
        return _quality;
    }

    /**
     * @param quality
     *            the {@link #_quality} to set.
     */
    public void setQuality ( double quality )
    {
        _quality = quality;
    }

    /**
     * @return The {@link #_prestigePoints}.
     */
    public double getPrestigePoints ( )
    {
        return _prestigePoints;
    }

    /**
     * @param prestigePoints
     *            The {@link #_prestigePoints} to set.
     */
    public void setPrestigePoints ( double prestigePoints )
    {
        double oldValue = _prestigePoints;
        _prestigePoints = prestigePoints;
        _changeSupport.firePropertyChange( PRESTIGE_POINTS_PROPERTY, oldValue, _prestigePoints );
    }

    /**
     * @return The {@link #_tasks}. The list is read only.
     */
    public List<Task> getTasks ( )
    {
        return Collections.unmodifiableList( _tasks );
    }

    /**
     * @param task
     *            The {@link Task} to add to the {@link #_tasks}.
     */
    public void addTask ( Task task )
    {
        List<Task> oldValue = Collections.unmodifiableList( new Vector<Task>( _tasks ) );
        _tasks.add( task );
        task.setProject( this );
        _changeSupport.firePropertyChange( TASKS_PROPERTY, oldValue, Collections.unmodifiableList( _tasks ) );
    }

    /**
     * @param tasks
     *            The {@link #_tasks} to set.
     */
    public void setTasks ( List<Task> tasks )
    {
        List<Task> oldValue = Collections.unmodifiableList( new Vector<Task>( _tasks ) );
        _tasks = tasks;
        _changeSupport.firePropertyChange( TASKS_PROPERTY, oldValue, Collections.unmodifiableList( _tasks ) );
    }

    /**
     * @param listener
     *            The {@link PropertyChangeListener} to add.
     */
    public void addPropertyChangeListener ( PropertyChangeListener listener )
    {
        if ( listener != null )
        {
            _changeSupport.addPropertyChangeListener( listener );
        }
    }

    /**
     * @param listener
     *            The {@link PropertyChangeListener} to remove.
     */
    public void removePropertyChangeListener ( PropertyChangeListener listener )
    {
        if ( listener != null )
        {
            _changeSupport.removePropertyChangeListener( listener );
        }
    }

    /**
     * @return the {@link #_changeSupport}.
     */
    protected PropertyChangeSupport getChangeSupport ( )
    {
        return _changeSupport;
    }

    @Override
    public String toString ( )
    {
        return _name;
    }
}
