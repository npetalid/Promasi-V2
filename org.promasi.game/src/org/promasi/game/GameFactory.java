/**
 * 
 */
package org.promasi.game;

import org.promasi.game.company.Company;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.GameModelModel;
import org.promasi.game.model.MarketPlaceModel;
import org.promasi.game.model.ProjectModel;
import org.promasi.game.project.Project;

/**
 * @author alekstheod
 *
 */
public class GameFactory implements IGameFactory {

	/* (non-Javadoc)
	 * @see org.promasi.game.IGameFactory#createCompany(org.promasi.game.model.CompanyModel)
	 */
	@Override
	public Company createCompany(CompanyModel company) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGameFactory#createProject(org.promasi.game.model.ProjectModel)
	 */
	@Override
	public Project createProject(ProjectModel project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModel createGameModel(GameModelModel gameModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MarketPlace createMarketPlace(MarketPlaceModel marketPlace) {
		// TODO Auto-generated method stub
		return null;
	}

}
