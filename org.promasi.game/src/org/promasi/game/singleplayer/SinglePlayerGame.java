/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGame;
import org.promasi.game.IGameModelListener;
import org.promasi.game.IGamesServer;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.utilities.clock.Clock;
import org.promasi.utilities.clock.IClockListener;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class SinglePlayerGame implements IGame, IClockListener, IGameModelListener
{
	/**
	 * Logger
	 */
	private static final ILogger CONST_LOGGER = LoggerFactory.getInstance(SinglePlayerGame.class);
	
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
	public boolean hireEmployee(String employeeId) {
		boolean result = false;
		try {
			_lockObject.lock();
			result = _gameModel.hireEmployee(employeeId);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean dischargeEmployee(String employeeId){
		boolean result = false;
		
		try {
			_lockObject.lock();
			result = _gameModel.dischargeEmployee(employeeId);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean assignTasks(final Map<String, List<EmployeeTaskMemento> > employeeTasks) {
		boolean result = true;
		try
		{
			_lockObject.lock();
			for( Map.Entry<String, List<EmployeeTaskMemento> > entry : employeeTasks.entrySet()){
				result &=_gameModel.assignTasks(entry.getKey(), entry.getValue());
			}
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
	public boolean startGame() {
		try{
			_lockObject.lock();
			if( !_systemClock.isActive() ){
				_systemClock.start();	
			}
		}finally{
			_lockObject.unlock();
		}

		CONST_LOGGER.info("SinglePlayer game started");
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
	public void onExecuteStep(GameModel game, CompanyMemento company) {
		for(IClientGameListener listener : _listeners){
			listener.onExecuteStep(this, company, _systemClock.getCurrentDateTime());
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
						listener.gameStarted(this, _gameModel.getSerializableGameModel(), _systemClock.getCurrentDateTime());
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
	public void gameFinished(GameModel gameModel, CompanyMemento company) {
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

	@Override
	public void removeListeners() {
		try{
			_lockObject.lock();
			_gameModel.removeListeners();
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public boolean addDepartmentListener(IDepartmentListener listener) {
		return _gameModel.addDepartmentListener(listener);
	}

	@Override
	public boolean removeDepartmentListener(IDepartmentListener listener) {
		return _gameModel.removeDepartmentListener(listener);
	}

	@Override
	public boolean addMarketPlaceListener(IMarketPlaceListener listener) {
		return _gameModel.addMarketPlaceListener(listener);
	}

	@Override
	public boolean removeMarketPlaceListener(IMarketPlaceListener listener) {
		return _gameModel.removeMarketPlaceListener(listener);
	}
}
