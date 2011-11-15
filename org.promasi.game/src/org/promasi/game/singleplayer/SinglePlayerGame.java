/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGame;
import org.promasi.game.IGameModelListener;
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
	public SinglePlayerGame(GameModel gameModel)throws GameException{
		if(gameModel==null){
			throw new GameException("Wrong argument gameModel==null");
		}
		
		_gameModel=gameModel;
		_isRunning=false;
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
	public synchronized void hireEmployee(String employeeId) throws GameException {
		try {
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
		}
	}

	@Override
	public synchronized void dischargeEmployee(String employeeId)throws GameException {
		try {
			_gameModel.dischargeEmployee(employeeId);
		} catch (IllegalArgumentException e) {
			throw new GameException(e.toString());
		} catch (NullArgumentException e) {
			throw new GameException(e.toString());
		} catch (SerializationException e) {
			throw new GameException(e.toString());
		}
	}

	@Override
	public synchronized boolean assignTasks(String employeeId, List<SerializableEmployeeTask> employeeTasks) {
		return _gameModel.assignTasks(employeeId, employeeTasks);
	}

	@Override
	public synchronized boolean addListener(IClientGameListener listener)throws GameException {
		if(listener==null){
			throw new GameException("Wrong argument listener==null");
		}
		
		if(_listeners.contains(listener)){
			return false;
		}
		
		_listeners.add(listener);
		return true;
	}

	@Override
	public synchronized boolean removeListener(IClientGameListener listener) throws GameException {
		if(listener==null){
			throw new GameException("Wrong argument listener==null");
		}
		
		if(!_listeners.contains(listener)){
			return false;
		}
		
		_listeners.remove(listener);
		return true;
	}

	@Override
	public synchronized boolean executeGameStep(Date currentDateTime) throws GameException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean startGame() {
		if(!_isRunning){
			for(IClientGameListener listener : _listeners){
				try {
					listener.gameStarted(this, _gameModel.getSerializableGameModel(), _systemClock.getCurrentDateTime());
				} catch (SerializationException e) {
					_isRunning=false;
					_systemClock.stop();
					return false;
				}
			}
			
			_systemClock.start();
			_isRunning=true;
			
			for(IClientGameListener listener : _listeners){
				try {
					listener.gameStarted(this, _gameModel.getSerializableGameModel(), _systemClock.getCurrentDateTime());
				} catch (SerializationException e) {
					//Logger
				}
			}
		}
	
		return true;
	}
	
	@Override
	public synchronized boolean setGameSpeed(int newSpeed) {
		_systemClock.setDelay(newSpeed);
		return true;
	}

	@Override
	public synchronized boolean stopGame() {
		_systemClock.stop();
		_isRunning=false;
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
		for( IClientGameListener gameEventHandler : _listeners){
			gameEventHandler.onTick(this, dateTime);
		}
		
		_gameModel.executeGameStep(dateTime);
	}

	@Override
	public void gameFinished(GameModel gameModel, SerializableCompany company) {
		for( IClientGameListener gameEventHandler : _listeners){
			try {
				gameEventHandler.gameFinished(this, gameModel.getSerializableGameModel(),company);
			} catch (SerializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
