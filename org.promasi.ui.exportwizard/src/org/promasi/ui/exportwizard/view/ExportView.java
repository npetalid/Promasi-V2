package org.promasi.ui.exportwizard.view;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.joda.time.DateTime;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.builder.model.XmlProject;
import org.promasi.coredesigner.model.parser.ModelParser;
import org.promasi.coredesigner.model.parser.VisualXmlParser;
import org.promasi.game.GameException;
import org.promasi.game.company.Company;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.Employee;
import org.promasi.game.company.MarketPlace;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectMemento;
import org.promasi.ui.exportwizard.editor.dialog.ProjectSettingsDialog;
import org.promasi.ui.exportwizard.editor.dialog.SelectCompanyDialog;
import org.promasi.ui.exportwizard.editor.dialog.SelectEmployeeDialog;
import org.promasi.ui.exportwizard.editor.dialog.model.ProjectSettings;
import org.promasi.ui.exportwizard.editor.dialog.table.model.ProjectTaskTableModel;
import org.promasi.ui.exportwizard.editor.shell.PromasiModelBuilder;
import org.promasi.ui.exportwizard.resources.Companies;
import org.promasi.ui.exportwizard.resources.CompanyModel;
import org.promasi.ui.exportwizard.resources.EmployeeModel;
import org.promasi.ui.exportwizard.resources.Employees;
import org.promasi.ui.exportwizard.resources.GameStories;
import org.promasi.ui.exportwizard.resources.GameStory;
import org.promasi.ui.exportwizard.resources.ImageUtilities;
import org.promasi.ui.exportwizard.resources.ResourceHandler;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.xml.sax.SAXException;


/**
 * 
 * @author antoxron
 *
 */

public class ExportView extends ViewPart implements MouseListener {

	
	
	
	public static final String ID = "org.promasi.ui.exportwizard.exportview";
	private Label _projectSettingsLabel;
	private Label _selectCompanyLabel;
	private Label _hireEmployeesLabel;
	private Label _exportProjectLabel;
	private Label _importProjectLabel;
	
	private ProjectSettings _settings = null;
	private CompanyModel _selectedCompany;
	
	private List<EmployeeModel> _selectedEmployees;
	private SdProject _sdProject;
	
	
	public ExportView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setBackgroundImage(  AbstractUIPlugin.imageDescriptorFromPlugin("org.promasi.ui.exportwizard","icons/background.jpg").createImage()  );
		parent.setLayout(null);
		
		
		FontData fontData = new FontData("Tahoma", 7, SWT.NORMAL);
		
		Font labelsFont = new Font(parent.getDisplay() , fontData);
		
		_projectSettingsLabel = new Label(parent, SWT.WRAP);

		_projectSettingsLabel.setFont(labelsFont);
		_projectSettingsLabel.setBackground(ImageUtilities.getColor(SWT.COLOR_WHITE));
		_projectSettingsLabel.setBounds(23, 65, 133, 16);
		_projectSettingsLabel.setText("Click here to configure project");
		_projectSettingsLabel.addMouseListener(this);
		
		_selectCompanyLabel = new Label(parent, SWT.NONE);
		_selectCompanyLabel.setFont(labelsFont);
		_selectCompanyLabel.setBackground(ImageUtilities.getColor(SWT.COLOR_WHITE));
		_selectCompanyLabel.setBounds(174, 153, 139, 13);
		_selectCompanyLabel.setText("Click here to select company");
		_selectCompanyLabel.addMouseListener(this);

		_hireEmployeesLabel = new Label(parent, SWT.NONE);
		_hireEmployeesLabel.setFont(labelsFont);
		_hireEmployeesLabel.setBackground(ImageUtilities.getColor(SWT.COLOR_WHITE));
		_hireEmployeesLabel.setBounds(334, 244, 139, 13);
		_hireEmployeesLabel.setText("Click here to hire employees");
		_hireEmployeesLabel.addMouseListener(this);

		_exportProjectLabel = new Label(parent, SWT.NONE);
		_exportProjectLabel.setFont(labelsFont);
		_exportProjectLabel.setBackground(ImageUtilities.getColor(SWT.COLOR_WHITE));
		_exportProjectLabel.setBounds(503, 340, 133, 13);
		_exportProjectLabel.setText("Click here to export the project");
		
		_importProjectLabel = new Label(parent, SWT.NONE);
		_importProjectLabel.setBackground(ImageUtilities.getColor(SWT.COLOR_WHITE));
		_importProjectLabel.setFont(labelsFont);
		_importProjectLabel.setBounds(343, 68, 139, 13);
		_importProjectLabel.setText("Click here to import the project");
		_importProjectLabel.addMouseListener(this);
		
		_exportProjectLabel.addMouseListener(this);

	}
	
	private SdProject getSdProject() {

		SdProject sdProject = null;
		
		
		FileDialog importDialog = new FileDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				SWT.MULTI | SWT.OPEN );
		importDialog.setFilterNames(new String[] { ""+ "pms" +" files(*."+"pms"+")" } );
		importDialog.setFilterExtensions(new String[] { "*."+"pms"+"" } );
        String results = importDialog.open();
        if ( results != null )  {
        	String path = importDialog.getFilterPath();
        	String filename = importDialog.getFileName();
   
        	
        	String fullPath = path + File.separatorChar + filename;
        	
        	XmlProject xmlProject = null;
        	VisualXmlParser parser = new VisualXmlParser( fullPath );
        	try {
				xmlProject = parser.parse();
			} 
        	catch ( IOException e2 ) {
				
				e2.printStackTrace();
			} 
        	catch ( SAXException e2 ) {
			
				e2.printStackTrace();
			}        	
        	
        	
    		if ( xmlProject != null ) {
    			ModelParser modelParser = new ModelParser();
    			modelParser.setProject( xmlProject );
    			sdProject = modelParser.getProject();
    			if ( sdProject != null ) {
    				
    			}
    		}
        }
        return sdProject;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	private boolean buildProjectSettings(String filePath , ProjectSettings projectSettings , SdProject sdProject) {
		
		boolean isModelExported = false;
		
		
		
		if (  (filePath != null) && (projectSettings != null) && (sdProject != null))  {
			
			PromasiModelBuilder builder = new PromasiModelBuilder(projectSettings);
			builder.setModel(sdProject);
			Project project = (Project) builder.getModel();
			
			ProjectMemento sProject=project.getMemento();
	        String xmlString=sProject.serialize();
	      
	        try {
		            PrintWriter out = new PrintWriter(new FileWriter(filePath));
		            out.print(xmlString);
		            out.close();
		            isModelExported = true;
	        
		 	}catch (IOException eh) {
				eh.printStackTrace();
			}
		}
	 	return isModelExported;
	}
	private boolean  buildCompany(String filePath ,  CompanyModel companyModel) {
		
		boolean isModelExported = false;
		
		
		if ( (filePath != null) && (companyModel != null)) {
	 		
	 		String companyName = companyModel.getCompanyName();
	 		String description = companyModel.getHtmlDescription();
	 		double budget = Double.valueOf( companyModel.getBudget() );
	 		double prestigePoints = Double.valueOf( companyModel.getPrestigePoints() );
	 		
	 		
	 		String[] startTime = companyModel.getStartTime().split(":");
			String[] endTime = companyModel.getEndTime().split(":");
			
			
			int hours = Integer.valueOf(startTime[0]);
			int minutes = Integer.valueOf(startTime[1]);
			int seconds = Integer.valueOf(startTime[2]);
			
			
			DateTime companyStartTime = new DateTime(2000,1,1,hours,minutes,seconds,0);
	 		
			hours = Integer.valueOf(endTime[0]);
			minutes = Integer.valueOf(endTime[1]);
			seconds = Integer.valueOf(endTime[2]);
			
			DateTime companyEndTime = new DateTime(2000,1,1,hours,minutes,seconds,0);
			
			
	 		Company company;
		 	 
		 	 
			try {
				company = new Company(companyName,description, companyStartTime.toLocalTime(), companyEndTime.toLocalTime() ,budget,prestigePoints);
				CompanyMemento sCompany=company.getMemento();
				
				PrintWriter out = null;
					out = new PrintWriter(new FileWriter(filePath));
					isModelExported = true;

		            out.print(sCompany.serialize());
		            out.close();
			} catch (GameException e1) {
				e1.printStackTrace();
			}
		    catch (IOException e2) {
		    	e2.printStackTrace();
		    }
			
	 	}
		return isModelExported;
	}
	
	
	private boolean buildMarketplace(String filePath , List<EmployeeModel> selectedEmployees ) {
		
		
		boolean isModelExported = false;
		
		if (  (filePath != null) && (selectedEmployees != null)   ) {
			
			Map<String, Employee> employees = new TreeMap<String, Employee>();
			
			for (EmployeeModel employeeModel :selectedEmployees) {
				
				String id = employeeModel.getid();
				String firstName = employeeModel.getName();
				String lastName = employeeModel.getLastName();
				String curriculumVitae = employeeModel.getHtmlCurriculumVitae();
				
				double salary = Double.valueOf( employeeModel.getSalary());
				
				double developerSkill = Double.valueOf(employeeModel.getDeveloperSkill());
				double testerSkill = Double.valueOf(employeeModel.getTesterSkill());
				double teamPlayerSkill = Double.valueOf(employeeModel.getTeamPlayerSkill());
				double systemKnowledgeSkill = Double.valueOf(employeeModel.getSystemKnowledgeSkill());
				
				Map<String, Double> employeeSkills=new TreeMap<String, Double>();
				employeeSkills.put("developer", developerSkill);
	            employeeSkills.put("tester", testerSkill);
	            employeeSkills.put("teamPlayer", teamPlayerSkill);
	            employeeSkills.put("systemKnowledge", systemKnowledgeSkill);
				
				
	            try {
					Employee employee = new Employee(firstName,lastName,id,curriculumVitae,salary,employeeSkills);
					
		            employees.put(employee.getEmployeeId(), employee);
					
		            MarketPlace marketPlace = null;
	
					marketPlace = new MarketPlace(employees);
			
		            String xmlString = marketPlace.getMemento().serialize();
		                
		            PrintWriter out = null;
				
					out = new PrintWriter(new FileWriter(filePath));
					isModelExported = true;
			
		            out.print(xmlString);
		            out.close();
				} catch (GameException e1) {
				
					e1.printStackTrace();
				}
				catch (IOException e1) {
				
					e1.printStackTrace();
				}
				catch (IllegalArgumentException e1) {
				
					e1.printStackTrace();
				} catch (NullArgumentException e2) {
				
					e2.printStackTrace();
				}
			}
			
			
		}
		return isModelExported;
	}
	private boolean buildGameInfo(String filePath , GameStory gameStory) {
		boolean isModelExported = false;

		
		
		if ((filePath != null) && (gameStory != null)) {
			
			
		      
			try {
				PrintWriter out = new PrintWriter(new FileWriter(filePath));
				out.print(gameStory.getDescription());
	            out.close();
	            isModelExported = true;
			} catch (IOException e) {
		
				e.printStackTrace();
			}
				
            
			
		}
		return isModelExported;
	}
	

	@Override
	public void mouseDown(MouseEvent e) {
		Object source = e.getSource();
		
		if (source.equals(_exportProjectLabel)) {
		
			

			
			
			if (  (_sdProject != null) && (_settings != null) && (_selectedCompany != null) && (_selectedEmployees != null)   ) {
				

				FileDialog saveDialog = new FileDialog(getSite().getShell(), SWT.SAVE );
				
			
				saveDialog.setFileName( _sdProject.getName() );
				String filePath =  saveDialog.open();
				
				boolean isProjectExported = false;
				if ( filePath != null) {
					
					String SINGLE_PLAYER_FOLDER = "SinglePlayer";
					String TUTORIAL_FOLDER = "Tutorial";
					String PROJEECTS_FOLDER = "Projects";
					
					String topFolder = filePath + File.separator + SINGLE_PLAYER_FOLDER;
					
					if (new File(topFolder).mkdirs()) {
						
						String tutorialFolder = topFolder + File.separator + TUTORIAL_FOLDER;
						if (new File(tutorialFolder).mkdirs()) {
						
				
							String companyFile = tutorialFolder + File.separator + "Company";
							String marketPlace = tutorialFolder + File.separator + "MarketPlace";
							String gameStory = tutorialFolder + File.separator + "GameInfo";
						

						
							boolean isCompanyBuild = buildCompany(companyFile , _selectedCompany);
							boolean isMarketPlaceBuild = buildMarketplace(marketPlace , _selectedEmployees);
							boolean isGameStoryBuild = false;
						
						
							GameStories gameStories = ResourceHandler.getInstance().getGameStories();
							if (gameStories != null) {
								List<GameStory> gameStoriesList = gameStories.getGameStories();
								if (!gameStoriesList.isEmpty()) {
									isGameStoryBuild = buildGameInfo(gameStory , gameStoriesList.get(0));
								
								}
							}
						
							if ( (isCompanyBuild) && (isMarketPlaceBuild) && (isGameStoryBuild)  ) {
								String projectsFolder = tutorialFolder + File.separator + PROJEECTS_FOLDER;
								if (new File(projectsFolder).mkdirs()) {
									String project = projectsFolder + File.separator + "project";
									isProjectExported = buildProjectSettings(project , _settings , _sdProject);
									System.out.println("okko");
								}
							}
						
						}
					}
				}
				if (isProjectExported) {
					MessageDialog.openInformation(getSite().getShell() , "Information",saveDialog.getFileName() + " is successfully exported ");
				}
				else {
					MessageDialog.openError(getSite().getShell(), "Error", "Your project contains error(s), please fix it and try again.");
				}	
			}
		}
		else if (source.equals(_hireEmployeesLabel)) {
			
			
			if (_selectedCompany != null) {
				Employees list = ResourceHandler.getInstance().getEmployees();
				
				if (list != null) {				
					
					SelectEmployeeDialog dialog = new SelectEmployeeDialog(list , _selectedEmployees);
					dialog.setBlockOnOpen(true);
					dialog.open();
					if (dialog.saveSettings()) {
						_selectedEmployees = dialog.getSelectedEmployees();
					}	
				}
			}
			
			

		}
		else if (source.equals(_projectSettingsLabel)) {
			
			
			
			
			if ( _sdProject != null ) {

				
				String projectName = _sdProject.getName();
				List<SdModel> sdModels = _sdProject.getModels();
				if ((!projectName.trim().isEmpty()) && (sdModels != null)) {
					
					
					List<ProjectTaskTableModel> tasks = new ArrayList<ProjectTaskTableModel>();
					
					
					if (_settings == null) {
						_settings = new ProjectSettings();
						List<String> variables = new ArrayList<String>();
						for (int i = 0;i < sdModels.size(); i++) {
							
							SdModel sdModel = sdModels.get(i);
							
							List<SdObject> sdObjects = sdModel.getChildrenArray();
							if (sdObjects != null) {
								variables = new ArrayList<String>();
								for (SdObject sdObject : sdObjects) {
									
									if (  (!(sdObject instanceof SdCalculate))    && (!(sdObject instanceof SdLookup))) {
										variables.add(sdObject.getName());
									}
									
								}
							}
							
							ProjectTaskTableModel taskModel = new ProjectTaskTableModel();
							taskModel.setTaskName(sdModel.getName());
							taskModel.setVariables(variables);
							tasks.add(taskModel);
							
						}
						_settings.setProjectName(projectName);
						_settings.setTasks(tasks);
					}
					ProjectSettingsDialog dialog = new ProjectSettingsDialog( _settings );
					dialog.setBlockOnOpen(true);
					dialog.open();
					if (dialog.saveSettings()) {
						_settings = dialog.getProjectSettings();
					}
					
				}	
			}
		}
		else if (source.equals(_selectCompanyLabel)) {
			
			if (_settings != null) {
				Companies companies = ResourceHandler.getInstance().getCompanies();
				
				if (companies != null) {
					SelectCompanyDialog dialog = new SelectCompanyDialog(companies , _selectedCompany);
					dialog.setBlockOnOpen(true);
					dialog.open();
					if (dialog.saveSettings()) {
						_selectedCompany = dialog.getSelectedCompany();
	
					}
					
				}
			}	
		}
		
		else if (source.equals(_importProjectLabel)) {
			
			_sdProject = getSdProject();
		}
		
		
	}

	@Override
	public void mouseUp(MouseEvent e) {
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
