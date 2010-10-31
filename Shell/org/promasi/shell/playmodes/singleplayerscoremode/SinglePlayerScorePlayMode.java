package org.promasi.shell.playmodes.singleplayerscoremode;


import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.naming.ConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.joda.time.DurationFieldType;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.communication.IMessageReceiver;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.core.SdSystem;
import org.promasi.model.Clock;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.IClockListener;
import org.promasi.model.INotifierListener;
import org.promasi.model.ITimerTask;
import org.promasi.model.MarketPlace;
import org.promasi.model.Message;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;
import org.promasi.model.Timer;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.IPlayModeListener;
import org.promasi.shell.Shell;
import org.promasi.shell.Story.StoriesPool;
import org.promasi.shell.Story.Story;
import org.promasi.shell.model.actions.IModelAction;
import org.promasi.shell.model.communication.ModelMessageReceiver;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.ActionBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.EventBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.ExternalEquationBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.OutputVariableBinding;
import org.promasi.ui.promasiui.promasidesktop.DesktopMainFrame;
import org.promasi.ui.promasiui.promasidesktop.story.StorySelectorFrame;


/**
 *
 * This play mode works with XML file storage. The purpose of this mode is to
 * complete all levels with the higher score(prestige points). For more info
 * read the "SinglePlayerScoreMode_Specification".
 *
 * @author eddiefullmetal
 *
 */
public class SinglePlayerScorePlayMode implements IPlayMode, IClockListener, IPlayModeListener, INotifierListener
{
	/**
	 * 
	 */
	public Map<String,Story> _stories;
	
    /**
     * All the registered {@link IPlayModeListener}s.
     */
    private List<IPlayModeListener> _listeners;
	
    /**
     * The current {@link Story} of this play mode.
     */
    private Story _currentStory;

    /**
     * The running sd system.
     */
    private SdSystem _currentSdSystem;

    /**
     * Object used for locking.
     */
    private Object _lockObject;
    
    /**
     * 
     */
    private ModelMessageReceiver _modelMessageReceiver;
    
    /**
     * 
     */
    private ICommunicator _systemCommunicator;
    
    /**
     * The directory that this play mode stores its data.
     */
    public static final String RELATIVE_DATA_DIRECTORY = "Data" + File.separator + "SinglePlayerScoreMode";

    /**
     * Keeps an {@link IStatePersister} for each {@link Project}.
     */
    private Map<Project, IStatePersister> _projectPersisters;


    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( SinglePlayerScorePlayMode.class );

    /**
     * Initializes the object.
     */
    public SinglePlayerScorePlayMode(  )
    {
    	_lockObject = new Object( );
        _projectPersisters = new Hashtable<Project, IStatePersister>( );
        _stories=new TreeMap<String,Story>();
        List<Story> stories=StoriesPool.getAllStories( );
        for(Story story : stories)
        {
        	_stories.put(story.getName(), story);
        }
        
        _systemCommunicator=new Communicator();
        _listeners = new Vector<IPlayModeListener>( );
        _modelMessageReceiver=new ModelMessageReceiver();
    }

    /**
     * Adds the {@link IPlayModeListener} to the {@link #_listeners}.
     */
    public void addListener ( IPlayModeListener listener )
    {
        if ( !_listeners.contains( listener ) )
        {
            _listeners.add( listener );
        }
    }
    
    @Override
    public String getDescription ( )
    {
        return "The purpose of this play mode is to gather the highest score.<br>"
                + "You will play through various levels. On each level you will have to complete a project.<br>";
    }

    @Override
    public String getName ( )
    {
        return "Single Player, Score mode";
    }

    @Override
    public String toString ( )
    {
        return getName( );
    }

    /**
     * @param currentStory
     *            the {@link #_currentStory} to set.
     */
    public void setCurrentStory ( Story story )
    {
    	if(story==null)
    	{
    		throw new NullArgumentException("Wrong argumen story==null");
    	}
        _currentStory = story;
        _modelMessageReceiver.setContext(_currentStory.getCompany());
        _currentStory.getCompany().getNotifier().addListener(this);
    }

    /**
     * @return the {@link #_currentStory}.
     */
    public Story getCurrentStory ( )
    {
        return _currentStory;
    }

    @Override
    public SdModel getModelForProject ( Project project )
    {
        return _currentStory.getModel( project );
    }

    @Override
    public IStatePersister getPersisterForProject ( Project project )
    {
        return _projectPersisters.get( project );
    }

    @Override
    public void start ( ) throws ConfigurationException
    {
        Company company = _currentStory.getCompany( );
        if ( _currentStory == null || !_currentStory.isValid( ) )
        {
            LOGGER.error( "No story is selected or story is not properly configured." );
            throw new ConfigurationException( "No story is selected or story is not properly configured." );
        }
        
        _currentStory.getCompany().getNotifier().addListener(this);
        company.setBoss( _currentStory.getBoss( ) );
        company.setAccountant( _currentStory.getAccountant( ) );
        company.setAdministrator( _currentStory.getAdministrator( ) );
        Clock.getInstance( ).addListener( this );
        Clock.getInstance( ).setCurrentDateTime( _currentStory.getStartDate( ).toDateTime( company.getStartTimeAsLocalTime( ) ).toMutableDateTime( ) );
        Clock.getInstance( ).start( );
        company.assignProject(_currentStory.getNextProject( ));
      
    }

    @Override
    public Map<Integer, Employee> getAllEmployees ( )
    {
        return _currentStory.getMarketPlace( ).getAvailableEmployees();
    }

    @Override
    public void projectAssigned ( Project project )
    {
        Company company = _currentStory.getCompany( );
        Message message = new Message( );
        message.setSender( company.getBoss( ) );
        message.setRecipient( company.getProjectManager( ) );
        message.setTitle( "New project." );
        Project currentProject = company.getCurrentProject( );
        message.setBody( currentProject.getDescription( ) );
        sendMail(message);
        
        ProjectStartTimerTask startTask = new ProjectStartTimerTask( project );
        startTask.start( );
        ProjectEndTimerTask endTask = new ProjectEndTimerTask( project );
        endTask.start( );
        for ( IPlayModeListener listener : _listeners )
        {
            listener.projectAssigned( project );
        }
    }

    @Override
    public void ticked ( List<DurationFieldType> changedTypes )
    {
        synchronized ( _lockObject )
        {
	    	if ( _currentSdSystem != null )
	    	{
	    		_currentSdSystem.executeStep( );
	    	}
	            
	    	if ( changedTypes.contains( DurationFieldType.days( ) ) )
	    	{
	    		Company company = _currentStory.getCompany( );
	    		double total = company.getAccountant( ).calculateDailySalaries( );
	    		LOGGER.info( "Paying employees " + total );
	    		Project currentProject = _currentStory.getCurrentProject( );
	    		if(currentProject!=null){
	    			currentProject.setBudget( currentProject.getBudget( ) - total );
	    		}
	    		
	    		Message message = new Message( );
	    		message.setSender( company.getAccountant( ) );
	    		message.setRecipient( company.getProjectManager( ) );
	    		message.setTitle( "Payments" );
	    		message.setBody( "Total payments : " + total );
	    		sendMail(message);
	    	}
	            
	    	if ( changedTypes.contains( DurationFieldType.hours( ) ) )
	    	{
	    		int currentHourOfDay = Clock.getInstance( ).getCurrentDateTime( ).getHourOfDay( );
	    		Company company = _currentStory.getCompany( );
	    		int companyEndHour = company.getEndTimeAsLocalTime( ).getHourOfDay( );
	    		if ( currentHourOfDay >= companyEndHour )
	    		{
	                    Clock.getInstance( ).performDayChange( company.getStartTimeAsLocalTime( ) );
	    		}
	    	}
        }
    }

    @Override
    public synchronized void projectStarted ( Project project )
    {
        synchronized ( _lockObject )
        {
	    	// Just to be safe. If for some reason a random project starts
	    	// ignore it.
	    	if ( project.equals( _currentStory.getCurrentProject( ) ) )
	    	{
	    		// set up the mode message receiver.
	    		_modelMessageReceiver.clearAll( );
	    		List<OutputVariableBinding> variableBindings = _currentStory.getOutputVariableBindings( project );
	    		for ( OutputVariableBinding outputVariableBinding : variableBindings )
	    		{
	    			_modelMessageReceiver.addValueSentData( outputVariableBinding.getSdObjectKey( ),outputVariableBinding.getModelXPath( ) );
	    		}
	                
	    		List<ExternalEquationBinding> externalBindings = _currentStory.getExternalEquationBindings( project );
	    		for ( ExternalEquationBinding externalEquationBinding : externalBindings )
	    		{
	    			_modelMessageReceiver.addValueRequestedData( externalEquationBinding.getSdObjectKey( ),externalEquationBinding.getModelXPath( ) );
	    		}
	                
	    		List<EventBinding> eventBindings = _currentStory.getEventBindings( project );
	    		for ( EventBinding eventBinding : eventBindings )
	    		{
	    			IModelAction action;
	    			try
	    			{
	    				@SuppressWarnings("rawtypes")
	    				Class clazz = Class.forName( eventBinding.getActionBinding( ).getActionClassName( ) );
	    				action = (IModelAction) clazz.newInstance( );
	    				ActionBinding actionBinding = eventBinding.getActionBinding( );
	    				for ( String paramName : actionBinding.getParameters( ).keySet( ) )
	    				{
	    					BeanUtils.setProperty( action, paramName, actionBinding.getParameter( paramName ) );
	    				}
	                        
	    				if ( action.isValid( ) )
	    				{
	    					_modelMessageReceiver.addEventAction( eventBinding.getEventName( ), eventBinding.getSdObjectKey( ), action );
	    				}
	    				else
	    				{
	                            LOGGER.warn( "Skipping action" + action + ",invalid action." );
	    				}
	    			}
	    			catch ( Exception e )
	    			{
	                        LOGGER.warn( "Could not add eventBinding.", e );
	    			}
	    		}
	                
	    		// Set up the SdModel.
	    		LOGGER.info( "Starting project..." );
	    		SdModel model = _currentStory.getModel( project );
	    		_currentSdSystem = new SdSystem( );
	    		_currentSdSystem.initialize( model.getSdObjects( ) );
	    		// Register the system communicator
	    		_systemCommunicator.setMainReceiver(_modelMessageReceiver);
	    		_currentSdSystem.registerCommunicator(_systemCommunicator);
	    		// Register the persister.
	    		IStatePersister persister = new MemoryStatePersister( );
	    		_projectPersisters.put( project, persister );
	    		_currentSdSystem.registerStatePersister( persister );
	    		// Execute the first step.
	    		_currentSdSystem.executeStep( );
	    	}
	    	else
	    	{
	    		LOGGER.warn( "projectStarted called with unexpected project..." );
	    	}
        }
    }

    @Override
    public void projectFinished ( Project project )
    {
        synchronized ( _lockObject )
        {
	    	_currentSdSystem = null;
	    	Company company = _currentStory.getCompany( );
	    	Project nextProject = _currentStory.getNextProject( );
	    	if ( nextProject != null )
	    	{
	    		// Add the prestige points gained from the finished project to
	    		// the company.
	    		company.setPrestigePoints( company.getPrestigePoints( ) + project.getPrestigePoints( ) );
	    		// Assign the next project
	    		company.assignProject( nextProject );
	    		SdModel sdModel=_currentStory.getModel(nextProject);
	    		_currentSdSystem=new SdSystem();
	    		_currentSdSystem.initialize(sdModel.getSdObjects());
	    		IStatePersister persister = new MemoryStatePersister( );
	    		_projectPersisters.put( project, persister );
	    		_currentSdSystem.registerStatePersister( persister );
	    		_currentSdSystem.executeStep( );
	    	}
        }
    }

    @Override
    public void employeeHired(Employee employee)
    {
        for ( IPlayModeListener listener : _listeners )
        {
            listener.employeeHired( employee );
        }
    }

	@Override
	public boolean login(String firstName,String lastName,String password){
		try {
			ProjectManager projectManager=new ProjectManager(firstName,lastName);
			StorySelectorFrame storySelector = new StorySelectorFrame( projectManager,this );
			storySelector.setVisible( true );
		} catch (NullArgumentException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean needPasswordToLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized List<String> getGamesList() {
		Vector<String> stories=new Vector<String>();
		for(Map.Entry<String, Story> entry : _stories.entrySet())
		{
			stories.add(entry.getKey());
		}
		
		return stories;
	}

	@Override
	public synchronized String getGameInfo(String gameId) throws IllegalArgumentException, NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		if(_stories.containsKey(gameId))
		{
			return _stories.get(gameId).getInfoString();
		}
		else
		{
			throw new IllegalArgumentException("Wrong argument gameId");
		}
	}

	@Override
	public synchronized boolean play(String gameId , ProjectManager projectManager)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==0");
		}
		
		if(projectManager==null)
		{
			throw new NullArgumentException("Wrong argument projectManager==null");
		}
		
        Story story = _stories.get(gameId);
        if ( story != null )
        {
        	 try {
	            LOGGER.info( "Selected story:" + story );
	            Company company = story.getCompany( );
	            company.setProjectManager( projectManager );
	            setCurrentStory( story );
	            Shell shell=new Shell(this);
	            DesktopMainFrame mainFrame = new DesktopMainFrame(shell);
	            mainFrame.showMainFrame( );
				shell.start();
			} catch (ConfigurationException e) {
				return false;
			}
        }
        else
        {
            return false;
        }
        
		return true;
	}

	@Override
	public void sendMail(Message message) 
	{
		_currentStory.getCompany().getMessagingServer( ).sendMessage( message );
	}

	@Override
	public void setMessageModelReceiver(IMessageReceiver messageReceiver)throws NullArgumentException 
	{
		_systemCommunicator.setMainReceiver(messageReceiver);		
	}

	@Override
	public Company getCompany() {
		return _currentStory.getCompany();
	}

	@Override
	public MarketPlace getMarketPlace() {
		return _currentStory.getMarketPlace();
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
            for ( IPlayModeListener listener : _listeners )
            {
                listener.projectStarted( _project );
            }
            
            projectStarted(_project);
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
            _currentStory.getCompany().setCurrentProject( null );
            // Notify all listeners.
            for ( IPlayModeListener listener : _listeners )
            {
                listener.projectFinished( _project );
            }
            
            projectFinished(_project);
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
