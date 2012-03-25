package org.promasi.game;

import java.util.List;
import java.util.Map;

import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.singleplayer.IClientGameListener;

/**
 * 
 * @author m1cRo
 * Game interface represent the main
 * functionality of an ProMaSi game.
 */
public interface IGame 
{
	/**
	 * Will return the game name.
	 * @return string which represent the name 
	 * of the game.
	 */
	public String getName();
	/**
	 * User can call this method in order to
	 * get the game description.
	 * @return Instance of {@link String} which contains the game
	 * description.
	 * @return the game description.
	 */
	public String getGameDescription();
	
	/**
	 * User can call this method in order to 
	 * hire a new employee.
	 * @param employeeId The employee id to hire.
	 */
	public boolean hireEmployee(final String employeeId);
	
	/**
	 * User can call this method in order to 
	 * discharge an employee.
	 * @param employeeId The employee id to discharge.
	 * @return true if succeed, false otherwise.
	 */
	public boolean dischargeEmployee(final String employeeId);
	
	/**
	 * Call this method in order to assign the tasks to
	 * employees.
	 * @param employeeTasks Instance of {@link Map} interface implementation
	 * which contains the pairs of employeeId mapped to the list of the {@link EmployeeTaskMemento}
	 * which represents the tasks.
	 * @return true if succeed, false otherwise.
	 */
	public boolean assignTasks(final Map<String, List<EmployeeTaskMemento> > employeeTasks);
	
	/**
	 * Call this method in order to register an new Game listener.
	 * @param listener Instance of {@link IClientGameListener} interface implementation.
	 * @return true if succeed, false otherwise.
	 */
	public boolean addListener(final IClientGameListener listener);
	
	/**
	 * Will remove the given game listener.
	 * @param listener Instance of {@link IClientGameListener} interface implementation.
	 * @return true if succeed, false otherwise.
	 */
	public boolean removeListener(final IClientGameListener listener);
	
	/**
	 * 
	 * @param listener
	 * @return true if succeed, false otherwise.
	 */
	public boolean addCompanyListener( ICompanyListener listener );
	
	/**
	 * 
	 * @param listener
	 * @return true if succeed, false otherwise.
	 */
	public boolean removeCompanyListener( ICompanyListener listener );
	
	/**
	 * 
	 * @param listener
	 * @return true if succeed, false otherwise.
	 */
	public boolean addDepartmentListener( IDepartmentListener listener );
	
	/**
	 * 
	 * @param listener
	 * @return true if succeed, false otherwise.
	 */
	public boolean removeDepartmentListener( IDepartmentListener listener );
	
	/**
	 * Will add the listener to the game's MarketPlace.
	 * @param listener
	 * @return true if succeed, false otherwise.
	 */
	public boolean addMarketPlaceListener( IMarketPlaceListener listener );
	
	/**
	 * Will remove the {@link MarketPlace} listener.
	 * @param listener instance of {@link IMarketPlaceListener} interface 
	 * implementation to remove.
	 * @return true if succeed, false otherwise.
	 */
	public boolean removeMarketPlaceListener (IMarketPlaceListener listener );
	
	/**
	 * Will remove all listener which was assigned to the
	 * current game.
	 */
	public void removeListeners();
	
	/**
	 * Will start the game
	 * @return true if succeed, false otherwise.
	 */
	public boolean startGame();
	
	/**
	 * Will return the instance of {@link AGamesServer} in
	 * which this game is running.
	 * @return instance of {@link AGamesServer}
	 */
	public AGamesServer getGamesServer();
	
	/**
	 * Will stop a current game.
	 * @return true if succeed, false otherwise.
	 */
	public boolean stopGame();
	
	/**
	 * Will change the step execution period.
	 * @param newSpeed A new period in milliseconds.
	 * @return true if succeed, false otherwise.
	 */
	public boolean setGameSpeed(int newSpeed);
}
