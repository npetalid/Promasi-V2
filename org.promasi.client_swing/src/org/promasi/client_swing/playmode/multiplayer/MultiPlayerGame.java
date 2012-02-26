/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.AGamesServer;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.singleplayer.IClientGameListener;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;

/**
 * @author alekstheod
 * Represent the MultiPlayer game in ProMaSi system.
 * This class will be responsible for all needed communication
 * in playing game state between client and server.
 */
public class MultiPlayerGame implements IGame, IClientListener {
	
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	private String _description;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private AGamesServer _gamesServer;
	
	/**
	 * List of game listeners, needed to handle
	 * game events.
	 */
	private List< IClientGameListener > _listeners;
	
	/**
	 * 
	 */
	private List< IDepartmentListener > _departmentListeners;
	
	/**
	 * 
	 */
	private List< ICompanyListener > _companyListeners;
	
	/**
	 * 
	 */
	private List< IMarketPlaceListener > _marketPlaceListeners;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 * @param gamesServer
	 * @param client
	 * @param gameId
	 * @param description
	 * @throws GuiException
	 */
	public MultiPlayerGame(AGamesServer gamesServer, ProMaSiClient client, String gameId, String description )throws GuiException{
		if( gameId == null ){
			throw new GuiException("Wrong argument gameId == null");
		}
		
		if( description == null ){
			throw new GuiException("Wrong argument description == null");
		}
		
		if( client == null ){
			throw new GuiException("Wrong argument client == null");
		}
		
		if( gamesServer == null ){
			throw new GuiException("Wrong argument gamesServer == null");
		}
		
		_gameId = gameId;
		_description = description;
		_client = client;
		_gamesServer = gamesServer;
		_lockObject = new ReentrantLock();
		
		_listeners = new LinkedList<>();
		_departmentListeners = new LinkedList<>();
		_companyListeners = new LinkedList<>();
		_marketPlaceListeners = new LinkedList<>();
		_client.addListener(this);
	}
	
	@Override
	public String getGameDescription() {
		return _description;
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
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && !_listeners.contains(listener) ){
				result = _listeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean removeListener(IClientGameListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && _listeners.contains(listener) ){
				result = _listeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public AGamesServer getGamesServer() {
		return _gamesServer;
	}

	@Override
	public boolean addCompanyListener(ICompanyListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && !_companyListeners.contains(listener) ){
				result = _companyListeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean removeCompanyListener(ICompanyListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && _companyListeners.contains(listener) ){
				result = _companyListeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public void removeListeners() {
		try{
			_lockObject.lock();
			_listeners.clear();
			_companyListeners.clear();
			_departmentListeners.clear();
			_marketPlaceListeners.clear();
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public boolean addDepartmentListener(IDepartmentListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && !_departmentListeners.contains(listener) ){
				result = _departmentListeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean removeDepartmentListener(IDepartmentListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && _departmentListeners.contains(listener) ){
				result = _departmentListeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean addMarketPlaceListener(IMarketPlaceListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && !_marketPlaceListeners.contains(listener) ){
				result = _marketPlaceListeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean removeMarketPlaceListener(IMarketPlaceListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if( listener != null && _marketPlaceListeners.contains(listener) ){
				result = _marketPlaceListeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public String toString(){
		return _gameId;
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
	public void onReceive(ProMaSiClient client, String recData) {
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
	}

	@Override
	public void onConnect(ProMaSiClient client) {
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
	}
}
