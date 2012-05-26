/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

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

import org.jdesktop.swingx.JXPanel;
import org.joda.time.DateTime;
import org.promasi.desktop_swing.IDesktop;
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
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 * Represent the menu which is needed in order to 
 * create a new tasks to the Gantt scheduler.
 */
public class NewTaskJPanel extends JPanel implements ICompanyListener ,IDepartmentListener{

	/**
	 * 
	 */
	private static final ILogger CONST_LOGGER = LoggerFactory.getInstance(NewTaskJPanel.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of the {@link IGame} interface implementation
	 * represents the running game.
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
	private JComboBox<ProjectTask> _projectTasks;
	
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
	 * Constructor will initialize the object. 
	 * While initialization the object will be registered
	 * as a company and department listener in the given instance
	 * of {@link IGame} interface implementation which represents the running game.
	 * @param game Instance of {@link IGame} interface implementation.
	 * @param app instance of {@link ISchedulerApplication} interface implementation
	 * which represents the scheduler application in ProMaSi system.
	 * @param prevPanel instance of the preview panel needed in order to exchange
	 * the frame of the scheduler when the scheduling is canceled.
	 * @param desktop instance of the {@link IDesktop} interface implementation
	 * needed in order to interact with the ProMaSi's desktop.
	 * @throws GuiException in case of invalid arguments.
	 */
	public NewTaskJPanel( IGame game, ISchedulerApplication app, JPanel prevPanel, final IDesktop desktop)throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( app == null ){
			throw new GuiException("Wrong argument app == null");
		}
		
		if( prevPanel == null ){
			throw new GuiException("Wrong argument prevPanel == null");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
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
		JXPanel prjTasksPanel = new JXPanel();
		prjTasksPanel.setBackground(Colors.LightBlue.alpha(1f));
		_projectTasks = new JComboBox<ProjectTask>();
		_projectTasks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_taskNameField.setBorder(BorderFactory.createTitledBorder(_projectTasks.getSelectedItem().toString()));
				_taskNameField.setText("#"+_projectTasks.getSelectedItem().toString());
			}
		});
		
		prjTasksPanel.setLayout(new BorderLayout());
		prjTasksPanel.add(_projectTasks, BorderLayout.CENTER);
		schedulerPanel.add(prjTasksPanel, BorderLayout.NORTH);
		prjTasksPanel.setBorder(BorderFactory.createTitledBorder("Project tasks"));
		
		add( prjTasksPanel, BorderLayout.NORTH);
		add( tabbedPane, BorderLayout.CENTER);
		
		//Setup control panel
		JXPanel bottomPanel = new JXPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBackground(Colors.LightBlue.alpha(1f));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(Colors.LightBlue.alpha(1f));
		controlPanel.setLayout(new BorderLayout());
		JButton createTaskButton = new JButton("Add Task");
		controlPanel.add(createTaskButton, BorderLayout.EAST);
		createTaskButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String taskName = _taskNameField.getText();
				if(!_scheduler.hasRunningTask(taskName)){
					ProjectTask prjTask = (ProjectTask) _projectTasks.getSelectedItem();
					EmployeeTaskMemento memento = new EmployeeTaskMemento();
					memento.setFirstStep(_durationPanel.getFirstStep());
					memento.setLastStep(_durationPanel.getLastStep());
					memento.setDependencies(_tasksPanel.getSelectedDependencies());
					memento.setProjectTaskName(prjTask.toString());
					memento.setTaskName(taskName);
					
					final Map<String, List<EmployeeTaskMemento> > tasks = new TreeMap<String, List<EmployeeTaskMemento> >();
					Map<String, EmployeeMemento> selectedEmployees = _employeesPanel.getSelectedEmployees();
					if( !selectedEmployees.isEmpty() ){
						for( Map.Entry<String, EmployeeMemento> entry : selectedEmployees.entrySet()){
							List<EmployeeTaskMemento> tasksList = new LinkedList<EmployeeTaskMemento>();
							tasksList.add(memento);
							tasks.put(entry.getKey(), tasksList);
						}
						
						_game.assignTasks(tasks);
					}else{
						desktop.showMessageBox("Please select employees first", "Warning", 1 , null);
					}
				}else{
					desktop.showMessageBox("A task with a name '" + taskName +"'\n is already running", "Warning", 1 , null);
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
		
		JXPanel taskNamePanel = new JXPanel();
		taskNamePanel.setBackground(Colors.LightBlue.alpha(1f));
		
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
		
		_tasksPanel = new DependenciesJPanel(game);
		_tasksPanel.setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height));
		
		schedulerPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Dependencies", _tasksPanel);
		
		JPanel ganttPanel = new JPanel();
		ganttPanel.setLayout(new BorderLayout());

		_scheduler = new GanttJPanel(game);
		_scheduler.setBackground(Colors.White.alpha(0f));
		_scheduler.setOpaque(false);
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
	public void onExecuteWorkingStep(String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime) {}
	@Override
	public void companyAssigned(String owner, CompanyMemento company) {}
	@Override
	public void departmentAssigned(String director, DepartmentMemento department, DateTime dateTime) {}
	@Override
	public void employeeDischarged(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {}
	@Override
	public void employeeHired(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {}
}
