package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.joda.time.DateTime;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.project.ProjectMemento;
import org.swiftgantt.Config;
import org.swiftgantt.GanttChart;
import org.swiftgantt.common.Time;
import org.swiftgantt.model.GanttModel;
import org.swiftgantt.model.Task;
import org.swiftgantt.ui.TimeUnit;

public class GanttSchedulerJPanel extends JPanel {

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
	public static final int CONST_DURATION_MULTIPLIER = 10;
	
	/**
	 * 
	 */
	public GanttSchedulerJPanel(){
		_ganttChart = new GanttChart();
		_ganttChart.setPreferredSize(new Dimension(100,100));
		
		setLayout(new BorderLayout());
		_ganttChart.setVisible(true);
		_ganttChart.setBounds(0, 0, 100, 100);
		_ganttChart.setTimeUnit(TimeUnit.Day);
		Config config = _ganttChart.getConfig();
		config.setWorkingTimeBackColor(Color.YELLOW);//Set background color for working time.
		config.setTimeUnitWidth(50);//Set width for time unit
		config.setWorkingDaysSpanOfWeek(new int[]{Calendar.MONDAY, Calendar.THURSDAY});//Set span of working days in each week
		config.setAllowAccurateTaskBar(true);//Set true if you want to show accurate task bar.
		add(_ganttChart, BorderLayout.CENTER);
		_ganttModel = new GanttModel();
		_ganttChart.setModel(_ganttModel);
		_lockObject = new ReentrantLock();
		setBorder(BorderFactory.createTitledBorder("Scheduler"));
	}

	/**
	 * 
	 * @param owner
	 * @param company
	 * @param project
	 * @param dateTime
	 */
	public void projectAssigned(String owner, CompanyMemento company,final ProjectMemento project, final DateTime dateTime) {
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

	/**
	 * 
	 */
	public void clearScheduler() {
		_ganttChart.removeAll();
	}

	/**
	 * 
	 * @param company
	 * @param project
	 */
	public void setCompany(final CompanyMemento company, final ProjectMemento project) {

		try{
			_lockObject.lock();
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

			drawGanttDiagramm(projectTasks, project);
		}finally{
			_lockObject.unlock();
		}
	}

	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 
	 * @param scheduledTasks
	 * @param assignedProject
	 */
	private void drawGanttDiagramm( final Map<String, EmployeeTaskMemento> scheduledTasks, final ProjectMemento assignedProject ){
		try{
			_lockObject.lock();
			if( _ganttModel != null && _projectAssignDate != null ){
				Task taskGroup = new Task(assignedProject.getName(), new Time(_projectAssignDate.toDate()), new Time(_projectAssignDate.plusHours(assignedProject.getProjectDuration()/10).toDate()));
				Map<String, Task> ganttTasks = new TreeMap<String, Task>();
				for (Map.Entry<String, EmployeeTaskMemento> entry : scheduledTasks.entrySet() ){
					EmployeeTaskMemento employeeTask = entry.getValue();
					Time startTime = new Time(_projectAssignDate.plusHours( employeeTask.getFirstStep() ).toDate());
					Time endTime = new Time(_projectAssignDate.plusHours( employeeTask.getLastStep()).toDate());
					Task newTask = new Task(employeeTask.getTaskName(), startTime, endTime );
					ganttTasks.put(employeeTask.getTaskName(), newTask);
					taskGroup.add(newTask);
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
				_ganttModel.setDeadline(new Time(_projectAssignDate.plusHours(assignedProject.getProjectDuration()/CONST_DURATION_MULTIPLIER).toDate()));
				if( !taskGroup.isLeaf() ){
					_ganttModel.addTask(taskGroup);
				}
				
				_ganttChart.setModel(_ganttModel);
			}
		}finally{
			_lockObject.unlock();
		}
	}

}
