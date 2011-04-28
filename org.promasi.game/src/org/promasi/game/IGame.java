package org.promasi.game;

import java.util.List;

import org.joda.time.DateTime;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.singleplayer.ISinglePlayerGameListener;
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
	public void hireEmployee(final String employeeId)throws NullArgumentException, IllegalArgumentException, SerializationException;
	
	/**
	 * 
	 * @param employeeId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 * @throws SerializationException
	 */
	public void dischargeEmployee(final String employeeId)throws NullArgumentException, IllegalArgumentException, SerializationException;
	
	/**
	 * 
	 * @param employeeId
	 * @param employeeTask
	 * @return
	 * @throws NullArgumentException 
	 */
	public boolean assignTasks(final String employeeId, List<SerializableEmployeeTask> employeeTasks) throws NullArgumentException;
	
	/**
	 * 
	 * @param gameEventHandler
	 * @throws NullArgumentException
	 */
	public boolean addListener(final ISinglePlayerGameListener listener)throws NullArgumentException;
	
	/*
	 * 
	 */
	public boolean removeListener(final ISinglePlayerGameListener listener)throws NullArgumentException;
	
	/**
	 * 
	 * @param currentDateTime
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean executeGameStep(DateTime currentDateTime)throws NullArgumentException;
	
	/**
	 * 
	 * @return
	 */
	public boolean startGame();
	
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
