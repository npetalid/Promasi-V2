/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utilities.file.RootDirectory;
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
public class SchedulerDesktopApplication extends ADesktopApplication implements ICompanyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String CONST_APPNAME = "Scheduler";
	
	/**
	 * 
	 */
	public static final String CONST_APP_ICON = "planner.png";
	
	/**|
	 * 
	 */
	private GanttChart _gantChart;
	
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
	 * @param appName
	 * @throws GuiException
	 * @throws IOException 
	 */
	public SchedulerDesktopApplication( IGame game ) throws GuiException, IOException {
		super(CONST_APPNAME, RootDirectory.getInstance().getImagesDirectory() + CONST_APP_ICON);
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_game = game;
		_game.addCompanyListener(this);
		_gantChart = new GanttChart();
		_gantChart.setPreferredSize(new Dimension(100,100));
		
		setLayout(new BorderLayout());
		_gantChart.setVisible(true);
		_gantChart.setBounds(0, 0, 100, 100);
		_gantChart.setTimeUnit(TimeUnit.Week);

		Config config = _gantChart.getConfig();
		config.setWorkingTimeBackColor(Color.YELLOW);//Set background color for working time.
		config.setTimeUnitWidth(50);//Set width for time unit
		config.setWorkingDaysSpanOfWeek(new int[]{Calendar.MONDAY, Calendar.THURSDAY});//Set span of working days in each week
		config.setAllowAccurateTaskBar(true);//Set true if you want to show accurate task bar.
		add(_gantChart, BorderLayout.CENTER);
		_lockObject = new ReentrantLock();
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
							_gantChart.setModel(_ganttModel);
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
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					_gantChart.removeAll();
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void projectFinished(String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime) {
		ClearGanttChart();
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime) {
		ClearGanttChart();
	}

	@Override
	public void onExecuteWorkingStep(final String owner,final CompanyMemento company,final ProjectMemento assignedProject,final DateTime dateTime) {
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
		
		if( !projectTasks.isEmpty() && assignedProject != null ){
			try {
				SwingUtilities.invokeAndWait( new Runnable() {
					
					@Override
					public void run() {
						try{
							_lockObject.lock();
							_ganttModel.removeAll();
							Task taskGroup = new Task("My Work 1", new Time(), new Time().increaseYear());
							Task task1 = new Task("Sub-task 1", new Time(), new Time().increaseWeek());
							Task task2 = new Task();
							task2.setName("Sub-task 2");
							task2.setStart(new Time());
							task2.setEnd(new Time().increaseMonth());// Since version 0.3.0, the end time set to a task is included in duration of the task
							task2.addPredecessor(task1);

							taskGroup.add(new Task[]{task1, task2});

							task2.addPredecessor(task1);
							
							_ganttModel.addTask(taskGroup);
							_gantChart.setModel(_ganttModel);
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

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}

}
