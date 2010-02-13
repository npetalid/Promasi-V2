package org.promasi.shell.playmodes.singleplayerscoremode;


import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.promasi.core.SdModel;
import org.promasi.model.Accountant;
import org.promasi.model.Administrator;
import org.promasi.model.Boss;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.Project;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.EventBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.ExternalEquationBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.OutputVariableBinding;
import org.promasi.utilities.ErrorBuilder;
import org.promasi.utilities.IValidatable;


/**
 * 
 * The story contains a series of {@link Project}s that the project manager has
 * to go through.
 * 
 * @author eddiefullmetal
 * 
 */
public class Story
        implements IValidatable
{

    /**
     * The {@link Queue} with all the projects that the story has.
     */
    private final Queue<Project> _projects;

    /**
     * Each {@link Project} which {@link SdModel} needs.
     */
    private Map<Project, SdModel> _projectModelRelations;

    /**
     * Each {@link Project} which {@link OutputVariableBinding}s needs.
     */
    private Map<Project, List<OutputVariableBinding>> _projectOutputVariableBindings;

    /**
     * Each {@link Project} which {@link ExternalEquationBinding}s needs.
     */
    private Map<Project, List<ExternalEquationBinding>> _projectExternalEquationBindings;

    /**
     * Each {@link Project} which {@link EventBinding}s needs.
     */
    private Map<Project, List<EventBinding>> _projectEventBindings;

    /**
     * The {@link DifficultyLevel} of the {@link Story}.
     */
    private DifficultyLevel _difficultyLevel;

    /**
     * The {@link Boss} of this story.
     */
    private Boss _boss;

    /**
     * The {@link Administrator} of this story.
     */
    private Administrator _administrator;

    /**
     * The {@link Accountant} of this story.
     */
    private Accountant _accountant;

    /**
     * The company of the story.
     */
    private Company _company;

    /**
     * The date that the story starts.
     */
    private LocalDate _startDate;

    /**
     * All the available employees for the current story.
     */
    private List<Employee> _employees;

    /**
     * The current {@link Project}. This value is changed when the
     * {@link #getNextProject()} is called.
     */
    private Project _currentProject;

    /**
     * File that contains the description of the {@link Story}. Must be an HTML
     * file.
     */
    private File _infoFile;

    /**
     * The name of the story.
     */
    private String _name;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Story.class );

    /**
     * Initializes the object.
     */
    public Story( )
    {
        _projects = new LinkedList<Project>( );
        _projectModelRelations = new Hashtable<Project, SdModel>( );
        _projectOutputVariableBindings = new Hashtable<Project, List<OutputVariableBinding>>( );
        _projectExternalEquationBindings = new Hashtable<Project, List<ExternalEquationBinding>>( );
        _projectEventBindings = new Hashtable<Project, List<EventBinding>>( );
        _employees = new Vector<Employee>( );
    }

    /**
     * 
     * Adds the specified project to the {@link #_projects} with the last key as
     * order.
     * 
     * @param project
     *            The {@link Project} to add to the {@link #_projects}.
     * @throws NullArgumentException
     *             if the project is null.
     */
    public void addProject ( Project project )
            throws NullArgumentException
    {
        // Check arguments.
        if ( project == null )
        {
            LOGGER.error( ErrorBuilder.generateNullArgumentError( "addProject", "project" ) );
            throw new NullArgumentException( "project" );
        }
        _projects.add( project );
    }

    /**
     * @return the {@link #_projects}.
     */
    public Queue<Project> getProjects ( )
    {
        return _projects;
    }

    /**
     * Also modifies the {@link #_currentProject}.
     * 
     * @return The next {@link Project} and removes it from the
     *         {@link #_projects}.
     */
    public Project getNextProject ( )
    {
        _currentProject = _projects.poll( );
        return _currentProject;
    }

    /**
     * @return The next {@link Project} without removing it from the
     *         {@link #_projects}
     */
    public Project peekNextProject ( )
    {
        return _projects.peek( );
    }

    /**
     * @param difficultyLevel
     *            the {@link #_difficultyLevel} to set.
     */
    public void setDifficultyLevel ( DifficultyLevel difficultyLevel )
    {
        _difficultyLevel = difficultyLevel;
    }

    /**
     * @return the {@link #_difficultyLevel}.
     */
    public DifficultyLevel getDifficultyLevel ( )
    {
        return _difficultyLevel;
    }

    /**
     * @param boss
     *            the {@link #_boss} to set.
     */
    public void setBoss ( Boss boss )
    {
        _boss = boss;
    }

    /**
     * @return the {@link #_boss}.
     */
    public Boss getBoss ( )
    {
        return _boss;
    }

    /**
     * @return the {@link #_administrator}.
     */
    public Administrator getAdministrator ( )
    {
        return _administrator;
    }

    /**
     * @param administrator
     *            the {@link #_administrator} to set.
     */
    public void setAdministrator ( Administrator administrator )
    {
        _administrator = administrator;
    }

    /**
     * @return the {@link #_accountant}.
     */
    public Accountant getAccountant ( )
    {
        return _accountant;
    }

    /**
     * @param accountant
     *            the {@link #_accountant} to set.
     */
    public void setAccountant ( Accountant accountant )
    {
        _accountant = accountant;
    }

    /**
     * @param infoFile
     *            the {@link #_infoFile} to set.
     */
    public void setInfoFile ( File infoFile )
    {
        _infoFile = infoFile;
    }

    /**
     * @return the {@link #_infoFile}.
     */
    public File getInfoFile ( )
    {
        return _infoFile;
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
     * @return the {@link #_startDate}.
     */
    public LocalDate getStartDate ( )
    {
        return _startDate;
    }

    /**
     * @param startDate
     *            the {@link #_startDate} to set.
     */
    public void setStartDate ( LocalDate startDate )
    {
        _startDate = startDate;
    }

    /**
     * @return the {@link #_company}.
     */
    public Company getCompany ( )
    {
        return _company;
    }

    /**
     * @param company
     *            the {@link #_company} to set.
     */
    public void setCompany ( Company company )
    {
        _company = company;
    }

    /**
     * @param project
     *            The {@link Project} to get the model for.
     * @return The {@link SdModel} that corresponds to the specified project.
     */
    public SdModel getModel ( Project project )
    {
        return _projectModelRelations.get( project );
    }

    /**
     * @return the {@link #_employees}.
     */
    public List<Employee> getEmployees ( )
    {
        return _employees;
    }

    /**
     * @param employees
     *            the {@link #_employees} to set. If a null parameter is passed
     *            an empty {@link Vector} is created.
     */
    public void setEmployees ( List<Employee> employees )
    {
        if ( employees == null )
        {
            employees = new Vector<Employee>( );
        }
        _employees = employees;
    }

    /**
     * @param project
     *            The {@link Project} to get the {@link OutputVariableBinding}s
     *            for.
     * @return A list with the {@link OutputVariableBinding} that corresponds to
     *         the specified project.
     */
    public List<OutputVariableBinding> getOutputVariableBindings ( Project project )
    {
        return _projectOutputVariableBindings.get( project );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectOutputVariableBindings}.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectOutputVariableBindings}.
     * @param variableBinding
     *            The {@link OutputVariableBinding} to add as a value to the
     *            {@link #_projectOutputVariableBindings}.
     */
    public void addOutputVariableBinding ( Project project, OutputVariableBinding variableBinding )
    {
        List<OutputVariableBinding> variableBindings = _projectOutputVariableBindings.get( project );
        if ( variableBindings == null )
        {
            variableBindings = new Vector<OutputVariableBinding>( );
        }
        variableBindings.add( variableBinding );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectOutputVariableBindings}. If an entry
     * for the project exists it removes it and adds the a new.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectOutputVariableBindings}.
     * @param model
     *            A list with the {@link OutputVariableBinding}s to add as a
     *            value to the {@link #_projectOutputVariableBindings}.
     */
    public void setOutputVariableBindings ( Project project, List<OutputVariableBinding> variableBindings )
    {
        if ( _projectOutputVariableBindings.containsKey( project ) )
        {
            _projectOutputVariableBindings.remove( project );
        }
        _projectOutputVariableBindings.put( project, variableBindings );
    }

    /**
     * @param project
     *            The {@link Project} to get the {@link ExternalEquationBinding}
     *            s for.
     * @return A list with the {@link ExternalEquationBinding} that corresponds
     *         to the specified project.
     */
    public List<ExternalEquationBinding> getExternalEquationBindings ( Project project )
    {
        return _projectExternalEquationBindings.get( project );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectExternalEquationBindings}.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectExternalEquationBindings}.
     * @param variableBinding
     *            The {@link ExternalEquationBinding} to add as a value to the
     *            {@link #_projectExternalEquationBindings}.
     */
    public void addExternalEquationBinding ( Project project, ExternalEquationBinding equationBinding )
    {
        List<ExternalEquationBinding> equationBindings = _projectExternalEquationBindings.get( project );
        if ( equationBindings == null )
        {
            equationBindings = new Vector<ExternalEquationBinding>( );
        }
        equationBindings.add( equationBinding );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectExternalEquationBindings}. If an
     * entry for the project exists it removes it and adds the a new.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectExternalEquationBindings}.
     * @param model
     *            A list with the {@link ExternalEquationBinding}s to add as a
     *            value to the {@link #_projectExternalEquationBindings}.
     */
    public void setExternalEquationBindings ( Project project, List<ExternalEquationBinding> variableBindings )
    {
        if ( _projectExternalEquationBindings.containsKey( project ) )
        {
            _projectExternalEquationBindings.remove( project );
        }
        _projectExternalEquationBindings.put( project, variableBindings );
    }

    /**
     * @param project
     *            The {@link Project} to get the {@link EventBinding}s for.
     * @return A list with the {@link EventBinding} that corresponds to the
     *         specified project.
     */
    public List<EventBinding> getEventBindings ( Project project )
    {
        return _projectEventBindings.get( project );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectEventBindings}.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectEventBindings}.
     * @param variableBinding
     *            The {@link EventBinding} to add as a value to the
     *            {@link #_projectEventBindings}.
     */
    public void addEventBinding ( Project project, EventBinding eventBinding )
    {
        List<EventBinding> eventBindings = _projectEventBindings.get( project );
        if ( eventBindings == null )
        {
            eventBindings = new Vector<EventBinding>( );
        }
        eventBindings.add( eventBinding );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectEventBindings}. If an entry for the
     * project exists it removes it and adds the a new.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectEventBindings}.
     * @param model
     *            A list with the {@link EventBinding}s to add as a value to the
     *            {@link #_projectEventBindings}.
     */
    public void setEventBindings ( Project project, List<EventBinding> eventBindings )
    {
        if ( _projectEventBindings.containsKey( project ) )
        {
            _projectEventBindings.remove( project );
        }
        _projectEventBindings.put( project, eventBindings );
    }

    /**
     * 
     * Adds an entry to the {@link #_projectModelRelations}. If an entry for the
     * project exists it removes it and adds the a new.
     * 
     * @param project
     *            The {@link Project} to add as a key to the
     *            {@link #_projectModelRelations}.
     * @param model
     *            The {@link SdModel} to add as a value to the
     *            {@link #_projectModelRelations}.
     */
    public void addProjectModelRelation ( Project project, SdModel model )
    {
        if ( _projectModelRelations.containsKey( project ) )
        {
            _projectModelRelations.remove( project );
        }
        _projectModelRelations.put( project, model );
    }

    /**
     * @return the {@link #_currentProject}.
     */
    public Project getCurrentProject ( )
    {
        return _currentProject;
    }

    @Override
    public String toString ( )
    {
        return _name;
    }

    @Override
    public boolean isValid ( )
    {
        if ( _startDate == null )
        {
            return false;
        }
        if ( StringUtils.isBlank( _name ) )
        {
            return false;
        }
        if ( _boss == null || _administrator == null || _accountant == null )
        {
            return false;
        }
        if ( _projects.size( ) == 0 || _projectModelRelations.size( ) == 0 )
        {
            return false;
        }
        if ( _projectModelRelations.size( ) != _projects.size( ) )
        {
            return false;
        }
        return true;
    }

}
