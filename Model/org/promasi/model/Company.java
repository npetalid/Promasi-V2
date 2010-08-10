package org.promasi.model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalTime;
import org.promasi.utilities.Cloner;
import org.promasi.utilities.ICloneable;


/**
 * 
 * Represents the company.
 * 
 * @author eddiefullmetal
 * 
 */
public class Company implements ICloneable<Company>, Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * The name of the company.
     */
    private String _name;

    /**
     * The prestige points of the company. Prestige points represent the overall
     * score of the company.
     */
    private double _prestigePoints;

    /**
     * Description about the company.
     */
    private String _description;

    /**
     * The Boss of the company.
     */
    private Boss _boss;

    /**
     * The Project manager of the company.
     */
    private ProjectManager _projectManager;

    /**
     * The Accountant of the company.
     */
    private Accountant _accountant;

    /**
     * The Administrator of the company.
     */
    private Administrator _administrator;

    /**
     * All the hired employees of the company.
     */
    private List<Employee> _employees;

    /**
     * The {@link MessagingServer} of the company.
     */
    private MessagingServer _messagingServer;

    /**
     * The {@link Notifier} of this company.
     */
    private Notifier _notifier;

    /**
     * The current {@link Project} of the company.
     */
    private Project _currentProject;

    /**
     * The time that the company starts working.
     */
    private LocalTime _startTime;

    /**
     * The time that the company stops working.
     */
    private LocalTime _endTime;

    /**
     * The {@link PropertyChangeSupport} for this class.
     */
    private PropertyChangeSupport _changeSupport;

    /**
     * Defines the number of working days for each month.
     */
    private int _workingDays = 23;// TODO add this to the configuration.

    public static String NAME_PROPERTY = "name";
    public static String PRESTIGE_POINTS_PROPERTY = "prestigePoints";
    public static String DESCRIPTION_PROPERTY = "description";
    public static String BOSS_PROPERTY = "boss";
    public static String PROJECT_MANAGER_PROPERTY = "projectManager";
    public static String ACCOUNTANT_PROPERTY = "accountant";
    public static String ADMINISTRATOR_PROPERTY = "administrator";
    public static String EMPLOYEES_PROPERTY = "employees";
    public static String CURRENT_PROJECT_PROPERTY = "currentProject";
    public static String START_TIME_PROPERTY = "startTime";
    public static String END_TIME_PROPERTY = "endTime";
    public static String WORKING_DAYS_PROPERTY = "workingDays";

    /**
     * Initializes the object.
     */
    public Company( )
    {
        _changeSupport = new PropertyChangeSupport( this );
        _prestigePoints = 0.0d;
        _name = StringUtils.EMPTY;
        _employees = new Vector<Employee>( );
        _notifier = new Notifier( );
        _messagingServer = new MessagingServer( );
    }

    /**
     * 
     * Initializes the object.
     * 
     * @param name
     *            The {@link #_name} to set.
     */
    public Company( String name )
    {
        this( );
        _name = name;
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
     * @return the {@link #_description}.
     */
    public String getDescription ( )
    {
        return _description;
    }

    /**
     * @param description
     *            the {@link #_description} to set.
     */
    public void setDescription ( String description )
    {
        _description = description;
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
     * @return The {@link #_boss}.
     */
    public Boss getBoss ( )
    {
        return _boss;
    }

    /**
     * @param boss
     *            The {@link #_boss} to set.
     */
    public void setBoss ( Boss boss )
    {
        Person oldValue = Cloner.clone( _boss );
        _boss = boss;
        _changeSupport.firePropertyChange( BOSS_PROPERTY, oldValue, _boss );
    }

    /**
     * @return The {@link #_projectManager}.
     */
    public ProjectManager getProjectManager ( )
    {
        return _projectManager;
    }

    /**
     * @param projectManager
     *            The {@link #_projectManager} to set.
     */
    public void setProjectManager ( ProjectManager projectManager )
    {
        Person oldValue = Cloner.clone( _projectManager );
        _projectManager = projectManager;
        _changeSupport.firePropertyChange( PROJECT_MANAGER_PROPERTY, oldValue, _projectManager );
    }

    /**
     * @return The {@link #_accountant}.
     */
    public Accountant getAccountant ( )
    {
        return _accountant;
    }

    /**
     * @param accountant
     *            The {@link #_accountant} to set.
     */
    public void setAccountant ( Accountant accountant )
    {
        Person oldValue = Cloner.clone( _accountant );
        _accountant = accountant;
        _changeSupport.firePropertyChange( ACCOUNTANT_PROPERTY, oldValue, _accountant );
    }

    /**
     * @return The {@link #_administrator}.
     */
    public Administrator getAdministrator ( )
    {
        return _administrator;
    }

    /**
     * @param administrator
     *            The {@link #_administrator} to set.
     */
    public void setAdministrator ( Administrator administrator )
    {
        Person oldValue = Cloner.clone( _administrator );
        _administrator = administrator;
        _changeSupport.firePropertyChange( ADMINISTRATOR_PROPERTY, oldValue, _administrator );
    }

    /**
     * @return The {@link #_employees}. The list cannot be modified.
     */
    public List<Employee> getEmployees ( )
    {
        return Collections.unmodifiableList( _employees );
    }

    /**
     * @param employee
     *            The {@link Employee} to add to the {@link #_employees}.
     */
    public void addEmployee ( Employee employee )
    {
        List<Employee> oldValue = Collections.unmodifiableList( new Vector<Employee>( _employees ) );
        _employees.add( employee );
        _changeSupport.firePropertyChange( EMPLOYEES_PROPERTY, oldValue, Collections.unmodifiableList( _employees ) );
    }

    /**
     * @param employees
     *            The {@link #_employees} to set.
     */
    public void setEmployees ( List<Employee> employees )
    {
        List<Employee> oldValue = Collections.unmodifiableList( new Vector<Employee>( _employees ) );
        _employees = employees;
        _changeSupport.firePropertyChange( EMPLOYEES_PROPERTY, oldValue, Collections.unmodifiableList( _employees ) );
    }

    /**
     * @return the {@link #_messagingServer}
     */
    public MessagingServer getMessagingServer ( )
    {
        return _messagingServer;
    }

    /**
     * @return the {@link #_notifier}
     */
    public Notifier getNotifier ( )
    {
        return _notifier;
    }

    /**
     * @return The {@link #_currentProject}.
     */
    public Project getCurrentProject ( )
    {
        return _currentProject;
    }

    /**
     * @param project
     *            The {@link #_currentProject} to set.
     */
    public void setCurrentProject ( Project project )
    {
        Project oldValue = _currentProject;
        _currentProject = project;
        _changeSupport.firePropertyChange( CURRENT_PROJECT_PROPERTY, oldValue, _currentProject );
    }

    /**
     * @return the {@link #_startTime}.
     */
    public LocalTime getStartTime ( )
    {
        return _startTime;
    }

    /**
     * @param startTime
     *            the {@link #_startTime} to set.
     */
    public void setStartTime ( LocalTime startTime )
    {
        LocalTime oldValue = _startTime;
        _startTime = startTime;
        _changeSupport.firePropertyChange( START_TIME_PROPERTY, oldValue, _startTime );
    }

    /**
     * @return the {@link #_workingDays}.
     */
    public int getWorkingDays ( )
    {
        return _workingDays;
    }

    /**
     * @param workingDays
     *            the {@link #_workingDays} to set.
     */
    public void setWorkingDays ( int workingDays )
    {
        int oldValue = _workingDays;
        _workingDays = workingDays;
        _changeSupport.firePropertyChange( WORKING_DAYS_PROPERTY, oldValue, _workingDays );
    }

    /**
     * @return the {@link #_endTime}.
     */
    public LocalTime getEndTime ( )
    {
        return _endTime;
    }

    /**
     * @param endTime
     *            the {@link #_endTime} to set.
     */
    public void setEndTime ( LocalTime endTime )
    {
        LocalTime oldValue = _endTime;
        _endTime = endTime;
        _changeSupport.firePropertyChange( END_TIME_PROPERTY, oldValue, _endTime );
    }

    /**
     * Assigns a {@link Project} to this {@link Company}, and notifies the
     * {@link Notifier}.
     * 
     * @param project
     *            The {@link Project} to assign to the company.
     */
    public void assignProject ( Project project )
    {
        setCurrentProject( project );
        _notifier.projectAssigned( project );
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
    public Company copy ( )
    {
        try
        {
            return (Company) clone( );
        }
        catch ( CloneNotSupportedException e )
        {
            // Not going to happen since the class supports the clonable
            // interface.
            return null;
        }
    }
    
}
