/**
 * 
 */
package org.promasi.shell.playmodes.multiplayermode;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.client.clientstate.LoginClientState;
import org.promasi.multiplayer.client.TcpEventHandler;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;
import org.promasi.shell.ui.playmode.Story;
import org.promasi.ui.promasiui.promasidesktop.DesktopMainFrame;
import org.promasi.network.protocol.client.request.LoginRequest;
import org.promasi.network.protocol.dtos.GameDto;
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
	private List<Employee> _employees;
	
	/**
	 * 
	 */
	private ProMaSiClient _promasiClient;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private List<GameDto> _games;
	
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
		
		_employees=new LinkedList<Employee>();
		TcpClient tcpClient=new TcpClient(_hostname,_port);
		
		_promasiClient=new ProMaSiClient( tcpClient,new LoginClientState(this));
		tcpClient.registerTcpEventHandler(new TcpEventHandler(_promasiClient));
		_games=new Vector<GameDto>();
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
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
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
		for( Employee empl : _employees){
			if(empl.equals(employee)){
				return;
			}
		}
		
		_employees.add(employee);
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

    public synchronized void updateGameList(final List<GameDto> stories )
    {
    	_games.clear();
    	_games.addAll(stories);
    }
    
	@Override
	public synchronized List<String> getGamesList(){
		Vector<String> gameList=new Vector<String>();
		for(GameDto game : _games)
		{
			gameList.add(game.getName());
		}
		
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
			GameDto game=_games.get(gameId);
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
			throw new  IllegalArgumentException("Wrong argument gameId");
		}
	}

	@Override
	public URL getGameInfo(int gameId)throws IllegalArgumentException 
	{
		if(gameId<0)
		{
			throw new IllegalArgumentException("Wrong argument gameId<0");
		}
		
		return null;
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
		
		 GameDto game=_games.get(gameId);
	     if ( game != null )
	     {
	        Company company = game.getCompany();
	        company.setProjectManager( projectManager );
	        _shell.setCompany( company );
	            
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
