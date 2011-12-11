/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
public class TaskSchedulerJPanel extends JPanel implements ICompanyListener ,IDepartmentListener{

	/**
	 * 
	 */
	private static final ILogger CONST_LOGGER = LoggerFactory.getInstance(TaskSchedulerJPanel.class);
	
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
	private GanttScheduler _scheduler;
	
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
	private ScheduledTasksJPanel _tasksPanel;
	
	/**
	 * 
	 * @param game
	 * @param app
	 * @throws GuiException
	 */
	public TaskSchedulerJPanel( IGame game, ISchedulerApplication app, JPanel prevPanel)throws GuiException{
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
		schedulerPanel.add(new ScheduledTasksJPanel(_game), BorderLayout.WEST);

		
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
					tasksList.add(memento);
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
		_taskNameField.setFont(new Font("Arial", 0, 25));
		taskNamePanel.add(_taskNameField, BorderLayout.CENTER);
		
		_durationPanel = new DurationJPanel(game);
		bottomPanel.add(taskNamePanel, BorderLayout.CENTER);
		bottomPanel.add(_durationPanel, BorderLayout.EAST);
		bottomPanel.add(controlPanel, BorderLayout.SOUTH);
		add(bottomPanel, BorderLayout.SOUTH);
		
		// Setup scheduled tasks
		_tasksPanel = new ScheduledTasksJPanel(game);
		schedulerPanel.add(_tasksPanel, BorderLayout.WEST);
		_tasksPanel.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
		
		JPanel ganttPanel = new JPanel();
		ganttPanel.setLayout(new BorderLayout());

		_scheduler = new GanttScheduler();
		schedulerPanel.add(_scheduler, BorderLayout.CENTER);
		
		_game.addDepartmentListener(this);
		_game.addCompanyListener(this);
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_application.setPanel(_prevPanel);
			}
		});
		
		CONST_LOGGER.info("Task assigned");
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
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
					}finally{
						_lockObject.unlock();
					}
					
				}
			});
		}
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					_scheduler.clearScheduler();
				}finally{
					_lockObject.unlock();
				}
			}
		});
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,ProjectMemento assignedProject, DateTime dateTime) {
		projectFinished(owner, company, assignedProject, dateTime);
	}
	
	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company, final ProjectMemento assignedProject, final DateTime dateTime) {}
	@Override
	public void companyAssigned(String owner, CompanyMemento company) {}
	@Override
	public void departmentAssigned(String director, DepartmentMemento department) {}
	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {}
}
