/**
 * 
 */
package org.promasi.game;

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
public interface IGameFactory {
	Company createCompany( CompanyModel company );
	Project createProject( ProjectModel project );
	GameModel createGameModel( GameModelModel gameModel );
	MarketPlace createMarketPlace( MarketPlaceModel marketPlace );
	ProjectTask createProjectTask( ProjectTaskModel projectTask );
	Employee createEmployee( EmployeeModel employee );
}
