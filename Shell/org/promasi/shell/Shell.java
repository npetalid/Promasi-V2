package org.promasi.shell;


import java.util.List;
import java.util.Vector;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.promasi.communication.ICommunicator;
import org.promasi.model.Clock;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.INotifierListener;
import org.promasi.model.ITimerTask;
import org.promasi.model.Message;
import org.promasi.model.Project;
import org.promasi.model.Timer;
import org.promasi.shell.model.communication.JXPathResolver;
import org.promasi.shell.model.communication.ModelMessageReceiver;
import org.promasi.shell.model.communication.ResolverFactory;

/**
 *
 * Controls the current session of the user.
 *
 * @author eddiefullmetal
 *
 */
public final class Shell
        implements INotifierListener
{

    /**
     * The current {@link IPlayMode}.
     */
    private IPlayMode _currentPlayMode;

    /**
     * The company of the user(project manager).
     */
    private Company _company;


    /**
     * All the registered {@link IShellListener}s.
     */
    private List<IShellListener> _listeners;

    /**
     * The current model message receiver.
     */
    private ModelMessageReceiver _modelMessageReceiver;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( Shell.class );

    /**
     * Initializes the object.
     */
    public Shell( )
    {
        _listeners = new Vector<IShellListener>( );
        _modelMessageReceiver=new ModelMessageReceiver();
    }


    /**
     * @return the {@link #_currentPlayMode}.
     */
    public IPlayMode getCurrentPlayMode ( )
    {
        return _currentPlayMode;
    }

    /**
     *
     * Sets the {@link #_currentPlayMode} and initializes the play mode.
     *
     * @param currentPlayMode
     *            the {@link #_currentPlayMode} to set.
     * @throws NullArgumentException
     */
    public void setCurrentPlayMode ( IPlayMode currentPlayMode ) throws NullArgumentException
    {
    	if(currentPlayMode==null)
    	{
    		throw new NullArgumentException("Wrong argument currentPlayMode==null");
    	}
    	
        _currentPlayMode = currentPlayMode;
    }

    /**
     * @return the {@link #_company}.
     */
    public Company getCompany ( )
    {
        return _company;
    }

    /**
     *
     * Sets the {@link #_company} and the context of the
     * {@link ModelMessageReceiver}.
     *
     * @param company
     *            the {@link #_company} to set.
     */
    public void setCompany ( Company company )
    {
        _company = company;
        // Initialize model objects.
        _modelMessageReceiver.setContext( company );
        ResolverFactory.registerResolver( new JXPathResolver( ) );
        _company.getNotifier( ).addListener( this );
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
    public List<Employee> getAllEmployees ( )
    {
        return _currentPlayMode.getAllEmployees( );
    }

    /**
     * @return All the hired employees of the {@link #_company}.
     */
    public List<Employee> getHiredEmployees ( )
    {
        return _company.getEmployees( );
    }

    /**
     * @return The current {@link Project}
     */
    public Project getCurrentProject ( )
    {
        return _company.getCurrentProject( );
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
        _company.getAccountant( ).hireEmployee( employee );
    }

    /**
     * Adds the {@link IShellListener} to the {@link #_listeners}.
     */
    public void addListener ( IShellListener listener )
    {
        if ( !_listeners.contains( listener ) )
        {
            _listeners.add( listener );
        }
    }

    /**
    *
    * @param communicator
    */
   public void registerCommunicator(ICommunicator communicator)
   {
	   _currentPlayMode.registerCommunicator(communicator);
   }

    /**
     * Start a timer to notify all the {@link IShellListener}s when the start
     * date of the project has come.
     */
    @Override
    public void projectAssigned ( Project project )
    {
        ProjectStartTimerTask startTask = new ProjectStartTimerTask( project );
        startTask.start( );
        ProjectEndTimerTask endTask = new ProjectEndTimerTask( project );
        endTask.start( );
        for ( IShellListener listener : _listeners )
        {
            listener.projectAssigned( project );
        }
    }

    @Override
    public void employeeHired ( Employee employee )
    {
        for ( IShellListener listener : _listeners )
        {
            listener.employeeHired( employee );
        }
    }

    /**
     * Moves the {@link Clock} to the start date of the current project. If no
     * current project is available or the project has already started the
     * method does nothing.
     */
    public void jumpToProjectDate ( )
    {
        Project project = _company.getCurrentProject( );
        if ( project != null )
        {
            LOGGER.info( "Moving to project date " + project.getStartDate( ) );
            int days = project.getStartDate( ).getDayOfYear( ) - Clock.getInstance( ).getCurrentDateTime( ).getDayOfYear( );
            // Means that the project hasn't started yet.
            if ( days > 0 )
            {
                for ( int i = 0; i < days; i++ )
                {
                    Clock.getInstance( ).performDayChange( _company.getStartTimeAsLocalTime( ) );
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
        _company.getMessagingServer( ).sendMessage( message );
    }

    /**
     *
     * @return
     */
    public ModelMessageReceiver getModelMessageReceiver()
    {
    	return _modelMessageReceiver;
    }

    /**
     * Class used only by the {@link Shell} to schedule the project start time.
     */
    private  class ProjectStartTimerTask implements ITimerTask  {

        /**
         * The {@link Project}.
         */
        private Project _project;

        /**
         * Initializes the object.
         */
        public ProjectStartTimerTask( Project project )
        {
            _project = project;
        }

        public void start ( )
        {
            Timer.getInstance( ).scheduleTask( this, _project.getStartDate( ).toLocalDateTime( ) );
        }

        @Override
        public void runTimerTask ( )
        {
            // Notify all listeners.
            for ( IShellListener listener : _listeners )
            {
                listener.projectStarted( _project );
            }
        }


        @Override
        public String toString ( )
        {
            return "ProjectStartTimerTask on project :" + _project;
        }
    }

    /**
     * Class used only by the {@link Shell} to schedule the project end time.
     */
    public class ProjectEndTimerTask implements ITimerTask{

        /**
         * The {@link Project}.
         */
        private Project _project;

        /**
         * Initializes the object.
         */
        public ProjectEndTimerTask( Project project )
        {
            _project = project;
        }

        public void start ( )
        {
            Timer.getInstance( ).scheduleTask( this, _project.getEndDate( ).toLocalDateTime( ) );
        }

        @Override
        public void runTimerTask ( )
        {
            LOGGER.info( "Project " + _project + " is finished..." );
            _company.setCurrentProject( null );
            // Notify all listeners.
            for ( IShellListener listener : _listeners )
            {
                listener.projectFinished( _project );
            }
        }

        /**
         * @return the {@link #_project}.
         */
        public Project getProject ( )
        {
            return _project;
        }

        @Override
        public String toString ( )
        {
            return "ProjectEndTimerTask on project :" + _project;
        }
    }

}
