/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
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
	private List<ISinglePlayerGameListener> _listeners;
	
	/**
	 * 
	 * @param gameModel
	 * @throws NullArgumentException
	 */
	public SinglePlayerGame(GameModel gameModel)throws NullArgumentException{
		if(gameModel==null){
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		_gameModel=gameModel;
		_isRunning=false;
		_listeners=new LinkedList<ISinglePlayerGameListener>();
		_systemClock=new Clock();
		_systemClock.addListener(this);
		_gameModel.addListener(this);
	}

	@Override
	public String getGameDescription() {
		return _gameModel.getGameDescription();
	}

	@Override
	public synchronized void hireEmployee(String employeeId) throws NullArgumentException,IllegalArgumentException, SerializationException {
		_gameModel.hireEmployee(employeeId);
	}

	@Override
	public synchronized void dischargeEmployee(String employeeId)throws NullArgumentException, IllegalArgumentException,SerializationException {
		_gameModel.dischargeEmployee(employeeId);
		
	}

	@Override
	public synchronized boolean assignTasks(String employeeId, List<SerializableEmployeeTask> employeeTasks) {
		return _gameModel.assignTasks(employeeId, employeeTasks);
	}

	@Override
	public synchronized boolean addListener(ISinglePlayerGameListener listener)throws NullArgumentException {
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(_listeners.contains(listener)){
			return false;
		}
		
		_listeners.add(listener);
		return true;
	}

	@Override
	public synchronized boolean removeListener(ISinglePlayerGameListener listener) throws NullArgumentException {
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(!_listeners.contains(listener)){
			return false;
		}
		
		_listeners.remove(listener);
		return true;
	}

	@Override
	public synchronized boolean executeGameStep(DateTime currentDateTime)
			throws NullArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public synchronized boolean startGame() {
		if(!_isRunning){
			for(ISinglePlayerGameListener listener : _listeners){
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
			
			for(ISinglePlayerGameListener listener : _listeners){
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
		for(ISinglePlayerGameListener listener : _listeners){
			listener.projectAssigned(this, company, project, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void projectFinished(GameModel game, SerializableCompany company,SerializableProject project) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.projectFinished(this, company, project, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeHired(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company,SerializableEmployee employee) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.employeeHired(this, marketPlace, company, employee, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeDischarged(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company,SerializableEmployee employee) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.employeeDischarged(this, marketPlace, company, employee, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeTasksAssigned(GameModel game,SerializableCompany company, SerializableEmployee employee,List<SerializableEmployeeTask> employeeTasks) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.employeeTasksAttached(this, company, employee, employeeTasks, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void employeeTaskDetached(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company,SerializableEmployee employee, SerializableEmployeeTask employeeTask) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.employeeTaskDetached(this, marketPlace, company, employee, employeeTask, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void companyIsInsolvent(GameModel game, SerializableCompany company) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.companyIsInsolvent(this, company, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void onExecuteStep(GameModel game, SerializableCompany company,SerializableProject assignedProject) {
		for(ISinglePlayerGameListener listener : _listeners){
			listener.onExecuteStep(this, company, assignedProject, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void onTick(DateTime dateTime) {
		for( ISinglePlayerGameListener gameEventHandler : _listeners){
			gameEventHandler.onTick(this, dateTime);
		}
		
		try {
			_gameModel.executeGameStep(dateTime);
		} catch (NullArgumentException e) {
			//Logger
		}
	}

	@Override
	public void gameFinished(GameModel gameModel, SerializableCompany company) {
		for( ISinglePlayerGameListener gameEventHandler : _listeners){
			try {
				gameEventHandler.gameFinished(this, gameModel.getSerializableGameModel(),company);
			} catch (SerializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
