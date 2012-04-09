/**
 * 
 */
package org.promasi.game;

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
import org.promasi.utilities.design.Observer;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 * Represent the running game on the ProMaSi system.
 * A GameModel uses all the required components
 * such as instance of {@link MarketPlace}, {@link Company} a
 * queue of {@link Project}s in order to run the ProMaSi's game.
 */
public class GameModel extends Observer<IGameModelListener>
{
	/**
	 * The game's name.
	 */
	protected String _gameName;
	
	/**
	 * Game's description string.
	 */
	protected String _gameDescription;
	
	/**
	 * Instance of {@link MarketPlace} needed
	 * in order to simulate the marketplace.
	 */
	protected MarketPlace _marketPlace;
	
	/**
	 * Instance of {@link Company} needed in
	 * order to simulate the company in ProMaSi sysstem.
	 */
	protected Company _company;
	
	/**
	 * A lock object needed in order to 
	 * synchronize between the system's clock
	 * and command threads.
	 */
	private Lock _lockObject;
	
	/**
	 * A queue of {@link Project} which will
	 * be executed by the company {@link Company}.
	 */
	protected Queue<Project> _projects;
	
	/**
	 * 
	 */
	private boolean _gameFinished;
	
	/**
	 * Constructor will initialize the object.
	 * @param gameName the games's name.
	 * @param gameDescription a game's description.
	 * @param marketPlace instance of {@link MarketPlace}
	 * needed in order to handle the employees.
	 * @param company instance of {@link Company} needed in 
	 * order to simulate the company's work.
	 * @param projects queue of {@link Project}s which
	 * will be executed by the company.
	 * @throws GameException in case of invalid arguments.
	 */
	public GameModel(	final String gameName, 
						final String gameDescription, 
						final MarketPlace marketPlace, 
						final Company company,
						final Queue<Project> projects)throws GameException{
		
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
		_gameName=gameName;
		_gameDescription=gameDescription;
		_gameFinished=false;
		_lockObject = new ReentrantLock();
	}
	
	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public GameModelMemento getMemento(){
		try {
			_lockObject.lock();
			return new GameModelMemento(this);
		} finally {
			_lockObject.unlock();
		}
	}

	public boolean hireEmployee(String employeeId, DateTime dateTime) {
		return _marketPlace.hireEmployee(_company, employeeId, dateTime);
	}

	public boolean dischargeEmployee(String employeeId, DateTime dateTime) {
		return _company.dischargeEmployee(employeeId, _marketPlace, dateTime);
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
	public boolean assignTasks(String employeeId,List<EmployeeTaskMemento> employeeTasks, DateTime dateTime) {
		try {
			return _company.assignTasks(employeeId, employeeTasks, dateTime);
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
					}
				}else if(_projects.size()==0 && !_company.hasAssignedProject() && !_gameFinished){
					for(IGameModelListener listener : getListeners()){
						listener.gameFinished(this, _company.getMemento());
					}
					
					_gameFinished=true;
				}else{
					_company.executeWorkingStep(currentDateTime,_marketPlace, currentDateTime);
				}
				
				for(IGameModelListener listener : getListeners()){
					listener.onExecuteStep(this, _company.getMemento());
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
	public void removeAllListeners(){
		try{
			_lockObject.lock();
			clearListeners();
			_marketPlace.clearListeners();
			_company.clearListeners();
		}finally{
			_lockObject.unlock();
		}
	}
}
