/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGame;
import org.promasi.game.IGameModelListener;
import org.promasi.game.IGamesServer;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.SerializableProject;
import org.promasi.utilities.clock.Clock;
import org.promasi.utilities.clock.IClockListener;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class SinglePlayerGame implements IGame, IClockListener, IGameModelListener
{
	/**
	 * 
	 */
	private GameModel _gameModel;
	
	/**
	 * 
	 */
	private Clock _systemClock;
	
	/**
	 * 
	 */
	private IGamesServer _gamesServer;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private boolean _isRunning;
	
	/**
	 * 
	 */
	private List<IClientGameListener> _listeners;
	
	/**
	 * 
	 * @param gameModel
	 * @throws NullArgumentException
	 */
	public SinglePlayerGame(IGamesServer gamesServer, GameModel gameModel)throws GameException{
		if(gameModel==null){
			throw new GameException("Wrong argument gameModel==null");
		}
		
		if( gamesServer == null ){
			throw new GameException("Wrong argument gamesServer==null");
		}
		
		_isRunning = false;
		_gamesServer = gamesServer;
		_lockObject = new ReentrantLock();
		_gameModel=gameModel;
		_listeners=new LinkedList<IClientGameListener>();
		_systemClock=new Clock();
		_systemClock.addListener(this);
		if(!_gameModel.addListener(this) )
		{
			throw new GameException("SystemClock addListener failed");
		}
	}

	@Override
	public String getGameDescription() {
		return _gameModel.getGameDescription();
	}
	
	@Override
	public String toString(){
		return _gameModel.getName();
	}

	@Override
	public void hireEmployee(String employeeId) throws GameException {
		try {
			_lockObject.lock();
			_gameModel.hireEmployee(employeeId);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void dischargeEmployee(String employeeId)throws GameException {
		try {
			_lockObject.lock();
			_gameModel.dischargeEmployee(employeeId);
		} catch (IllegalArgumentException e) {
			throw new GameException(e.toString());
		} catch (NullArgumentException e) {
			throw new GameException(e.toString());
		} catch (SerializationException e) {
			throw new GameException(e.toString());
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public boolean assignTasks(String employeeId, List<SerializableEmployeeTask> employeeTasks) {
		boolean result = false;
		try
		{
			_lockObject.lock();
			result =_gameModel.assignTasks(employeeId, employeeTasks);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean addListener(IClientGameListener listener) {
		boolean result = false;
		try{
			_lockObject.lock();
			if(listener!=null){
				if(!_listeners.contains(listener)){
					result =_listeners.add(listener);
				}
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
			if(listener!=null){
				if( _listeners.contains(listener) ){
					result =_listeners.remove(listener);
				}
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}

	@Override
	public boolean executeGameStep(Date currentDateTime) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startGame() {
		try{
			_lockObject.lock();
			if( !_systemClock.isActive() ){
				_systemClock.start();	
			}
		}finally{
			_lockObject.unlock();
		}

		return true;
	}
	
	@Override
	public boolean setGameSpeed(int newSpeed) {
		try{
			_lockObject.lock();
			_systemClock.setDelay(newSpeed);
		}finally{
			_lockObject.unlock();
		}
		
		return true;
	}

	@Override
	public boolean stopGame() {
		try{
			_lockObject.lock();
			_systemClock.removeListener(this);
			_systemClock.stop();
		}finally{
			_lockObject.unlock();
		}

		return true;
	}
	

	@Override
	public void projectAssigned(GameModel game, SerializableCompany company, SerializableProject project) {
		for(IClientGameListener listener : _listeners){
			listener.projectAssigned(this, company, project, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void projectFinished(GameModel game, SerializableCompany company,SerializableProject project) {
		for(IClientGameListener listener : _listeners){
			listener.projectFinished(this, company, project, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeHired(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company,SerializableEmployee employee) {
		for(IClientGameListener listener : _listeners){
			listener.employeeHired(this, marketPlace, company, employee, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeDischarged(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company,SerializableEmployee employee) {
		for(IClientGameListener listener : _listeners){
			listener.employeeDischarged(this, marketPlace, company, employee, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeTasksAssigned(GameModel game,SerializableCompany company, SerializableEmployee employee,List<SerializableEmployeeTask> employeeTasks) {
		for(IClientGameListener listener : _listeners){
			listener.employeeTasksAttached(this, company, employee, employeeTasks, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeTaskDetached(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company,SerializableEmployee employee, SerializableEmployeeTask employeeTask) {
		for(IClientGameListener listener : _listeners){
			listener.employeeTaskDetached(this, marketPlace, company, employee, employeeTask, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void companyIsInsolvent(GameModel game, SerializableCompany company) {
		for(IClientGameListener listener : _listeners){
			listener.companyIsInsolvent(this, company, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void onExecuteStep(GameModel game, SerializableCompany company,SerializableProject assignedProject) {
		for(IClientGameListener listener : _listeners){
			listener.onExecuteStep(this, company, assignedProject, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void onTick(DateTime dateTime) {
		try{
			_lockObject.lock();
			if( !_isRunning ){
				_isRunning = true;
				for( IClientGameListener listener : _listeners ){	
					try {
						listener.gameStarted(this, _gameModel.getSerializableGameModel(), _systemClock.getCurrentDateTime() );
					} catch (SerializationException e) {
						_systemClock.stop();
					}
				}
			}
			
			for( IClientGameListener gameEventHandler : _listeners){
				gameEventHandler.onTick(this, dateTime);
			}
			
			_gameModel.executeGameStep(dateTime);
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void gameFinished(GameModel gameModel, SerializableCompany company) {
		try{
			_lockObject.lock();
			for( IClientGameListener gameEventHandler : _listeners){
				try {
					gameEventHandler.gameFinished(this, gameModel.getSerializableGameModel(),company);
				} catch (SerializationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public IGamesServer getGamesServer() {
		return _gamesServer;
	}

	@Override
	public boolean addCompanyListener(ICompanyListener listener) {
		return _gameModel.addCompanyListener(listener);
	}

	@Override
	public boolean removeCompanyListener(ICompanyListener listener) {
		return _gameModel.removeCompanyListener(listener);
	}
}
