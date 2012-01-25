/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.util.List;
import java.util.Map;

import org.promasi.game.IGame;
import org.promasi.game.IGamesServer;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.multiplayer.IMultiPlayerGame;
import org.promasi.game.singleplayer.IClientGameListener;

/**
 * @author alekstheod
 *
 */
public class MultiPlayerGame implements IGame {

	/**
	 * 
	 */
	private IMultiPlayerGame _game;
	
	/**
	 * 
	 * @param game
	 */
	public MultiPlayerGame( IMultiPlayerGame game ){
		
	}
	
	@Override
	public String getGameDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hireEmployee(String employeeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dischargeEmployee(String employeeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean assignTasks(Map<String , List<EmployeeTaskMemento> > employeeTasks) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addListener(IClientGameListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeListener(IClientGameListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean stopGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setGameSpeed(int newSpeed) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IGamesServer getGamesServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCompanyListener(ICompanyListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCompanyListener(ICompanyListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addDepartmentListener(IDepartmentListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDepartmentListener(IDepartmentListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addMarketPlaceListener(IMarketPlaceListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMarketPlaceListener(IMarketPlaceListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

}
