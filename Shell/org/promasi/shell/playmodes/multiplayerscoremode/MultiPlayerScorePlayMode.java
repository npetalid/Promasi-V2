/**
 * 
 */
package org.promasi.shell.playmodes.multiplayerscoremode;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
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
import org.promasi.shell.ui.Story.Story;
import org.promasi.ui.promasiui.multiplayer.MakeGameForm;
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
	private List<Story> _games;
	
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
		_games=new Vector<Story>();
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
    public synchronized void updateGameList(final List<Story> stories )
    {
    	_games.clear();
    	_games.addAll(stories);
    }
    
    
	@Override
	public synchronized List<String> getGamesList(){
		Vector<String> gameList=new Vector<String>();
		for(Story game : _games)
		{
			gameList.add(game.getName());
		}
		
		gameList.add(new String("New Game"));
		return gameList;
	}

	
	@Override
	public String getGameDescription(int gameId) throws IllegalArgumentException
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		if(gameId<_games.size())
		{
			Story game=_games.get(gameId);
			if(game!=null)
			{
				return game.getName();
			}
			else
			{
				throw new  IllegalArgumentException("Wrong argument gameId");
			}
		}
		else
		{
			return new String("Create new Game");
		}
	}

	
	@Override
	public String getGameInfo(int gameId)throws IllegalArgumentException 
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		if(gameId<_games.size())
		{
			Story game=_games.get(gameId);
			if(game!=null)
			{
				return game.getInfoString();
			}
			else
			{
				throw new  IllegalArgumentException("Wrong argument gameId");
			}
		}
		else
		{
			return new String("Create new Game");
		}
	}
	

	@Override
	public boolean play(int gameId, ProjectManager projectManager)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		if(projectManager==null)
		{
			throw new NullArgumentException("Wrong argument projectManager==null");
		}
		
		if(_games.size()==gameId)
		{
	    	 MakeGameForm makeGameForm=new MakeGameForm(_promasiClient);
	    	 makeGameForm.setVisible(true);
		}
		else if(gameId<_games.size())
		{
			 Story game=_games.get(gameId);
		     if ( game != null )
		     {
				 JoinGameRequest request=new JoinGameRequest(game.getName());
				 _promasiClient.sendMessage(request.toProtocolString());
		     }
		     else
		     {
		    	 return false;
		     }
		}
		
	     return true;
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
