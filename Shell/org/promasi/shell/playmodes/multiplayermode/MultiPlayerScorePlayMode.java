/**
 * 
 */
package org.promasi.shell.playmodes.multiplayermode;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.LinkedList;
import java.util.Vector;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Employee;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.client.ChooseGameClientState;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;
import org.promasi.shell.playmodes.singleplayerscoremode.Story;
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
	private ProMaSiClient _proMaSiClient;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private List<Story> _stories;
	
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
		_proMaSiClient=new ProMaSiClient( new TcpClient(_hostname,_port),new ChooseGameClientState(this) );
		_stories=new Vector<Story>();
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

	public void setCurrentStory(Story story) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProjectManager login(String firstName, String lastName,String password) {
		return new ProjectManager(firstName,lastName);
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

    @Override
    public synchronized void updateStories(final List<Story> stories )
    {
    	_stories.clear();
    	_stories.addAll(stories);
    }
    
	@Override
	public synchronized List<Story> getStories(){
		List<Story> stories=new Vector<Story>(_stories);
		return stories;
	}

}
