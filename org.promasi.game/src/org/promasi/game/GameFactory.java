/**
 * 
 */
package org.promasi.game;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import org.joda.time.LocalTime;
import org.promasi.game.company.Company;
import org.promasi.game.company.Employee;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.EmployeeModel;
import org.promasi.game.model.EquationProgressModel;
import org.promasi.game.model.GameModelModel;
import org.promasi.game.model.MarketPlaceModel;
import org.promasi.game.model.ProjectModel;
import org.promasi.game.model.ProjectTaskModel;
import org.promasi.game.model.SdSystemBasedSimulatorModel;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectTask;
import org.promasi.game.project.SdSystemBasedSimulator;
import org.promasi.sdsystem.SdSystem;
import org.promasi.sdsystem.SdSystemFactory;
import org.promasi.sdsystem.model.generated.SdSystemModel;
import org.promasi.utilities.equation.EquationModel;
import org.promasi.utilities.equation.IEquation;

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
		Company result = null;
		
		try {
			result = new Company(company.getName(), company.getDescription(), new LocalTime(company.getStartTime()), new LocalTime(company.getEndTime()), company.getBudget(), company.getPrestigePoints());
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see org.promasi.game.IGameFactory#createProject(org.promasi.game.model.ProjectModel)
	 */
	@Override
	public Project createProject(ProjectModel project) {
		Project result = null;
		
		try {
			Map<String, ProjectTask> tasks = new TreeMap<>();
			for( ProjectModel.ProjectTasks.Entry entry : project.getProjectTasks().getEntry() )
			{
				tasks.put(entry.getKey(), createProjectTask(entry.getValue()));
			}
			
			result = new Project(project.getName(), project.getDescription(), project.getProjectDuration(), tasks, project.getProjectPrice(), project.getDifficultyLevel() );
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public GameModel createGameModel(GameModelModel gameModel) {
		GameModel result = null;
		
		try {
			Queue<Project> projects = new LinkedList<>();
			for( ProjectModel projectModel : gameModel.getProjectModel())
			{
				projects.add(createProject(projectModel));
			}
			
			result = new GameModel(gameModel.getGameName(), gameModel.getGameDescription(), createMarketPlace(gameModel.getMarketPlaceModel()), createCompany(gameModel.getCompanyModel()), projects);
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public MarketPlace createMarketPlace(MarketPlaceModel marketPlace) {
		MarketPlace result = null;
		
		try {
			Map<String, Employee> employees = new TreeMap<>();
			for( MarketPlaceModel.AvailableEmployees.Entry entry : marketPlace.getAvailableEmployees().getEntry() )
			{
				employees.put(entry.getKey(), createEmployee(entry.getValue()));
			}
			
			result = new MarketPlace(employees);
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public ProjectTask createProjectTask(ProjectTaskModel projectTask) {
		ProjectTask result = null;
		
		try {
			SdSystemFactory sdSystemFactory = new SdSystemFactory();
			SdSystemModel sdSystemModel = ((SdSystemBasedSimulatorModel)projectTask.getSimulationModel()).getSdSystemModel();
			
			SdSystem sdSystem = (SdSystem)sdSystemFactory.createSdSystem(sdSystemModel);
			EquationModel eqModel = ( (EquationProgressModel)projectTask.getProgressEquation() ).getEquationModel();
			IEquation progressEquation = sdSystemFactory.createEquation(eqModel);
			result = new ProjectTask(projectTask.getName(), projectTask.getDescription(), new SdSystemBasedSimulator( sdSystem), progressEquation );
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Employee createEmployee(EmployeeModel employee) {
		Employee result = null;
		
		try {
			Map<String, Double> skills = new TreeMap<>();
			for( EmployeeModel.EmployeeSkills.Entry entry : employee.getEmployeeSkills().getEntry() )
			{
				skills.put(entry.getKey(), entry.getValue());
			}
			
			result = new Employee(employee.getFirstName(), employee.getLastName(), employee.getEmployeeId(), employee.getCurriculumVitae(), employee.getSalary(), skills);
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
