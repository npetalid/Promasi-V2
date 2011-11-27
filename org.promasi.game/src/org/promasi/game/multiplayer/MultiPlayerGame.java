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
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utilities.clock.Clock;
import org.promasi.utilities.clock.IClockListener;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class MultiPlayerGame implements IMultiPlayerGame, IClockListener, IGameModelListener, IEmployeeListener, ICompanyListener, IDepartmentListener
{
	/**
	 * 
	 */
	private MarketPlace _marketPlace;
	
	/**
	 * 
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
	 * 
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
	 * 
	 */
	private Object _lockObject;
	
	/**
	 * 
	 * @param clientId
	 * @param gameName
	 * @param gameDescription
	 * @param marketPlace
	 * @param company
	 * @param projects
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public MultiPlayerGame(final String clientId, final String gameName, final String gameDescription, final MarketPlace marketPlace, final Company company,final Queue<Project> projects)throws GameException{
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
			throw new IllegalArgumentException("Wrong argument projects is empty");
		}
		
		if(clientId==null){
			throw new GameException("Wrong argument client==null");
		}
		
		GameModel gameModel=new GameModel(gameName,gameDescription,marketPlace,company,projects);
		try {
			_gameModel=gameModel.getSerializableGameModel();
		} catch (SerializationException e) {
			throw new IllegalArgumentException("Failed because "+ e.getMessage());
		}
		
		_marketPlace=marketPlace;

		_gameModels=new TreeMap<String, GameModel>(); 
		_finishedGames=new TreeMap<String, GameModelMemento>();
		gameModel.addListener(this);
		gameModel.addCompanyListener(this);
		_gameModels.put(clientId, gameModel);
		_listeners=new LinkedList<IServerGameListener>();
		_isRunning=false;
		_systemClock=new Clock();
		_systemClock.addListener(this);
		_gameOwnerId=clientId;
		_lockObject=new Object();
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
	public void hireEmployee(final String clientId, String employeeId) throws NullArgumentException,IllegalArgumentException, SerializationException {
		synchronized(_lockObject){
			if(clientId==null){
				throw new NullArgumentException("Wrong argument client==null");
			}
			
			if(!_gameModels.containsKey(clientId)){
				throw new IllegalArgumentException("Wrong argument client");
			}
			
			if(employeeId==null){
				throw new NullArgumentException("Wrong argument employeeId==null");
			}
			
			GameModel game=_gameModels.get(clientId);
			game.hireEmployee(employeeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#dischargeEmployee(java.lang.String)
	 */
	@Override
	public void dischargeEmployee(final String clientId, String employeeId)throws NullArgumentException, IllegalArgumentException, SerializationException {
		synchronized(_lockObject){
			if(clientId==null){
				throw new NullArgumentException("Wrong argument client==null");
			}
			
			if(!_gameModels.containsKey(clientId)){
				throw new IllegalArgumentException("Wrong argument client");
			}
			
			if(employeeId==null){
				throw new NullArgumentException("Wrong argument employeeId==null");
			}
			
			GameModel game=_gameModels.get(clientId);
			game.dischargeEmployee(employeeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#assignTasks(java.lang.String, java.util.List)
	 */
	@Override
	public boolean assignTasks(final String clientId, String employeeId,List<EmployeeTaskMemento> employeeTasks)throws NullArgumentException, IllegalArgumentException {
		synchronized(_lockObject){
			if(clientId==null){
				throw new NullArgumentException("Wrong argument client==null");
			}
			
			if(!_gameModels.containsKey(clientId)){
				throw new IllegalArgumentException("Wrong argument client");
			}
			
			if(employeeId==null){
				throw new NullArgumentException("Wrong argument employeeId==null");
			}
			
			
			return _gameModels.get(clientId).assignTasks(employeeId, employeeTasks);
		}
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#registerGameEventHandler(org.promasi.game.IGameListener)
	 */
	@Override
	public boolean addListener(IServerGameListener listener)throws NullArgumentException {
		synchronized(_lockObject){
			if(listener==null){
				throw new NullArgumentException("Wrong argument listener==null");
			}
			
			if(_listeners.contains(listener)){
				return false;
			}
			
			_listeners.add(listener);
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#removeGameEventHandler(org.promasi.game.IGameListener)
	 */
	@Override
	public boolean removeListener(IServerGameListener listener)throws NullArgumentException {
		synchronized(_lockObject){
			if(listener==null){
				throw new NullArgumentException("Wrong argument listener==null");
			}
			
			if(!_listeners.contains(listener)){
				return false;
			}
			
			_listeners.remove(listener);
			return true;	
		}
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGame#startGame()
	 */
	@Override
	public boolean startGame(final String clientId)throws NullArgumentException {
		synchronized(_lockObject){
			if(_isRunning){
				return false;
			}
			
			if(!_gameModels.containsKey(clientId)){
				return false;
			}
			
			_systemClock.start();
			_isRunning=true;
			
			LinkedList<IServerGameListener> listeners=new LinkedList<IServerGameListener>(_listeners);
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				for(IServerGameListener listener : listeners){
					try {
						listener.gameStarted(entry.getKey(), this, entry.getValue().getSerializableGameModel() , _systemClock.getCurrentDateTime());
					} catch (SerializationException e) {
						//Logger
					}
				}
			}
			
			return true;
		}
	}

	

	@Override
	public void onTick(DateTime dateTime) {
		synchronized(_lockObject){
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				entry.getValue().executeGameStep(_systemClock.getCurrentDateTime());
			}
			
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				for(IServerGameListener listener : _listeners){
					listener.onTick(entry.getKey(), this, dateTime);
				}
			}	
		}
	}

	@Override
	public boolean joinGame(String playerId) throws NullArgumentException {
		synchronized(_lockObject){
			if(playerId==null){
				throw new NullArgumentException("Wrong argument client==null");
			}
			
			if(_gameModels.containsKey(playerId)){
				return false;
			}

			try {
				Queue<Project> projects=new LinkedList<Project>();
				for(ProjectMemento project : _gameModel.getProjects()){
					projects.add(project.getProject());
				}
				GameModel gameModel=new GameModel(_gameModel.getGameName(), _gameModel.getGameDescription(), _marketPlace, _gameModel.getCompany().getCompany(),projects);
				gameModel.addListener(this);
				_gameModels.put(playerId, gameModel);
				
				List<String> playersList=new Vector<String>();
				for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
					playersList.add(entry.getKey());
				}
				
				for(IServerGameListener listener : _listeners){
					listener.playersListUpdated(this, playersList);
				}
			} catch (GameException e) {
				return false;
			} catch (SerializationException e) {
				return false;
			}
			
			return true;
		}
	}
	
	/**
	 * 
	 */
	public boolean leaveGame(final String clientId){
		synchronized(_lockObject){
			if(clientId==null){
				return false;
			}
			
			if(!_gameModels.containsKey(clientId)){
				return false;
			}
			
			_gameModels.get(clientId).removeGameModelListener(this);
			_gameModels.remove(clientId);	
			
			List<String> playersList=new Vector<String>();
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				playersList.add(entry.getKey());
			}
			
			for(IServerGameListener listener : _listeners){
				listener.playersListUpdated(this, playersList);
			}
					
			return true;
		}
	}

	@Override
	public boolean stopGame(String clientId) throws NullArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setGameSpeed(String clientId, int newSpeed) throws NullArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getGamePlayers() {
		synchronized (_lockObject) {
			List<String> players=new Vector<String>();
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				players.add(entry.getKey());
			}
			
			return players;
		}
	}


	@Override
	public boolean sendMessage(String clientId, String message)throws NullArgumentException {
		synchronized(_lockObject){
			if(clientId==null){
				throw new NullArgumentException("Wrong argument clientId==null");
			}
			
			if(message==null){
				throw new NullArgumentException("Wrong argument message==null");
			}
			
			if(_gameModels.containsKey(clientId)){
				for(IServerGameListener listener : _listeners){
					listener.messageSent(clientId, this, message);
				}
			}
			
			return false;	
		}
	}

	@Override
	public void onExecuteStep(GameModel game, CompanyMemento company, ProjectMemento assignedProject) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			if(entry.getValue()==game){
				for(IServerGameListener listener : _listeners){
					listener.onExecuteStep(entry.getKey(), this, company, assignedProject, _systemClock.getCurrentDateTime());
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
					try {
						_finishedGames.put(entry.getKey(), entry.getValue().getSerializableGameModel());
					} catch (SerializationException e) {
						//Logger
					}
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
			listener.onExecuteStep(owner, this, company, assignedProject, dateTime);
		}
	}


	@Override
	public void taskAttached(String supervisor,EmployeeMemento employee,List<EmployeeTaskMemento> employeeTask) {
		// TODO Auto-generated method stub
	}


	@Override
	public void taskDetached(String supervisor,EmployeeMemento employee, EmployeeTaskMemento employeeTask) {
		// TODO Auto-generated method stub
	}


	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		/*for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IServerGameListener listener : _listeners){
				try{
					GameModelMemento gameModel=entry.getValue().getSerializableGameModel();
					MarketPlaceMemento sMarketPlace=gameModel.getMarketPlace();
					CompanyMemento sCompany=gameModel.getCompany();
					listener.employeeDischarged(entry.getKey(), this, sMarketPlace, sCompany, employee, _systemClock.getCurrentDateTime());
				}catch(SerializationException e){
					//Logger
				}
			}
		}*/
	}


	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		/*for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IServerGameListener listener : _listeners){
				try{
					GameModelMemento gameModel=entry.getValue().getSerializableGameModel();
					MarketPlaceMemento sMarketPlace=gameModel.getMarketPlace();
					CompanyMemento sCompany=gameModel.getCompany();
					listener.employeeHired(entry.getKey(), this, sMarketPlace, sCompany, employee, _systemClock.getCurrentDateTime());
				}catch(SerializationException e){
					//Logger
				}
			}
		}*/
	}


	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tasksAssignFailed(String supervisor, EmployeeMemento employee,
			List<EmployeeTaskMemento> employeeTasks) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}
}
