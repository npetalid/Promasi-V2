package org.promasi.shell;


import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Clock;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.Message;
import org.promasi.model.Project;
import org.promasi.shell.model.communication.JXPathResolver;
import org.promasi.shell.model.communication.ResolverFactory;

/**
 *
 * Controls the current session of the user.
 *
 * @author eddiefullmetal
 *
 */
public final class Shell
{

    /**
     * The current {@link IPlayMode}.
     */
    private IPlayMode _currentPlayMode;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Shell.class );

    /**
     * Initializes the object.
     */
    public Shell(IPlayMode playMode )throws NullArgumentException
    {
    	if(playMode==null)
    	{
    		throw new NullArgumentException("Wrong argument playMode==null");
    	}
    	
        ResolverFactory.registerResolver( new JXPathResolver( ) );
        
        _currentPlayMode=playMode;
    }

    /**
     * 
     * @param project
     * @return
     */
    public SdModel getModelForProject(Project project)
    {
    	return _currentPlayMode.getModelForProject(project);
    }
    
    /**
     * 
     * @param project
     * @return
     */
    public IStatePersister getPersisterForProject(Project project)
    {
    	return _currentPlayMode.getPersisterForProject(project);
    }
 
    /**
     * 
     * @return
     */
    public Company getCompany()
    {
    	return _currentPlayMode.getCompany();
    }
 
    
    /**
     * Shows the {@link IMainFrame} and starts the play mode.
     *
     * @throws ConfigurationException
     */
    public void start() throws ConfigurationException
    {
        _currentPlayMode.start();
    }

    /**
     * @return All the available employees in the system(Hired and Free).
     */
    public Map<Integer,Employee> getAllEmployees ( )
    {
        return _currentPlayMode.getAllEmployees( );
    }

    /**
     * @return All the hired employees of the {@link #_company}.
     */
    public List<Employee> getHiredEmployees ( )
    {
        return _currentPlayMode.getCompany().getEmployees( );
    }

    /**
     * @return The current {@link Project}
     */
    public Project getCurrentProject ( )
    {
        return _currentPlayMode.getCompany().getCurrentProject( );
    }

    /**
     * Hires the employee.
     *
     * @param employee
     *            The {@link Employee} to hire.
     */
    public void hireEmployee ( Employee employee )
    {
        LOGGER.info( "Hiring employee " + employee );
        _currentPlayMode.getCompany().getAccountant( ).hireEmployee( employee );
    }

    /**
     * Adds the {@link IShellListener} to the {@link #_listeners}.
     */
    public void addListener ( IShellListener listener )
    {
        _currentPlayMode.addListener(listener);
    }

    /**
     * Moves the {@link Clock} to the start date of the current project. If no
     * current project is available or the project has already started the
     * method does nothing.
     */
    public void jumpToProjectDate ( )
    {
        Project project = _currentPlayMode.getCompany().getCurrentProject( );
        if ( project != null )
        {
            LOGGER.info( "Moving to project date " + project.getStartDate( ) );
            int days = project.getStartDate( ).getDayOfYear( ) - Clock.getInstance( ).getCurrentDateTime( ).getDayOfYear( );
            // Means that the project hasn't started yet.
            if ( days > 0 )
            {
                for ( int i = 0; i < days; i++ )
                {
                    Clock.getInstance( ).performDayChange( _currentPlayMode.getCompany().getStartTimeAsLocalTime( ) );
                }
            }
        }
    }

    /**
     * @param message
     *            The message to send.
     */
    public void sendMail ( Message message )
    {
    	_currentPlayMode.getCompany().getMessagingServer( ).sendMessage( message );
    }
}
