package org.promasi.shell;

import java.util.List;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.IMessageReceiver;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Company;
import org.promasi.model.Employee;
import org.promasi.model.Message;
import org.promasi.model.Project;
import org.promasi.model.ProjectManager;

/**
 *
 * Interface that defines a play mode.
 *
 * @author eddiefullmetal
 *
 */
public interface IPlayMode
{

    /**
     * @return The name of the play mode.
     */
	public String getName ( );

    /**
     * @return The description of the play mode. The description can be in HTML
     *         format.
     *
     */
    public String getDescription ( );

    /**
     * Starts the play mode.
     *
     * @throws ConfigurationException
     *             In case the play mode is not configured properly.
     */
    void start ( ) throws ConfigurationException;

    /**
     * @return All the available employees in the system(Hired and Free).
     */
    public List<Employee> getAllEmployees ( );

    /**
     * @param project
     * @return The {@link SdModel} that is associated with the specified
     *         {@link Project}.
     */
    public SdModel getModelForProject ( Project project );

    /**
     * @param project
     * @return The {@link IStatePersister} for the specified project.
     */
    public IStatePersister getPersisterForProject ( Project project );
    
    /**
     * 
     * @param firstName
     * @param lastName
     * @param password
     * @return
     */
    public boolean login(String firstName,String lastName,String password);
    
    /**
     * 
     * @return
     */
    boolean needPasswordToLogin();
	
	/**
	 * 
	 * @return
	 */
	public List<String> getGamesList();
	
	/**
	 * 
	 * @param gameId
	 * @return
	 */
	public String getGameInfo(String gameId)throws IllegalArgumentException,NullArgumentException;
	
	/**
	 * 
	 * @param gameId
	 * @return
	 */
	public boolean play(String gameId , ProjectManager projectManager)throws IllegalArgumentException,NullArgumentException;
	
	/**
	 * 
	 * @param message
	 */
	public void sendMail ( Message message )throws NullArgumentException;
	
	/**
	 * 
	 * @param messageReceiver
	 * @throws NullArgumentException
	 */
	public void setMessageModelReceiver(IMessageReceiver messageReceiver)throws NullArgumentException;
	
	/**
	 * 
	 * @return
	 */
	public Company getCompany();
	
	/**
	 * 
	 * @param listener
	 */
	public void addListener ( IPlayModeListener listener )throws NullArgumentException;
	
	/**
	 * 
	 * @param employee
	 */
	public boolean hireEmployee(Employee employee);;
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmployeeHired(Employee employee)throws NullArgumentException;
}
