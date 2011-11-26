package org.promasi.game;

import java.util.Date;
import java.util.List;

import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.utilities.exceptions.NullArgumentException;

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
	 */
	public boolean hireEmployee(final String employeeId);
	
	/**
	 * 
	 * @param employeeId
	 */
	public boolean dischargeEmployee(final String employeeId);
	
	/**
	 * 
	 * @param employeeId
	 * @param employeeTasks
	 * @return
	 */
	public boolean assignTasks(final String employeeId, List<EmployeeTaskMemento> employeeTasks);
	
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
	 * @param listener
	 * @return
	 */
	public boolean addDepartmentListener( IDepartmentListener listener );
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeDepartmentListener( IDepartmentListener listener );
	
	/**
	 * 
	 */
	public void removeListeners();
	
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
