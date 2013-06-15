/**
 * 
 */
package org.promasi.game.multiplayer;

import java.util.Queue;

import org.promasi.game.GameException;
import org.promasi.game.GameModel;
import org.promasi.game.IGameFactory;
import org.promasi.game.company.Company;
import org.promasi.game.company.Employee;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.EmployeeModel;
import org.promasi.game.model.GameModelModel;
import org.promasi.game.model.MarketPlaceModel;
import org.promasi.game.model.ProjectModel;
import org.promasi.game.model.ProjectTaskModel;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectTask;

/**
 * @author alekstheod
 *
 */
public class MultiPlayerGameFactory implements IGameFactory {

	private IGameFactory _baseFactory;
	
	/**
	 * Game model prototype. Will be used in order to create a
	 * new {@link = GameModel} instance for a new player.
	 */
	private GameModelModel _gameModel;
	
	/**
	 * A shared market place.
	 */
	private MarketPlace _marketPlace;
	
	public MultiPlayerGameFactory(IGameFactory baseFactory, String clientId, String gameName, String gameDescription, MarketPlace marketPlace, Company company, Queue<Project> projects) throws GameException{
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
		
		if(baseFactory==null){
			throw new GameException("Wrong argument baseFactory==null");
		}
		
		_baseFactory=baseFactory;
		GameModel gameModel=new GameModel(gameName,gameDescription,marketPlace,company,projects);
		_gameModel=gameModel.getMemento();
		_marketPlace = marketPlace;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.game.IGameFactory#createCompany(org.promasi.game.model.CompanyModel)
	 */
	@Override
	public Company createCompany(CompanyModel company) {
		return _baseFactory.createCompany(_gameModel.getCompanyModel());
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGameFactory#createProject(org.promasi.game.model.ProjectModel)
	 */
	@Override
	public Project createProject(ProjectModel project) {
		return _baseFactory.createProject(project);
	}

	@Override
	public GameModel createGameModel(GameModelModel gameModel) {
		return _baseFactory.createGameModel(_gameModel);
	}

	@Override
	public MarketPlace createMarketPlace(MarketPlaceModel marketPlace) {
		return _marketPlace;
	}

	@Override
	public ProjectTask createProjectTask(ProjectTaskModel projectTask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee createEmployee(EmployeeModel employee) {
		// TODO Auto-generated method stub
		return null;
	}

}
