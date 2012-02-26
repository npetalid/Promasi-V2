/**
 * 
 */
package org.promasi.game.multiplayer;

/**
 * @author m1cRo
 *
 */
import java.util.List;

import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.multiplayer.IServerGameListener;
import org.promasi.utilities.exceptions.NullArgumentException;

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
	 * @return
	 */
	public boolean hireEmployee(final String playerId, final String employeeId);
	
	/**
	 * 
	 * @param playerId
	 * @param employeeId
	 * @return
	 */
	public boolean dischargeEmployee(final String playerId, final String employeeId);
	
	/**
	 * 
	 * @param playerId
	 * @param employeeId
	 * @param employeeTasks
	 * @return
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public boolean assignTasks(final String playerId, final String employeeId, List<EmployeeTaskMemento> employeeTasks);
	
	/**
	 * 
	 * @param client
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean addListener(final IServerGameListener listener);
	
	/**
	 * 
	 * @param client
	 * @param gameEventHandler
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean removeListener(final IServerGameListener gameEventHandler);
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean startGame(final String playerId);
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean joinGame(final String playerId);
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean leaveGame(final String playerId);
	
	/**
	 * 
	 * @param playerId
	 * @param message
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean sendMessage(final String playerId, String message);
	
	/**
	 * 
	 * @param playerId
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean stopGame(final String playerId);
	
	/**
	 * 
	 * @param playerId
	 * @param newSpeed
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean setGameSpeed(final String playerId, int newSpeed);
}