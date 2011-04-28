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
import org.promasi.game.GameModel;
import org.promasi.game.IGameModelListener;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.Company;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.Project;
import org.promasi.game.project.SerializableProject;
import org.promasi.utilities.clock.Clock;
import org.promasi.utilities.clock.IClockListener;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class MultiPlayerGame implements IMultiPlayerGame, IClockListener, IGameModelListener
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
	private Map<String, SerializableGameModel> _finishedGames;
	
	/**
	 * 
	 */
	private List<IMultiPlayerGameListener> _listeners;
	
	/**
	 * 
	 */
	private SerializableGameModel _gameModel;
	
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
	public MultiPlayerGame(final String clientId, final String gameName, final String gameDescription, final MarketPlace marketPlace, final Company company,final Queue<Project> projects)throws NullArgumentException, IllegalArgumentException{
		if(company==null){
			throw new NullArgumentException("Wrong argument company==null");
		}

		if(marketPlace==null){
			throw new NullArgumentException("Wrong argument marketPlace==null");
		}
		
		if(projects==null){
			throw new NullArgumentException("Wrong argument projects==null");
		}
		
		if(gameName==null){
			throw new NullArgumentException("Wrong argument gameName==null");
		}
		
		if(gameDescription==null){
			throw new NullArgumentException("Wrong argument gameDescription==null");
		}
		
		if(projects.isEmpty()){
			throw new IllegalArgumentException("Wrong argument projects is empty");
		}
		
		if(clientId==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		GameModel gameModel=new GameModel(gameName,gameDescription,marketPlace,company,projects);
		try {
			_gameModel=gameModel.getSerializableGameModel();
		} catch (SerializationException e) {
			throw new IllegalArgumentException("Failed because "+ e.getMessage());
		}
		
		_marketPlace=marketPlace;

		_gameModels=new TreeMap<String, GameModel>(); 
		_finishedGames=new TreeMap<String, SerializableGameModel>();
		gameModel.addListener(this);
		_gameModels.put(clientId, gameModel);
		_listeners=new LinkedList<IMultiPlayerGameListener>();
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
	public boolean assignTasks(final String clientId, String employeeId,List<SerializableEmployeeTask> employeeTasks)throws NullArgumentException, IllegalArgumentException {
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
	public boolean addListener(IMultiPlayerGameListener listener)throws NullArgumentException {
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
	public boolean removeListener(IMultiPlayerGameListener listener)throws NullArgumentException {
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
			
			LinkedList<IMultiPlayerGameListener> listeners=new LinkedList<IMultiPlayerGameListener>(_listeners);
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				for(IMultiPlayerGameListener listener : listeners){
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
				try {
					entry.getValue().executeGameStep(_systemClock.getCurrentDateTime());
				} catch (NullArgumentException e) {
					//Logger
				}
			}
			
			for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
				for(IMultiPlayerGameListener listener : _listeners){
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
				for(SerializableProject project : _gameModel.getProjects()){
					projects.add(project.getProject());
				}
				GameModel gameModel=new GameModel(_gameModel.getGameName(), _gameModel.getGameDescription(), _marketPlace, _gameModel.getCompany().getCompany(),projects);
				gameModel.addListener(this);
				_gameModels.put(playerId, gameModel);
				
				List<String> playersList=new Vector<String>();
				for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
					playersList.add(entry.getKey());
				}
				
				for(IMultiPlayerGameListener listener : _listeners){
					listener.playersListUpdated(this, playersList);
				}
			} catch (IllegalArgumentException e) {
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
	public boolean leaveGame(final String clientId)throws NullArgumentException{
		synchronized(_lockObject){
			if(clientId==null){
				throw new NullArgumentException("Wrong argument client==null");
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
			
			for(IMultiPlayerGameListener listener : _listeners){
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
				for(IMultiPlayerGameListener listener : _listeners){
					listener.messageSent(clientId, this, message);
				}
			}
			
			return false;	
		}
	}


	@Override
	public void projectAssigned(GameModel game, SerializableCompany company, SerializableProject project) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			if(entry.getValue()==game){
				for(IMultiPlayerGameListener listener : _listeners){
					listener.projectAssigned(entry.getKey(), this, company, project, _systemClock.getCurrentDateTime());
				}
			}
		}
	}


	@Override
	public void projectFinished(GameModel game, SerializableCompany company,SerializableProject project) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			if(entry.getValue()==game){
				for(IMultiPlayerGameListener listener : _listeners){
					listener.projectFinished(entry.getKey(), this, company, project, _systemClock.getCurrentDateTime());
				}
			}
		}
		
	}


	@Override
	public void employeeHired(GameModel game,SerializableMarketPlace marketPlace, SerializableCompany company, SerializableEmployee employee) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IMultiPlayerGameListener listener : _listeners){
				try{
					SerializableGameModel gameModel=entry.getValue().getSerializableGameModel();
					SerializableMarketPlace sMarketPlace=gameModel.getMarketPlace();
					SerializableCompany sCompany=gameModel.getCompany();
					listener.employeeHired(entry.getKey(), this, sMarketPlace, sCompany, employee, _systemClock.getCurrentDateTime());
				}catch(SerializationException e){
					//Logger
				}
			}
		}
	}


	@Override
	public void employeeDischarged(GameModel game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			for(IMultiPlayerGameListener listener : _listeners){
				try{
					SerializableGameModel gameModel=entry.getValue().getSerializableGameModel();
					SerializableMarketPlace sMarketPlace=gameModel.getMarketPlace();
					SerializableCompany sCompany=gameModel.getCompany();
					listener.employeeDischarged(entry.getKey(), this, sMarketPlace, sCompany, employee, _systemClock.getCurrentDateTime());
				}catch(SerializationException e){
					//Logger
				}
			}
		}
	}


	@Override
	public void employeeTasksAssigned(GameModel game, SerializableCompany company, SerializableEmployee employee, List<SerializableEmployeeTask> employeeTasks) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void employeeTaskDetached(GameModel game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee, SerializableEmployeeTask employeeTask) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void companyIsInsolvent(GameModel game, SerializableCompany company) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onExecuteStep(GameModel game, SerializableCompany company, SerializableProject assignedProject) {
		for(Map.Entry<String, GameModel> entry : _gameModels.entrySet()){
			if(entry.getValue()==game){
				for(IMultiPlayerGameListener listener : _listeners){
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
	public void gameFinished(GameModel game, SerializableCompany company) {
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
			for(IMultiPlayerGameListener listener : _listeners){
				listener.gameFinished(_finishedGames);
			}
		}
	}
}
