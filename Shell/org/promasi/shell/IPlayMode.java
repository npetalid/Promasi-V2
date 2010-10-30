package org.promasi.shell;

import java.util.List;
import java.util.Map;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.ICommunicator;
import org.promasi.core.IStatePersister;
import org.promasi.core.SdModel;
import org.promasi.model.Employee;
import org.promasi.model.MarketPlace;
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
    public Map<Integer,Employee> getAllEmployees ( );

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
     * @param communicator
     */
    public void registerCommunicator(ICommunicator communicator);
    
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
	public String getGameInfo(String gameId)throws IllegalArgumentException;
	
	/**
	 * 
	 * @param gameId
	 * @return
	 */
	public boolean play(String gameId , ProjectManager projectManager)throws IllegalArgumentException,NullArgumentException;
	
	/**
	 * 
	 * @param marketPlace
	 * @throws NullArgumentException
	 */
	public void setMarketPlace(MarketPlace marketPlace)throws NullArgumentException;
}
