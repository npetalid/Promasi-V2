/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.project.ProjectMemento;
import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Time;
import org.swiftgantt.model.GanttModel;
import org.swiftgantt.model.Task;
import org.swiftgantt.ui.TimeUnit;

/**
 * @author alekstheod
 *
 */
public class SchedulerJPanel extends JPanel implements ICompanyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**|
	 * 
	 */
	private GanttChart _ganttChart;
	
	/**
	 * 
	 */
	private GanttModel _ganttModel;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private DateTime _projectAssignDate;
	
	/**
	 * 
	 */
	private ISchedulerApplication _app;
	
	/**
	 * 
	 */
	private ProjectMemento _project;
	
	/**
	 * 
	 */
	private CompanyMemento _company;
	
	/**
	 * 
	 * @param appName
	 * @throws GuiException
	 * @throws IOException 
	 */
	public SchedulerJPanel( IGame game, ISchedulerApplication app ) throws GuiException, IOException {
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		if( app == null ){
			throw new GuiException("Wrong argument app == null");
		}
		
		_app = app;
		_game = game;
		_ganttChart = new GanttChart();
		_ganttChart.setPreferredSize(new Dimension(100,100));
		
		setLayout(new BorderLayout());
		_ganttChart.setVisible(true);
		_ganttChart.setBounds(0, 0, 100, 100);
		_ganttChart.setTimeUnit(TimeUnit.Week);

		Config config = _ganttChart.getConfig();
		config.setWorkingTimeBackColor(Color.YELLOW);//Set background color for working time.
		config.setTimeUnitWidth(50);//Set width for time unit
		config.setWorkingDaysSpanOfWeek(new int[]{Calendar.MONDAY, Calendar.THURSDAY});//Set span of working days in each week
		config.setAllowAccurateTaskBar(true);//Set true if you want to show accurate task bar.
		add(_ganttChart, BorderLayout.CENTER);
		_ganttChart.setVisible(true);
		_lockObject = new ReentrantLock();
		
		JPanel wizardPanel = new JPanel();
		wizardPanel.setLayout(new BorderLayout());
		JButton createButton = new JButton("New");
		wizardPanel.add( createButton, BorderLayout.EAST);
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					_app.setPanel(new TaskJPanel(_game, _app, _company, _project, SchedulerJPanel.this));
				} catch (GuiException e) {
					//TODO Log
				}
			}
		});
		
		add( wizardPanel, BorderLayout.SOUTH);
		_game.addCompanyListener(this);
	}
	
	@Override
	public void projectAssigned(final String owner,final CompanyMemento company,final ProjectMemento project,final DateTime dateTime) {
		if( project != null ){
			try {
				SwingUtilities.invokeAndWait( new Runnable() {
					
					@Override
					public void run() {
						try{
							_lockObject.lock();
							_ganttModel = new GanttModel();
							Time time = new Time(dateTime.plusHours(project.getProjectDuration()/10).toDate());
							_ganttModel.setDeadline(time);
							_projectAssignDate = dateTime;
							_ganttChart.setModel(_ganttModel);
							_project = project;
						}finally{
							_lockObject.unlock();
						}
					}
				});
			} catch (InterruptedException e) {
			} catch (InvocationTargetException e) {
			}
		}
	}

	private void ClearGanttChart(){
		_ganttChart.removeAll();
	}
	
	@Override
	public void projectFinished(String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime) {
		try {
			_lockObject.lock();
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					ClearGanttChart();
				}
			});
		} catch (InterruptedException e) {;
		} catch (InvocationTargetException e) {
		} finally {
			_lockObject.unlock();
		}
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime) {
		try {
			_lockObject.lock();
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					ClearGanttChart();
				}
			});
		} catch (InterruptedException e) {;
		} catch (InvocationTargetException e) {
		} finally {
			_lockObject.unlock();
		}
	}

	/**
	 * 
	 * @param projectTasks
	 * @param assignedProject
	 */
	private void drawGanttDiagramm( final Map<String, EmployeeTaskMemento> projectTasks, final ProjectMemento assignedProject ){
		try{
			_lockObject.lock();
			if( _ganttModel != null && _projectAssignDate != null ){
				_ganttModel.removeAll();
				
				_ganttModel.setDeadline(new Time(_projectAssignDate.plusHours(assignedProject.getProjectDuration()/10).toDate()));
				Task taskGroup = new Task(assignedProject.getName(), new Time(_projectAssignDate.toDate()), new Time(_projectAssignDate.plusHours(assignedProject.getProjectDuration()/10).toDate()));
				Map<String, Task> ganttTasks = new TreeMap<String, Task>();
				for (Map.Entry<String, EmployeeTaskMemento> entry : projectTasks.entrySet() ){
					EmployeeTaskMemento employeeTask = entry.getValue();
					Time startTime = new Time(_projectAssignDate.plusHours( employeeTask.getFirstStep()).toDate());
					Time endTime = new Time(_projectAssignDate.plusHours( employeeTask.getLastStep()).toDate());
					Task newTask = new Task(employeeTask.getTaskName(), startTime, endTime );
					ganttTasks.put(employeeTask.getTaskName(), newTask);
					taskGroup.add(newTask);
				}
				
				
				for (Map.Entry<String, EmployeeTaskMemento> entry : projectTasks.entrySet() ){
					EmployeeTaskMemento employeeTask = entry.getValue();
					if(  employeeTask.getDependencies() != null ){
						for( String taskName : employeeTask.getDependencies() ){
							if( ganttTasks.containsKey(taskName) && ganttTasks.containsKey(employeeTask.getTaskName() ) ){
								ganttTasks.get(employeeTask.getTaskName()).addPredecessor(ganttTasks.get(taskName));
							}
						}
					}
				}
				
				if( !taskGroup.isLeaf() ){
					_ganttModel.addTask(taskGroup);
				}
				
				_ganttChart.setModel(_ganttModel);
			}
		}finally{
			_lockObject.unlock();
		}
	}
	
	@Override
	public void onExecuteWorkingStep(final String owner, final CompanyMemento company, final ProjectMemento assignedProject, final DateTime dateTime) {
		try{
			_lockObject.lock();
			final Map<String, EmployeeTaskMemento> projectTasks = new TreeMap<String, EmployeeTaskMemento>();
			if( company != null && company.getITDepartment() != null && company.getITDepartment().getEmployees() != null ){
				Map<String, EmployeeMemento> employees = company.getITDepartment().getEmployees();
				for (Map.Entry<String, EmployeeMemento> entry : employees.entrySet()){
					Map<Integer, EmployeeTaskMemento> tasks = entry.getValue().getTasks();
					for (Map.Entry<Integer, EmployeeTaskMemento> taskEntry : tasks.entrySet() ){
						if( !projectTasks.containsKey(taskEntry.getValue().getTaskName())){
							projectTasks.put(taskEntry.getValue().getTaskName(), taskEntry.getValue() );
						}
					}
				}
			}

			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					drawGanttDiagramm(projectTasks, assignedProject);
				}
			});
			
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		try{
			_lockObject.lock();
			_company = company;
		}finally{
			_lockObject.unlock();
		}
	}
}
