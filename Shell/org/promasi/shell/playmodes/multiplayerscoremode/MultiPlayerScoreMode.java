package org.promasi.shell.playmodes.multiplayerscoremode;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.DurationFieldType;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.core.SdSystem;
import org.promasi.model.Employee;
import org.promasi.model.IClockListener;
import org.promasi.model.Project;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.IShellListener;
import org.promasi.shell.Shell;

public class MultiPlayerScoreMode implements IPlayMode, IClockListener,IShellListener {

	private Shell _shell;
	
    /**
     * The running sd system.
     */
    private SdSystem _currentSdSystem;


    private ICommunicator _systemCommunicator;
	
    /**
     * Object used for locking.
     */
    private Object _lockObject;

    /**
     * Keeps an {@link IStatePersister} for each {@link Project}.
     */
    private Map<Project, IStatePersister> _projectPersisters;
	
	public MultiPlayerScoreMode(Shell shell)throws NullArgumentException
	{
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell");
		}
		
		_shell=shell;
		_shell.addListener(this);
        _projectPersisters = new Hashtable<Project, IStatePersister>( );
        _lockObject = new Object( );
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
	public void employeeHired(Employee employee) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ticked(List<DurationFieldType> changedTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws ConfigurationException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdModel getCurrentModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdModel getModelForProject(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStatePersister getPersisterForProject(Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerCommunicator(ICommunicator communicator) {
		// TODO Auto-generated method stub

	}

}
