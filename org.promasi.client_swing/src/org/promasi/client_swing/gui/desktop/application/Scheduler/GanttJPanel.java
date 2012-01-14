package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
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
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Time;
import org.swiftgantt.model.GanttModel;
import org.swiftgantt.model.Task;
import org.swiftgantt.ui.TimeUnit;

public class GanttJPanel extends JPanel  implements ICompanyListener, IDepartmentListener{

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
	private DateTime _projectAssignDate;
	
	/**
	 * 
	 */
	private boolean _needToUpdateDiagramm;
	
	/**
	 * 
	 */
	public static final int CONST_DURATION_MULTIPLIER = 10;
	
	/**
	 * @throws GuiException 
	 * 
	 */
	public GanttJPanel( IGame game ) throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game");
		}
		
		_ganttChart = new GanttChart();
		_ganttChart.setPreferredSize(new Dimension(100,100));
		
		setLayout(new BorderLayout());
		_ganttChart.setVisible(true);
		_ganttChart.setBounds(0, 0, 100, 100);
		_ganttChart.setTimeUnit(TimeUnit.Day);
		_ganttChart.setAutoscrolls(true);
		
		_ganttChart.setShowTreeView(false);
		
		_ganttChart.getConfig().setTimeUnitWidth(30);
		_ganttChart.getConfig().setWorkingDaysSpanOfWeek(new int[]{Calendar.SUNDAY, Calendar.SATURDAY});
		_ganttChart.getConfig().setAllowAccurateTaskBar(true);
		_ganttChart.getConfig().setTaskTreeViewBackColor(Color.WHITE);
		_ganttChart.getConfig().setGanttChartBackColor(new Color(0x1B,0x88, 0xE0));
		_ganttChart.getConfig().setWorkingTimeBackColor(Color.WHITE);
		_ganttChart.getConfig().setRestoutTimeBackColor(new Color(177,201, 237));
		_ganttChart.getConfig().setFillInvalidArea(true);
		
		add(_ganttChart, BorderLayout.CENTER);
		_ganttModel = new GanttModel();
		_ganttModel.getTaskTreeModel().setAsksAllowsChildren(true);
		
		_ganttChart.setModel(_ganttModel);


		_lockObject = new ReentrantLock();
		setBorder(BorderFactory.createTitledBorder("Scheduler"));
		_needToUpdateDiagramm= true;
		game.addCompanyListener(this);
		game.addDepartmentListener(this);
	}

	/**
	 * 
	 * @param scheduledTasks
	 * @param assignedProject
	 */
	private void drawGanttDiagramm( Map<String, EmployeeTaskMemento> scheduledTasks, ProjectMemento assignedProject, DateTime dateTime ){
		try{
			_lockObject.lock();
			Vector<Task> tasks = new Vector<Task>();
			if(_projectAssignDate != null ){
				Map<String, Task> ganttTasks = new TreeMap<String, Task>();
				for (Map.Entry<String, EmployeeTaskMemento> entry : scheduledTasks.entrySet() ){
					EmployeeTaskMemento employeeTask = entry.getValue();
					Time startTime = new Time(_projectAssignDate.plusHours( employeeTask.getFirstStep() ).toDate());
					Time endTime = new Time(_projectAssignDate.plusHours( employeeTask.getLastStep()).toDate());
					Task newTask = new Task(employeeTask.getTaskName(), startTime, endTime, 0 );
					newTask.setAllowsChildren(false);
					newTask.setId(employeeTask.getFirstStep());
					ganttTasks.put(employeeTask.getTaskName(), newTask);
					tasks.add(newTask);
				}
				
				
				for (Map.Entry<String, EmployeeTaskMemento> entry : scheduledTasks.entrySet() ){
					EmployeeTaskMemento employeeTask = entry.getValue();
					if(  employeeTask.getDependencies() != null ){
						for( String taskName : employeeTask.getDependencies() ){
							if( ganttTasks.containsKey(taskName) && ganttTasks.containsKey(employeeTask.getTaskName() ) ){
								ganttTasks.get(employeeTask.getTaskName()).addPredecessor(ganttTasks.get(taskName));
							}
						}
					}
				}
				
				_ganttModel.removeAll();
				Task[] taskArray = new Task[tasks.size()];
				tasks.toArray(taskArray);
				_ganttModel.setKickoffTime( new Time(dateTime.toDate()));
				_ganttModel.addTask(taskArray);
				_ganttModel.setDeadline(new Time(_projectAssignDate.plusHours(assignedProject.getProjectDuration()/CONST_DURATION_MULTIPLIER).toDate()));
			}
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}
	
	/**
	 * 
	 * @param owner
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	@Override
	public void projectAssigned(String owner, CompanyMemento company,final ProjectMemento project, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					Time time = new Time(dateTime.plusHours(project.getProjectDuration()/CONST_DURATION_MULTIPLIER).toDate());
					_ganttModel.removeAll();
					_ganttModel.setDeadline(time);
					_projectAssignDate = dateTime;
				}finally{
					_lockObject.unlock();
				}
			}
		});
	}


	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					_ganttChart.removeAll();
				}finally{
					_lockObject.unlock();
				}
			}
		});
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}

	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			final ProjectMemento assignedProject, final DateTime dateTime) {
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
				
					if(_ganttModel.getKickoffTime().getDayIntervalTo(new Time(dateTime.toDate())) > 0)
					{
						_needToUpdateDiagramm = true;
					}
					
					if( _needToUpdateDiagramm ){
						final Map<String, EmployeeTaskMemento> projectTasks = new TreeMap<String, EmployeeTaskMemento>();
						if( company != null && company.getITDepartment() != null && company.getITDepartment().getEmployees() != null ){
							Map<String, EmployeeMemento> employees = company.getITDepartment().getEmployees();
							for (Map.Entry<String, EmployeeMemento> entry : employees.entrySet()){
								Map<String, EmployeeTaskMemento> tasks = entry.getValue().getTasks();
								for (Map.Entry<String, EmployeeTaskMemento> taskEntry : tasks.entrySet() ){
									if( !projectTasks.containsKey(taskEntry.getValue().getTaskName())){
										projectTasks.put(taskEntry.getValue().getTaskName(), taskEntry.getValue() );
									}
								}
							}
						}

						drawGanttDiagramm(projectTasks, assignedProject, dateTime);
						_needToUpdateDiagramm = false;
					}
					
				}finally{
					_lockObject.unlock();
				}
				
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		try{
			_lockObject.lock();
			_needToUpdateDiagramm = true;
		}finally{
			_lockObject.unlock();
		}	
	}

}
