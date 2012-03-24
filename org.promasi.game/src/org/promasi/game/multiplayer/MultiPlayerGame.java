/**
 * 
 */
package org.promasi.game.multiplayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGameModelListener;
import org.promasi.game.GameModelMemento;
import org.promasi.game.company.Company;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IEmployeeListener;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.MarketPlaceMemento;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utilities.clock.Clock;
import org.promasi.utilities.clock.IClockListener;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author m1cRo
 * Represent the Multiplayer game in ProMaSi system.
 * This game contains a list of the running game models, one for each client.
 */
public class MultiPlayerGame implements IMultiPlayerGame, IClockListener, IGameModelListener, IEmployeeListener, ICompanyListener, IDepartmentListener
{
	/**
	 * A shared market place.
	 */
	private MarketPlace _marketPlace;
	
	/**
	 * List of running game models each for one player.
	 */
	private Map<String, GameModel> _gameModels;
	
	/**
	 * 
	 */
	private Map<String, GameModelMemento> _finishedGames;
	
	/**
	 * 
	 */
	private List<IServerGameListener> _listeners;
	
	/**
	 * Game model prototype. Will be used in order to create a
	 * new {@link = GameModel} instance for a new player.
	 */
	private GameModelMemento _gameModel;
	
	/**
	 * 
	 */
	private boolean _isRunning;
	
	/**
	 * 
	 */
	private Clock _systemClock;
	
	/**
	 * 
	 */
	private String _gameOwnerId;
	
	/**
	 * Current date time.
	 */
	private DateTime _currentDateTime;
	
	/**
	 * Instance of {@link = ILogger} interface implementation.
	 * Needed for logging.
	 */
	private static final ILogger _logger = LoggerFactory.getInstance(MultiPlayerGame.class);
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * Constructor will initialize the object.
	 * @param clientId Client identification string.
	 * @param gameName Name of the game.
	 * @param gameDescription Game description.
	 * @param marketPlace A market place which will be used in this game.
	 * @param company Instance of {@link = Company} which represent the company.
	 * @param projects List of available projects.
	 * @throws GameException in case of initialization error.
	 */
	public MultiPlayerGame(String clientId, String gameName, String gameDescription, MarketPlace marketPlace, Company company, Queue<Project> projects)throws GameException{
		if(company==null){
			throw new GameException("Wrong argument company==null");
		}

		if(marketPlace==null){
			throw new GameException("Wrong argument marketPlace==null");
		}
		
		if(projects==null){
			throw new GameException("Wrong argument projects==null");
		}
		
		if(gameName==null){
			throw new GameException("Wrong argument gameName==null");
		}
		
		if(gameDescription==null){
			throw new GameException("Wrong argument gameDescription==null");
		}
		
		if(projects.isEmpty()){
			throw new GameException("Wrong argument projects is empty");
		}
		
		if(clientId==null){
			throw new GameException("Wrong argument client==null");
		}
		
		GameModel gameModel=new GameModel(gameName,gameDescription,marketPlace,company,projects);
		_gameModel=gameModel.getMemento();

		_marketPlace=marketPlace;

		_gameModels=new TreeMap<String, GameModel>(); 
		_finishedGames=new TreeMap<String, GameModelMemento>();
		gameModel.addListener(this);
		gameModel.addCompanyListener(this);
		_gameModels.put(clientId, gameModel);
		_listeners=new LinkedList<IServerGameListener>();
		_isRunning=false;
		_systemClock=new Clock();
		_currentDateTime = _systemClock.getCurrentDateTime();
		_systemClock.addListener(this);
		_gameOwnerId=clientId;
		_lockObject=new ReentrantLock();
		_logger.info("New multiplayer game initialization complete game owner:" + clientId + " game id:" + gameName );
	}


	@Override
	public String getGameName() {
		return _gameModel.getGameName();
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#getGameDescription()
	 */
	@Override
	public String getGameDescription() {
		return _gameModel.getGameDescription();
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#hireEmployee(java.lang.String)
	 */
	@Override
	public boolean hireEmployee(final String clientId, String employeeId) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(clientId!=null && employeeId != null && _gameModels.containsKey(clientId)){
				GameModel game=_gameModels.get(clientId);
				result = game.hireEmployee(employeeId, _currentDateTime);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#dischargeEmployee(java.lang.String)
	 */
	@Override
	public boolean dischargeEmployee(final String clientId, String employeeId) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			GameModel game=_gameModels.get(clientId);
			result = game.dischargeEmployee(employeeId, _currentDateTime);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#assignTasks(java.lang.String, java.util.List)
	 */
	@Override
	public boolean assignTasks(final String clientId, String employeeId,List<EmployeeTaskMemento> employeeTasks) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			result = _gameModels.get(clientId).assignTasks(employeeId, employeeTasks, _currentDateTime);
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#registerGameEventHandler(org.promasi.game.IGameListener)
	 */
	@Override
	public boolean addListener(IServerGameListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(listener!=null && !_listeners.contains(listener)){
				result = _listeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#removeGameEventHandler(org.promasi.game.IGameListener)
	 */
	@Override
	public boolean removeListener(IServerGameListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(listener!=null && _listeners.contains(listener)){
				result = _listeners.remove(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#startGame()
	 */
	@Override
	public boolean startGame(final String clientId) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(!_isRunning && _gameModels.containsKey(clientId)){
				
				_systemClock.start();
				_isRunning=true;
				result = true;
				LinkedList<IServerGameListener> listeners=new LinkedList<IServerGameListener>(_listeners);
				for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
					for(IServerGameListener listener : listeners){
						listener.gameStarted(entry.getKey(), this, entry.getValue().getMemento() , _currentDateTime);
					}
				}
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public void onTick(DateTime dateTime) {
		try{
			_lockObject.lock();
			_currentDateTime = dateTime;
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				entry.getValue().executeGameStep(_systemClock.getCurrentDateTime());
			}
			
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				for(IServerGameListener listener : _listeners){
					listener.onTick(entry.getKey(), this, dateTime);
				}
			}	
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public boolean joinGame(String playerId) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(playerId!=null && !_gameModels.containsKey(playerId)){
				Queue<Project> projects=new LinkedList<Project>();
				for(ProjectMemento project : _gameModel.getProjects()){
					projects.add(project.getProject());
				}
				
				Company company = _gameModel.getCompany().getCompany();
				GameModel gameModel=new GameModel(_gameModel.getGameName(), _gameModel.getGameDescription(), _marketPlace, company,projects);
				company.setOwner(playerId);
				gameModel.addListener(this);
				_gameModels.put(playerId, gameModel);
				
				List<String> playersList=new Vector<String>();
				for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
					playersList.add(entry.getKey());
				}
				
				for(IServerGameListener listener : _listeners){
					listener.playersListUpdated(this, playersList);
				}
				
				result = true;
			}

		}catch(Exception e){
			result = false;
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	public boolean leaveGame(final String clientId){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(clientId!=null && _gameModels.containsKey(clientId)){
				_gameModels.get(clientId).removeListener(this);
				_gameModels.remove(clientId);	
				
				List<String> playersList=new Vector<String>();
				for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
					playersList.add(entry.getKey());
				}
				
				for(IServerGameListener listener : _listeners){
					listener.playersListUpdated(this, playersList);
				}
				
				result = true;
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public boolean stopGame(String clientId){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setGameSpeed(String clientId, int newSpeed){
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getGamePlayers() {
		try{
			_lockObject.lock();
			List<String> players=new Vector<String>();
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				players.add(entry.getKey());
			}
			
			return players;
		}finally{
			_lockObject.unlock();
		}
	}


	@Override
	public boolean sendMessage(String clientId, String message){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(clientId!=null && message!=null){
				if(_gameModels.containsKey(clientId)){
					for(IServerGameListener listener : _listeners){
						listener.messageSent(clientId, this, message);
					}
					
					result = true;
				}
			}
	
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public void onExecuteStep(GameModel game, CompanyMemento company) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			if(entry.getValue()==game){
				for(IServerGameListener listener : _listeners){
					listener.onExecuteStep(entry.getKey(), this, company, _currentDateTime);
				}
			}
		}
	}


	@Override
	public String getGameOwnerId() {
		return _gameOwnerId;
	}


	@Override
	public void gameFinished(GameModel game, CompanyMemento company) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			if(entry.getValue()==game){
				if(!_finishedGames.containsKey(entry.getKey())){
					_finishedGames.put(entry.getKey(), entry.getValue().getMemento());
				}
			}
		}
		
		if(_finishedGames.size()==_gameModels.size()){
			for(IServerGameListener listener : _listeners){
				listener.gameFinished(_finishedGames);
			}
		}
	}


	@Override
	public void projectAssigned(String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.projectAssigned(owner, this, company, project, dateTime);
		}
	}


	@Override
	public void projectFinished(String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.projectFinished(owner, this, company, project, dateTime);
		}
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,ProjectMemento assignedProject, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.companyIsInsolvent(owner, this, company, dateTime);
		}
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyMemento company,ProjectMemento assignedProject, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.onExecuteStep(owner, this, company, dateTime);
		}
	}


	@Override
	public void taskAssigned(String supervisor,EmployeeMemento employee) {}


	@Override
	public void taskDetached(String supervisor,EmployeeMemento employee, EmployeeTaskMemento employeeTask) {}


	@Override
	public void employeeDischarged(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IServerGameListener listener : _listeners){
				GameModelMemento gameModel=entry.getValue().getMemento();
				MarketPlaceMemento sMarketPlace=gameModel.getMarketPlace();
				CompanyMemento sCompany=gameModel.getCompany();
				listener.employeeDischarged(entry.getKey(), this, sMarketPlace, sCompany, employee, dateTime);
			}
		}
	}


	@Override
	public void employeeHired(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IServerGameListener listener : _listeners){
				GameModelMemento gameModel=entry.getValue().getMemento();
				MarketPlaceMemento sMarketPlace=gameModel.getMarketPlace();
				CompanyMemento sCompany=gameModel.getCompany();
				listener.employeeHired(entry.getKey(), this, sMarketPlace, sCompany, employee, dateTime);
			}
		}
	}


	@Override
	public void companyAssigned(String owner, CompanyMemento company) {}


	@Override
	public void tasksAssignFailed(String supervisor, EmployeeMemento employee) {}


	@Override
	public void tasksAssigned(String director, DepartmentMemento department, DateTime dateTime) {}


	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department, DateTime dateTime) {}


	@Override
	public void departmentAssigned(String director, DepartmentMemento department, DateTime dateTime) {}
}
