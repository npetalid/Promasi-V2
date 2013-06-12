/**
 * 
 */
package org.promasi.game.singleplayer;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.AGamesServer;
import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGame;
import org.promasi.game.IGameModelListener;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.EmployeeTaskModel;
import org.promasi.utilities.clock.IClock;
import org.promasi.utilities.clock.IClockListener;
import org.promasi.utilities.design.Observer;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author m1cRo
 * Represent the single player game.
 */
public class SinglePlayerGame extends Observer<IClientGameListener> implements IGame, IClockListener, IGameModelListener
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
	 * Instance of {@link IClock} interface
	 * implementation which represents the clock.
	 */
	private IClock _systemClock;
	
	/**
	 * 
	 */
	private AGamesServer _gamesServer;
	
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
	private DateTime _currentDateTime;
	
	/**
	 * 
	 * @param gamesServer
	 * @param gameModel
	 * @param systemClock
	 * @throws GameException
	 */
	public SinglePlayerGame(AGamesServer gamesServer, GameModel gameModel, IClock systemClock)throws GameException{
		if(gameModel==null){
			throw new GameException("Wrong argument gameModel==null");
		}
		
		if( gamesServer == null ){
			throw new GameException("Wrong argument gamesServer==null");
		}
		
		if( systemClock == null ){
			throw new GameException("Wrong argument systemClock == null");
		}
		
		_isRunning = false;
		_gamesServer = gamesServer;
		_lockObject = new ReentrantLock();
		_gameModel=gameModel;
		_systemClock = systemClock;
		_currentDateTime = _systemClock.getCurrentDateTime();
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
			result = _gameModel.hireEmployee(employeeId, _currentDateTime);
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
			result = _gameModel.dischargeEmployee(employeeId, _currentDateTime);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean assignTasks(final Map<String, List<EmployeeTaskModel> > employeeTasks) {
		boolean result = true;
		try
		{
			_lockObject.lock();
			for( Map.Entry<String, List<EmployeeTaskModel> > entry : employeeTasks.entrySet()){
				result &=_gameModel.assignTasks(entry.getKey(), entry.getValue(), _currentDateTime);
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
	public void onExecuteStep(GameModel game, CompanyModel company) {
		for(IClientGameListener listener : getListeners()){
			listener.onExecuteStep(this, company, _systemClock.getCurrentDateTime());
		}
	}

	@Override
	public void onTick(DateTime dateTime) {
		try{
			_lockObject.lock();
			_currentDateTime = dateTime;
			if( !_isRunning ){
				_isRunning = true;
				for( IClientGameListener listener : getListeners() ){	
					listener.gameStarted(this, _gameModel.getMemento(), _systemClock.getCurrentDateTime());
				}
			}
			
			for( IClientGameListener gameEventHandler : getListeners()){
				gameEventHandler.onTick(this, dateTime);
			}
			
			_gameModel.executeGameStep(dateTime);
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void gameFinished(GameModel gameModel, CompanyModel company) {
		try{
			_lockObject.lock();
			for( IClientGameListener gameEventHandler : getListeners()){
				Map<String, CompanyModel> players = new TreeMap<String, CompanyModel>();
				players.put(_gameModel.getPlayerId(), company);
				gameEventHandler.gameFinished(this, players);
			}
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public AGamesServer getGamesServer() {
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
			_gameModel.clearListeners();
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

	@Override
	public String getName() {
		return _gameModel.getName();
	}

	@Override
	public boolean removeTasks(List<EmployeeTaskModel> tasks) {
		return _gameModel.removeTasks(tasks);
	}
}
