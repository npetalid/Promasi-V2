package org.promasi.shell.playmodes.singleplayerscoremode;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.ConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.log4j.Logger;
import org.joda.time.DurationFieldType;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.core.SdSystem;
import org.promasi.model.Clock;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.IClockListener;
import org.promasi.model.Message;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;
import org.promasi.shell.model.actions.IModelAction;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.ActionBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.EventBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.ExternalEquationBinding;
import org.promasi.shell.playmodes.singleplayerscoremode.corebindings.OutputVariableBinding;
import org.promasi.shell.ui.playmode.StoriesPool;
import org.promasi.shell.ui.playmode.Story;
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
public class SinglePlayerScorePlayMode implements IPlayMode, IClockListener, IShellListener
{
	/**
	 * 
	 */
	List<Story> _stories;
	
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
     * The current system shell.
     */
    private Shell _shell;

    /**
     * Default logger for this class.
     */
    private static final Logger LOGGER = Logger.getLogger( SinglePlayerScorePlayMode.class );

    /**
     * Initializes the object.
     */
    public SinglePlayerScorePlayMode( Shell shell )throws NullArgumentException
    {
    	if(shell==null)
    	{
    		throw new NullArgumentException("Wrong argument shell==null");
    	}

    	_lockObject = new Object( );
    	_shell=shell;
    	_shell.addListener( this );
        _projectPersisters = new Hashtable<Project, IStatePersister>( );
        _stories=StoriesPool.getAllStories( );
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
    public void setCurrentStory ( Story currentStory )
    {
        _currentStory = currentStory;
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
        Company company = _shell.getCompany( );
        if ( _currentStory == null || !_currentStory.isValid( ) )
        {
            LOGGER.error( "No story is selected or story is not properly configured." );
            throw new ConfigurationException( "No story is selected or story is not properly configured." );
        }
        
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
        _shell.sendMail( message );
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
	    		_shell.sendMail( message );
	    	}
	            
	    	if ( changedTypes.contains( DurationFieldType.hours( ) ) )
	    	{
	    		int currentHourOfDay = Clock.getInstance( ).getCurrentDateTime( ).getHourOfDay( );
	    		Company company = _shell.getCompany( );
	    		int companyEndHour = company.getEndTimeAsLocalTime( ).getHourOfDay( );
	    		if ( currentHourOfDay >= companyEndHour )
	    		{
	                    Clock.getInstance( ).performDayChange( company.getStartTimeAsLocalTime( ) );
	    		}
	    	}
        }
    }

    @Override
    public void projectStarted ( Project project )
    {
        synchronized ( _lockObject )
        {
	    	// Just to be safe. If for some reason a random project starts
	    	// ignore it.
	    	if ( project.equals( _currentStory.getCurrentProject( ) ) )
	    	{
	    		// set up the mode message receiver.
	    		_shell.getModelMessageReceiver().clearAll( );
	    		List<OutputVariableBinding> variableBindings = _currentStory.getOutputVariableBindings( project );
	    		for ( OutputVariableBinding outputVariableBinding : variableBindings )
	    		{
	    			_shell.getModelMessageReceiver().addValueSentData( outputVariableBinding.getSdObjectKey( ),outputVariableBinding.getModelXPath( ) );
	    		}
	                
	    		List<ExternalEquationBinding> externalBindings = _currentStory.getExternalEquationBindings( project );
	    		for ( ExternalEquationBinding externalEquationBinding : externalBindings )
	    		{
	    			_shell.getModelMessageReceiver().addValueRequestedData( externalEquationBinding.getSdObjectKey( ),externalEquationBinding.getModelXPath( ) );
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
	    					_shell.getModelMessageReceiver().addEventAction( eventBinding.getEventName( ), eventBinding.getSdObjectKey( ), action );
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
	    	Company company = _shell.getCompany( );
	    	Project nextProject = _currentStory.getNextProject( );
	    	if ( nextProject != null )
	    	{
	    		// Add the prestige points gained from the finished project to
	    		// the company.
	    		company.setPrestigePoints( company.getPrestigePoints( ) + project.getPrestigePoints( ) );
	    		// Assign the next project
	    		company.assignProject( nextProject );
	    	}
        }
    }

    @Override
    public void employeeHired(Employee employee)
    {
  
    }

	@Override
	public void registerCommunicator(ICommunicator communicator)
	{
		_systemCommunicator=communicator;
		if(_currentSdSystem!=null)
		{
			_currentSdSystem.registerCommunicator(communicator);
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
		for(Story story : _stories)
		{
			stories.add(story.getName());
		}
		
		return stories;
	}

	@Override
	public synchronized String getGameDescription(int gameId) throws IllegalArgumentException
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		if(gameId<_stories.size())
		{
			Story story=_stories.get(gameId);
			if(story!=null)
			{
				return story.getName();
			}
			else
			{
				throw new IllegalArgumentException("Wrong argument gameId");
			}
		}
		
		throw new IllegalArgumentException("Wrong argument gameId");
	}

	@Override
	public synchronized URL getGameInfo(int gameId) throws IllegalArgumentException
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		if(gameId<_stories.size())
		{
			Story story=_stories.get(gameId);
			if(story!=null)
			{
				try {
					return story.getInfoFile().toURI().toURL();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new IllegalArgumentException("Wrong argument gameId");
				}
			}
			else
			{
				throw new IllegalArgumentException("Wrong argument gameId");
			}
		}
		else
		{
			throw new IllegalArgumentException("Wrong argument gameId");
		}
	}

	@Override
	public synchronized boolean play(int gameId , ProjectManager projectManager)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		if(projectManager==null)
		{
			throw new NullArgumentException("Wrong argument projectManager==null");
		}
		
        Story story = _stories.get(gameId);
        if ( story != null )
        {
            LOGGER.info( "Selected story:" + story );
            Company company = story.getCompany( );
            company.setProjectManager( projectManager );
            _shell.setCompany( company );
            setCurrentStory( story );
            
            try
            {
            	ICommunicator communicator=new Communicator();
            	communicator.setMainReceiver( _shell.getModelMessageReceiver());
            	
                _shell.setCurrentPlayMode(this);
                DesktopMainFrame mainFrame = new DesktopMainFrame(_shell);
                mainFrame.showMainFrame( );
                mainFrame.registerCommunicator(communicator);
                _shell.start();
            }
            catch ( ConfigurationException e )
            {
                e.printStackTrace( );
                return false;
            }
        }
        else
        {
            return false;
        }
        
		return true;
	}
}
