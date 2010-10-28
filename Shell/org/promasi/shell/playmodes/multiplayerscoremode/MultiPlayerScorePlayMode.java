/**
 * 
 */
package org.promasi.shell.playmodes.multiplayerscoremode;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Employee;
import org.promasi.model.MarketPlace;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.client.clientstate.LoginClientState;
import org.promasi.multiplayer.client.TcpEventHandler;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;
import org.promasi.network.protocol.client.request.ChooseGameMasterStateRequest;
import org.promasi.network.protocol.client.request.JoinGameRequest;
import org.promasi.network.protocol.client.request.LoginRequest;
import org.promasi.network.tcp.TcpClient;

/**
 * @author m1cRo
 *
 */
public class MultiPlayerScorePlayMode implements IPlayMode,IShellListener {

	/**
	 * Current play mode name
	 */
	private static final String _playmodeName="Multiplayer, score mode";
	
	/**
	 * 
	 */
	private static final String _playmodeDescription="Myltiplayer mode description";
	
	/**
	 * 
	 */
	private MarketPlace _marketPlace;
	
	/**
	 * 
	 */
	private ProMaSiClient _promasiClient;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**"
	 * 
	 */
	private ProjectManager _projectManager;
	
	/**
	 * 
	 */
	private Map<String,String> _stories;
	
	/**
	 * 
	 */
	private static final String _hostname="localhost";
	
	/**
	 * 
	 */
	private static final int _port=2222;
	
	/**
	 * 
	 * @param hostname
	 * @param portNumber
	 * @throws IllegalArgumentException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public MultiPlayerScorePlayMode(Shell shell) throws NullArgumentException, IllegalArgumentException, UnknownHostException, IOException{
		if(shell==null){
			throw new NullArgumentException("MultiplayerScorePlayMode wrong arguments");
		}
		
		_marketPlace=new MarketPlace();
		TcpClient tcpClient=new TcpClient(_hostname,_port);
		
		_promasiClient=new ProMaSiClient( tcpClient,new LoginClientState(this));
		tcpClient.registerTcpEventHandler(new TcpEventHandler(_promasiClient));
		_stories=new TreeMap<String,String>();
		_shell=shell;
		_projectManager=new ProjectManager();
	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#getName()
	 */
	@Override
	public String getName() {
		return _playmodeName;
	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#getDescription()
	 */
	@Override
	public String getDescription() {
		return _playmodeDescription;
	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#start()
	 */
	@Override
	public void start() throws ConfigurationException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#getAllEmployees()
	 */
	@Override
	public Map<Integer,Employee> getAllEmployees() {
		return _marketPlace.getAvailableEmployees();
	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#getModelForProject(org.promasi.model.Project)
	 */
	@Override
	public SdModel getModelForProject(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#getPersisterForProject(org.promasi.model.Project)
	 */
	@Override
	public IStatePersister getPersisterForProject(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.promasi.shell.IPlayMode#registerCommunicator(org.promasi.communication.ICommunicator)
	 */
	@Override
	public void registerCommunicator(ICommunicator communicator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void employeeHired(Employee employee){
		
	}

	@Override
	public void projectStarted(Project project) {
		// TODO Auto-generated method stub
	}

	@Override
	public void projectFinished(Project project) {
		// TODO Auto-generated method stub
	}

	@Override
	public void projectAssigned(Project project) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean login(String firstName, String lastName,String password) {
		_promasiClient.sendMessage(new LoginRequest(firstName,lastName).toProtocolString());
		return true;
	}

	@Override
	public boolean needPasswordToLogin() {
		return false;
	}
	
    @Override
    public String toString ( )
    {
        return getName( );
    }

    /**
     * 
     * @param stories
     */
    public synchronized void updateGameList(final Map<String,String> stories )throws NullArgumentException
    {
    	if(stories==null)
    	{
    		throw new NullArgumentException("Wrong argument stories==null");
    	}
    	
    	_stories=stories;
    }
    
    
	@Override
	public synchronized List<String> getGamesList(){
		Vector<String> gameList=new Vector<String>();
		for(Map.Entry<String, String> entry : _stories.entrySet())
		{
			gameList.add(entry.getKey());
		}
		
		return gameList;
	}

	
	@Override
	public String getGameInfo(String gameId)throws NullArgumentException,IllegalArgumentException 
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		if(_stories.containsKey(gameId))
		{
			return _stories.get(gameId);
		}
		else
		{
			throw new IllegalArgumentException("Wrong argument gameId");
		}
	}
	

	@Override
	public boolean play(String gameId, ProjectManager projectManager)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		if(projectManager==null)
		{
			throw new NullArgumentException("Wrong argument projectManager==null");
		}
		
		if(_stories.containsKey(gameId))
		{
			JoinGameRequest request=new JoinGameRequest(gameId);
			_promasiClient.sendMessage(request.toProtocolString());
		}
		else
		{
			return false;
		}
		
	     return true;
	}
	
	public synchronized void createGame(ProjectManager projectManager)
	{
		ChooseGameMasterStateRequest request=new ChooseGameMasterStateRequest();
		_promasiClient.sendMessage(request.toProtocolString());
	}
	
	
	/**
	 * 
	 * @param projectManager
	 * @return
	 */
	public synchronized boolean setProjectManager(ProjectManager projectManager) 
	{
		if(projectManager==null)
		{
			return false;
		}
		
		_projectManager=projectManager;
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized ProjectManager getProjectManager()
	{
		return _projectManager;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public synchronized MarketPlace getMarketPlace()
	{
		return _marketPlace;
	}
	
	
	/**
	 * 
	 * @param marketPlace
	 * @throws NullArgumentException
	 */
	public synchronized void setMarketPlace(MarketPlace marketPlace)throws NullArgumentException
	{
		if(marketPlace==null)
		{
			throw new NullArgumentException("Wrong argument marketPlace==null");
		}
		
		_marketPlace=marketPlace;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Shell getShell()
	{
		return _shell;
	}

}
