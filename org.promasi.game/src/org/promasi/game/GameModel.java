/**
 * 
 */
package org.promasi.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;
import org.promasi.game.company.Company;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.company.IMarketPlaceListener;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.project.Project;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class GameModel
{
	/**
	 * 
	 */
	protected String _gameName;
	
	/**
	 * 
	 */
	protected String _gameDescription;
	
	/**
	 * 
	 */
	protected MarketPlace _marketPlace;
	
	/**
	 * 
	 */
	protected Company _company;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	protected Queue<Project> _projects;
	
	/**
	 * 
	 */
	protected Queue<Project> _runnedProjects;
	
	/**
	 * 
	 */
	private List<IGameModelListener> _listeners;
	
	/**
	 * 
	 */
	private boolean _gameFinished;
	
	/**
	 * 
	 * @param gameName
	 * @param gameDescription
	 * @param marketPlace
	 * @param company
	 * @param projects
	 * @throws IllegalArgumentException
	 * @throws NullArgumentException
	 */
	public GameModel(final String gameName, final String gameDescription, final MarketPlace marketPlace, final Company company,final Queue<Project> projects)throws GameException{
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
		
		_projects=projects;
		_marketPlace=marketPlace;
		_company=company;
		_listeners=new LinkedList<IGameModelListener>();
		_gameName=gameName;
		_gameDescription=gameDescription;
		_runnedProjects=new LinkedList<Project>();
		_gameFinished=false;
		_lockObject = new ReentrantLock();
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public GameModelMemento getSerializableGameModel()throws SerializationException{
		try {
			_lockObject.lock();
			return new GameModelMemento(this);
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		} finally {
			_lockObject.unlock();
		}
	}

	public boolean hireEmployee(String employeeId) {
		return _marketPlace.hireEmployee(_company, employeeId);
	}

	public boolean dischargeEmployee(String employeeId) {
		return _company.dischargeEmployee(employeeId, _marketPlace);
	}

	/**
	 * 
	 * @return
	 */
	public String getName(){
		return _gameName;
	}
	
	/**
	 * 
	 * @param employeeId
	 * @param employeeTasks
	 * @return
	 */
	public boolean assignTasks(String employeeId,List<EmployeeTaskMemento> employeeTasks) {
		try {
			return _company.assignTasks(employeeId, employeeTasks);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getGameDescription() {
		return _gameDescription;
	}

	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean addCompanyListener( ICompanyListener listener ){
		return _company.addListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeDepartmentListener( IDepartmentListener listener ){
		return _company.removeITDepartmentListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean addDepartmentListener( IDepartmentListener listener ){
		return _company.addITDepartmentListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeCompanyListener( ICompanyListener listener ){
		return _company.removeListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean removeMarketPlaceListener( IMarketPlaceListener listener ){
		return _marketPlace.removeListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 */
	public boolean addMarketPlaceListener( IMarketPlaceListener listener ){
		return _marketPlace.addListener(listener);
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean removeGameModelListener(IGameModelListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(listener!=null){
				if(_listeners.contains(listener)){
					result = _listeners.remove(listener);
				}
			}
			
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean addListener(IGameModelListener listener) {
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(listener!=null){
				if( !_listeners.contains(listener) ){
					result = _listeners.add(listener);
				}
			}
			
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/**
	 * 
	 * @param currentDateTime
	 * @return
	 * @throws NullArgumentException
	 */
	public boolean executeGameStep(DateTime currentDateTime) {
		boolean result = false;
		
		if( currentDateTime != null ){
			try{
				_lockObject.lock();
				if( !_company.hasAssignedProject() && _projects.size()>0){
					Project project=_projects.poll();
					if( project!=null ){
						_company.assignProject(project, currentDateTime);
						_runnedProjects.add(project);
					}
				}else if(_projects.size()==0 && !_company.hasAssignedProject() && !_gameFinished){
					for(IGameModelListener listener : _listeners){
						listener.gameFinished(this, _company.getMemento());
					}
					
					_gameFinished=true;
				}else{
					_company.executeWorkingStep(currentDateTime,_marketPlace, currentDateTime);
				}
				
				result = true;
			}finally{
				_lockObject.unlock();
			}	
		}

		return result;
	}
	
	/**
	 * 
	 */
	public void removeListeners(){
		try{
			_lockObject.lock();
			_listeners.clear();
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * 
	 */
	public void removeAllListeners(){
		try{
			_lockObject.lock();
			_listeners.clear();
			_marketPlace.removeAllListeners();
			_company.removeAllListeners();
		}finally{
			_lockObject.unlock();
		}
	}
}
