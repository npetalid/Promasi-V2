/**
 * 
 */
package org.promasi.game;

import java.util.LinkedList;
import java.util.Queue;

import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.project.Project;
import org.promasi.game.project.SerializableProject;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class SerializableGameModel 
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
	private SerializableMarketPlace _marketPlace;
	
	/**
	 * 
	 */
	private SerializableCompany _company;
	
	/**
	 * 
	 */
	private Queue<SerializableProject> _projects;
	
	/**
	 * 
	 */
	private Queue<SerializableProject> _runnedProjects;

	/**
	 * 
	 */
	public SerializableGameModel(){
		
	}
	
	/**
	 * 
	 * @param gameModel
	 * @throws NullArgumentException
	 * @throws SerializationException
	 */
	protected SerializableGameModel(GameModel gameModel)throws NullArgumentException, SerializationException{
		if(gameModel==null){
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		_gameName=gameModel._gameName;
		_gameDescription=gameModel._gameDescription;
		_marketPlace=gameModel._marketPlace.getSerializableMarketPlace();
		_company=gameModel._company.getSerializableCompany();
		_projects=new LinkedList<SerializableProject>();
		_runnedProjects=new LinkedList<SerializableProject>();
		for(Project project : gameModel._projects){
			_projects.add(project.getSerializableProject());
		}
		
		for(Project project : gameModel._runnedProjects){
			_runnedProjects.add(project.getSerializableProject());
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
		for(SerializableProject project : _projects){
			projects.add(project.getProject());
		}
		
		try {
			return new GameModel(_gameName, _gameDescription, _marketPlace.getMarketPlace(), _company.getCompany(), projects);
		} catch (IllegalArgumentException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		} catch (NullArgumentException e) {
			throw new SerializationException("Serialization failed because " + e.getMessage());
		}
	}
	
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(Queue<SerializableProject> projects) {
		_projects = projects;
	}

	/**
	 * @return the projects
	 */
	public Queue<SerializableProject> getProjects() {
		return _projects;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(SerializableCompany company) {
		_company = company;
	}

	/**
	 * @return the company
	 */
	public SerializableCompany getCompany() {
		return _company;
	}

	/**
	 * @param marketPlace the marketPlace to set
	 */
	public void setMarketPlace(SerializableMarketPlace marketPlace) {
		_marketPlace = marketPlace;
	}

	/**
	 * @return the marketPlace
	 */
	public SerializableMarketPlace getMarketPlace() {
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
	public void setRunnedProjects(Queue<SerializableProject> _runnedProjects) {
		this._runnedProjects = _runnedProjects;
	}

	/**
	 * @return the _runnedProjects
	 */
	public Queue<SerializableProject> getRunnedProjects() {
		return _runnedProjects;
	}
}
