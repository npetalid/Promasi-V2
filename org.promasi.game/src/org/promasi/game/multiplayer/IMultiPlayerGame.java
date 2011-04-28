/**
 * 
 */
package org.promasi.game.multiplayer;

/**
 * @author m1cRo
 *
 */
import java.util.List;

import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.multiplayer.IMultiPlayerGameListener;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public interface IMultiPlayerGame 
{
	/**
	 * 
	 * @return
	 */
	public String getGameOwnerId();
	
	/**
	 * 
	 * @return
	 */
	public String getGameName();
	
	/**
	 * 
	 * @return
	 */
	public String getGameDescription();
	
	/**
	 * 
	 * @return
	 */
	public List<String> getGamePlayers();
	
	/**
	 * 
	 * @param playerId
	 * @param employeeId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 * @throws SerializationException
	 */
	public void hireEmployee(final String playerId, final String employeeId)throws NullArgumentException, IllegalArgumentException, SerializationException;
	
	/**
	 * 
	 * @param playerId
	 * @param employeeId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 * @throws SerializationException
	 */
	public void dischargeEmployee(final String playerId, final String employeeId)throws NullArgumentException, IllegalArgumentException, SerializationException;
	
	/**
	 * 
	 * @param playerId
	 * @param employeeId
	 * @param employeeTasks
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public boolean assignTasks(final String playerId, final String employeeId, List<SerializableEmployeeTask> employeeTasks)throws NullArgumentException, IllegalArgumentException;
	
	/**
	 * 
	 * @param client
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean addListener(final IMultiPlayerGameListener listener)throws NullArgumentException;
	
	/**
	 * 
	 * @param client
	 * @param gameEventHandler
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean removeListener(final IMultiPlayerGameListener gameEventHandler)throws NullArgumentException;
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean startGame(final String playerId)throws NullArgumentException;
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean joinGame(final String playerId)throws NullArgumentException;
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean leaveGame(final String playerId)throws NullArgumentException;
	
	/**
	 * 
	 * @param playerId
	 * @param message
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean sendMessage(final String playerId, String message)throws NullArgumentException;
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean stopGame(final String playerId)throws NullArgumentException;
	
	/**
	 * 
	 * @param playerId
	 * @param newSpeed
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean setGameSpeed(final String playerId, int newSpeed)throws NullArgumentException;
}