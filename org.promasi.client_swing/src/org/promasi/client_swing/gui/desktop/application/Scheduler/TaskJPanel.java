/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.game.project.ProjectTaskMemento;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author alekstheod
 *
 */
public class TaskJPanel extends JPanel implements ICompanyListener ,IDepartmentListener{

	/**
	 * 
	 */
	private static final ILogger CONST_LOGGER = LoggerFactory.getInstance(TaskJPanel.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private ISchedulerApplication _application;
	
	/**
	 * 
	 */
	private JPanel _prevPanel;
	
	/**
	 * 
	 */
	private JComboBox _projectTasks;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private JTextField _taskNameField;
	
	/**
	 * 
	 */
	private GanttJPanel _scheduler;
	
	/**
	 * 
	 */
	private EmployeesJPanel _employeesPanel;
	
	/**
	 * 
	 */
	private DurationJPanel _durationPanel;
	
	/**
	 * 
	 */
	private DependenciesJPanel _tasksPanel;
	
	/**
	 * 
	 * @param game
	 * @param app
	 * @throws GuiException
	 */
	public TaskJPanel( IGame game, ISchedulerApplication app, JPanel prevPanel)throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( app == null ){
			throw new GuiException("Wrong argument app == null");
		}
		
		if( prevPanel == null ){
			throw new GuiException("Wrong argument prevPanel == null");
		}
		
		setLayout(new BorderLayout());

		_game = game;
		_application = app;
		_prevPanel = prevPanel;
		_lockObject = new ReentrantLock();
				
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		JPanel schedulerPanel = new JPanel();
		schedulerPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Scheduler", schedulerPanel);
		schedulerPanel.add(new DependenciesJPanel(_game), BorderLayout.WEST);

		
		// Setup employees list
		JPanel hrPanel = new JPanel();
		hrPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Human Resources", hrPanel);
		_employeesPanel = new EmployeesJPanel(game);
		JScrollPane scrollPane = new JScrollPane(_employeesPanel);
		hrPanel.add(scrollPane, BorderLayout.CENTER);
		
		
		JPanel taskDesignPanel = new JPanel();
		taskDesignPanel.setLayout(new BorderLayout());

		//Setup project tasks
		JPanel prjTasksPanel = new JPanel();
		_projectTasks = new JComboBox();
		_projectTasks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_taskNameField.setBorder(BorderFactory.createTitledBorder(_projectTasks.getSelectedItem().toString()));
			}
		});
		
		prjTasksPanel.setLayout(new BorderLayout());
		prjTasksPanel.add(_projectTasks, BorderLayout.CENTER);
		schedulerPanel.add(prjTasksPanel, BorderLayout.NORTH);
		prjTasksPanel.setBorder(BorderFactory.createTitledBorder("Project tasks"));
		
		add( tabbedPane, BorderLayout.CENTER);
		
		//Setup control panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		JButton createTaskButton = new JButton("Add Task");
		controlPanel.add(createTaskButton, BorderLayout.EAST);
		createTaskButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String taskName = _taskNameField.getText();
				ProjectTask prjTask = (ProjectTask) _projectTasks.getSelectedItem();
				EmployeeTaskMemento memento = new EmployeeTaskMemento();
				memento.setFirstStep(_durationPanel.getFirstStep());
				memento.setLastStep(_durationPanel.getLastStep());
				memento.setDependencies(_tasksPanel.getSelectedDependencies());
				memento.setProjectTaskName(prjTask.toString());
				memento.setTaskName(taskName);
				
				Map<String, List<EmployeeTaskMemento> > tasks = new TreeMap<String, List<EmployeeTaskMemento> >();
				Map<String, EmployeeMemento> selectedEmployees = _employeesPanel.getSelectedEmployees();
				for( Map.Entry<String, EmployeeMemento> entry : selectedEmployees.entrySet()){
					List<EmployeeTaskMemento> tasksList = new LinkedList<EmployeeTaskMemento>();
					tasksList.add(memento);
					tasks.put(entry.getKey(), tasksList);
					_game.assignTasks(tasks);
				}
			}
		});
		
		JButton backButton = new JButton("Back");
		controlPanel.add(backButton, BorderLayout.WEST);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_application.setPanel(_prevPanel);
			}
		});
		
		JPanel taskNamePanel = new JPanel();
		taskNamePanel.setBorder(BorderFactory.createTitledBorder("Task Name"));
		taskNamePanel.setLayout(new BorderLayout());
		_taskNameField = new JTextField();
		_taskNameField.setBorder(BorderFactory.createTitledBorder("Task"));
		taskNamePanel.add(_taskNameField, BorderLayout.SOUTH);
		
		_durationPanel = new DurationJPanel(game);
		bottomPanel.add(taskNamePanel, BorderLayout.CENTER);
		bottomPanel.add(_durationPanel, BorderLayout.EAST);
		bottomPanel.add(controlPanel, BorderLayout.SOUTH);
		add(bottomPanel, BorderLayout.SOUTH);
		
		// Setup scheduled tasks

		
		_tasksPanel = new DependenciesJPanel(game);
		_tasksPanel.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
		
		schedulerPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Dependencies", _tasksPanel);
		
		JPanel ganttPanel = new JPanel();
		ganttPanel.setLayout(new BorderLayout());

		_scheduler = new GanttJPanel(game);
		schedulerPanel.add(_scheduler, BorderLayout.CENTER);
		
		_game.addDepartmentListener(this);
		_game.addCompanyListener(this);
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department, DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_application.setPanel(_prevPanel);
			}
		});
		
		CONST_LOGGER.info("Task assigned");
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department, DateTime dateTime) {
		CONST_LOGGER.info("Task assign failed");
	}

	@Override
	public void projectAssigned(final String owner, final CompanyMemento company, final ProjectMemento project, final DateTime dateTime) {
		if( project != null ){
			SwingUtilities.invokeLater( new Runnable() {
				
				@Override
				public void run() {
					try{
						_lockObject.lock();
						_scheduler.projectAssigned(owner, company, project, dateTime);
						if( project.getProjectTasks() != null){
							for ( Map.Entry<String, ProjectTaskMemento> entry : project.getProjectTasks().entrySet() ){
								try {
									_projectTasks.addItem(new ProjectTask(entry.getValue()));
								} catch (GuiException e) {
									CONST_LOGGER.error("ProjectTask initialization error");
								}
							}
						}
						
						_taskNameField.setBorder(BorderFactory.createTitledBorder(_projectTasks.getSelectedItem().toString()));
					}finally{
						_lockObject.unlock();
					}
					
				}
			});
		}
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,ProjectMemento project, DateTime dateTime) {}
	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,ProjectMemento assignedProject, DateTime dateTime) {projectFinished(owner, company, assignedProject, dateTime);}
	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company, final ProjectMemento assignedProject, final DateTime dateTime) {}
	@Override
	public void companyAssigned(String owner, CompanyMemento company) {}
	@Override
	public void departmentAssigned(String director, DepartmentMemento department, DateTime dateTime) {}
	@Override
	public void employeeDischarged(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {}
	@Override
	public void employeeHired(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {}
}
