/**
 * 
 */
package org.promasi.shell.playmodes.multiplayermode;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.LinkedList;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Employee;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;
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
	private TcpClient _tcpClient;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 * @param hostname
	 * @param portNumber
	 * @throws IllegalArgumentException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public MultiPlayerScorePlayMode(Shell shell,String hostname,int portNumber) throws NullArgumentException, IllegalArgumentException, UnknownHostException, IOException{
		if(hostname==null || shell==null){
			throw new NullArgumentException("MultiplayerScorePlayMode wrong arguments");
		}
		
		if( portNumber<0 ){
			throw new IllegalArgumentException( "Illegal argument portNumber<0" );
		}
		
		_employees=new LinkedList<Employee>();
		_tcpClient=new TcpClient(hostname,portNumber);
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
		// TODO Auto-generated method stub
		return new ProjectManager(firstName,lastName);
	}

	@Override
	public boolean needPasswordToLogin() {
		// TODO Auto-generated method stub
		return true;
	}
	
    @Override
    public String toString ( )
    {
        return getName( );
    }

	@Override
	public List<Story> getStories() {
		// TODO Auto-generated method stub
		return null;
	}

}
