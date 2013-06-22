/**
 * 
 */
package org.promasi.game;

import org.promasi.game.company.Company;
import org.promasi.game.company.Employee;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.GameModelModel;
import org.promasi.game.model.generated.MarketPlaceModel;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.game.model.generated.ProjectTaskModel;
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
