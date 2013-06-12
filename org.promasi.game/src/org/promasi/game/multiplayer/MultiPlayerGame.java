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
import org.promasi.game.IGameFactory;
import org.promasi.game.IGameModelListener;
import org.promasi.game.company.Company;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IEmployeeListener;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.DepartmentModel;
import org.promasi.game.model.EmployeeModel;
import org.promasi.game.model.EmployeeTaskModel;
import org.promasi.game.model.GameModelModel;
import org.promasi.game.model.MarketPlaceModel;
import org.promasi.game.model.ProjectModel;
import org.promasi.game.project.Project;
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

	private GameModelModel _gameModel;
	
	/**
	 * List of running game models each for one player.
	 */
	private Map<String, GameModel> _gameModels = new TreeMap<String, GameModel>();
	
	/**
	 * 
	 */
	private Map<String, GameModelModel> _finishedGames = new TreeMap<String, GameModelModel>();
	
	/**
	 * 
	 */
	private List<IServerGameListener> _listeners;
	
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
	
	private IGameFactory _factory;
	
	private MarketPlace _marketPlace;
	
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
	public MultiPlayerGame(String clientId, GameModelModel model, MarketPlace marketPlace, IGameFactory factory)throws GameException{
		if( factory == null ){
			throw new GameException("Wrong argument factory");
		}
		
		GameModel gameModel = factory.createGameModel(model);
		if( gameModel == null ){
			throw new GameException("Wrong argument memento");
		}
		
		if( marketPlace == null ){
			throw new GameException("Wrong argument marketPlace");
		}

		_marketPlace = marketPlace;
		_factory = factory;
		_gameModel = model;
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
		_logger.info("New multiplayer game initialization complete game owner:" + clientId + " game id:" + _gameModel.getGameName() );
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
	public boolean assignTasks(final String clientId, String employeeId,List<EmployeeTaskModel> employeeTasks) {
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
				for(ProjectModel project : _gameModel.getProjects()){
					projects.add(_factory.createProject(project));
				}
				
				Company company = _factory.createCompany(_gameModel.getCompany());
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
	public void onExecuteStep(GameModel game, CompanyModel company) {
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
	public void gameFinished(GameModel game, CompanyModel company) {
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
	public void projectAssigned(String owner, CompanyModel company, ProjectModel project, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.projectAssigned(owner, this, company, project, dateTime);
		}
	}


	@Override
	public void projectFinished(String owner, CompanyModel company, ProjectModel project, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.projectFinished(owner, this, company, project, dateTime);
		}
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyModel company,ProjectModel assignedProject, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.companyIsInsolvent(owner, this, company, dateTime);
		}
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyModel company,ProjectModel assignedProject, DateTime dateTime) {
		for(IServerGameListener listener : _listeners){
			listener.onExecuteStep(owner, this, company, dateTime);
		}
	}


	@Override
	public void taskAssigned(String supervisor,EmployeeModel employee) {}


	@Override
	public void taskDetached(String supervisor,EmployeeModel employee, EmployeeTaskModel employeeTask) {}


	@Override
	public void employeeDischarged(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IServerGameListener listener : _listeners){
				GameModelModel gameModel=entry.getValue().getMemento();
				MarketPlaceModel sMarketPlace=gameModel.getMarketPlace();
				CompanyModel sCompany=gameModel.getCompany();
				listener.employeeDischarged(entry.getKey(), this, sMarketPlace, sCompany, employee, dateTime);
			}
		}
	}


	@Override
	public void employeeHired(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IServerGameListener listener : _listeners){
				GameModelModel gameModel=entry.getValue().getMemento();
				MarketPlaceModel sMarketPlace=gameModel.getMarketPlace();
				CompanyModel sCompany=gameModel.getCompany();
				listener.employeeHired(entry.getKey(), this, sMarketPlace, sCompany, employee, dateTime);
			}
		}
	}


	@Override
	public void companyAssigned(String owner, CompanyModel company) {}


	@Override
	public void tasksAssignFailed(String supervisor, EmployeeModel employee) {}


	@Override
	public void tasksAssigned(String director, DepartmentModel department, DateTime dateTime) {}


	@Override
	public void tasksAssignFailed(String director, DepartmentModel department, DateTime dateTime) {}


	@Override
	public void departmentAssigned(String director, DepartmentModel department, DateTime dateTime) {}
}
