/**
 * 
 */
package org.promasi.game;

import java.util.LinkedList;
import java.util.Queue;

import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.MarketPlaceMemento;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class GameModelMemento 
{
	/**
	 * 
	 */
	private String _gameName;
	
	/**
	 * 
	 */
	private String _gameDescription;
	
	/**
	 * 
	 */
	private MarketPlaceMemento _marketPlace;
	
	/**
	 * 
	 */
	private CompanyMemento _company;
	
	/**
	 * 
	 */
	private Queue<ProjectMemento> _projects;
	
	/**
	 * 
	 */
	private Queue<ProjectMemento> _runnedProjects;

	/**
	 * 
	 */
	public GameModelMemento(){
		
	}
	
	/**
	 * 
	 * @param gameModel
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	protected GameModelMemento(GameModel gameModel)throws NullArgumentException, SerializationException{
		if(gameModel==null){
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		_gameName=gameModel._gameName;
		_gameDescription=gameModel._gameDescription;
		_marketPlace=gameModel._marketPlace.getMemento();
		_company=gameModel._company.getMemento();
		_projects=new LinkedList<ProjectMemento>();
		_runnedProjects=new LinkedList<ProjectMemento>();
		for(Project project : gameModel._projects){
			_projects.add(project.getMemento());
		}
		
		for(Project project : gameModel._runnedProjects){
			_runnedProjects.add(project.getMemento());
		}
	}

	/**
	 * 
	 * @return
	 * @throws SerializationException
	 */
	public GameModel getGameModel()throws SerializationException{
		if(_gameName==null){
			throw new SerializationException("Serialization failed because _gameName property is null");
		}
		
		if(_gameDescription==null){
			throw new SerializationException("Serialization failed because _gameDescription property is null");
		}
		
		if(_marketPlace==null){
			throw new SerializationException("Serialization failed because _marketPlace property is null");
		}
		
		if(_company==null){
			throw new SerializationException("Serialization failed because _company property is null");
		}
		
		if(_projects==null){
			throw new SerializationException("Serialization failed because _projects property is null");
		}
		
		if(_projects.isEmpty()){
			throw new SerializationException("Serialization failed because _projects property is empty");
		}
		
		Queue<Project> projects=new LinkedList<Project>();
		for(ProjectMemento project : _projects){
			projects.add(project.getProject());
		}
		
		try {
			return new GameModel(_gameName, _gameDescription, _marketPlace.getMarketPlace(), _company.getCompany(), projects);
		} catch (IllegalArgumentException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		} catch (GameException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		}
	}
	
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Queue<ProjectMemento> projects) {
		_projects = projects;
	}

	/**
	 * @return the projects
	 */
	public Queue<ProjectMemento> getProjects() {
		return _projects;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyMemento company) {
		_company = company;
	}

	/**
	 * @return the company
	 */
	public CompanyMemento getCompany() {
		return _company;
	}

	/**
	 * @param marketPlace the marketPlace to set
	 */
	public void setMarketPlace(MarketPlaceMemento marketPlace) {
		_marketPlace = marketPlace;
	}

	/**
	 * @return the marketPlace
	 */
	public MarketPlaceMemento getMarketPlace() {
		return _marketPlace;
	}

	/**
	 * @param gameDescription the gameDescription to set
	 */
	public void setGameDescription(String gameDescription) {
		_gameDescription = gameDescription;
	}

	/**
	 * @return the gameDescription
	 */
	public String getGameDescription() {
		return _gameDescription;
	}

	/**
	 * @param gameName the gameName to set
	 */
	public void setGameName(String gameName) {
		_gameName = gameName;
	}

	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return _gameName;
	}

	/**
	 * @param _runnedProjects the _runnedProjects to set
	 */
	public void setRunnedProjects(Queue<ProjectMemento> _runnedProjects) {
		this._runnedProjects = _runnedProjects;
	}

	/**
	 * @return the _runnedProjects
	 */
	public Queue<ProjectMemento> getRunnedProjects() {
		return _runnedProjects;
	}
}
