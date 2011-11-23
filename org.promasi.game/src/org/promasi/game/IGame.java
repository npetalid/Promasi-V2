package org.promasi.game;

import java.util.Date;
import java.util.List;

import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * 
 * @author m1cRo
 *
 */
public interface IGame 
{
	/**
	 * 
	 * @return
	 */
	public String getGameDescription();
	
	/**
	 * 
	 * @param employeeId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 * @throws SerializationException
	 */
	public void hireEmployee(final String employeeId)throws GameException;
	
	/**
	 * 
	 * @param employeeId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 * @throws SerializationException
	 */
	public void dischargeEmployee(final String employeeId)throws GameException;
	
	/**
	 * 
	 * @param employeeId
	 * @param employeeTasks
	 * @return
	 */
	public boolean assignTasks(final String employeeId, List<SerializableEmployeeTask> employeeTasks);
	
	/**
	 * 
	 * @param gameEventHandler
	 * @throws NullArgumentException
	 */
	public boolean addListener(final IClientGameListener listener);
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeListener(final IClientGameListener listener);
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean addCompanyListener( ICompanyListener listener );
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeCompanyListener( ICompanyListener listener );
	
	/**
	 * 
	 * @param currentDateTime
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean executeGameStep(Date currentDateTime)throws GameException;
	
	/**
	 * 
	 * @return
	 */
	public boolean startGame();
	
	/**
	 * 
	 * @return
	 */
	public IGamesServer getGamesServer();
	
	/**
	 * 
	 * @return
	 */
	public boolean stopGame();
	
	/**
	 * 
	 * @param newSpeed
	 * @return
	 */
	public boolean setGameSpeed(int newSpeed);
}
