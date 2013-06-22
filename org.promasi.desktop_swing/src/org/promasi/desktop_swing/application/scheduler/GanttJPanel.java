package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.DepartmentModel;
import org.promasi.game.model.generated.DepartmentModel.Employees.Entry;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.EmployeeTaskModel;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.game.model.generated.ProjectModel.ProjectTasks;
import org.promasi.game.model.generated.ProjectTaskModel;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

import com.jidesoft.gantt.DateGanttChart;
import com.jidesoft.gantt.DefaultGanttEntry;
import com.jidesoft.gantt.DefaultGanttEntryRelation;
import com.jidesoft.gantt.DefaultGanttLabelRenderer;
import com.jidesoft.gantt.DefaultGanttModel;
import com.jidesoft.gantt.GanttChart;
import com.jidesoft.gantt.GanttEntryRelation;
import com.jidesoft.gantt.IntervalMarker;
import com.jidesoft.range.TimeRange;
import com.jidesoft.scale.DatePeriod;
import com.jidesoft.scale.DateScaleModel;
import com.jidesoft.scale.ResizePeriodsPopupMenuCustomizer;
import com.jidesoft.scale.VisiblePeriodsPopupMenuCustomizer;
import com.jidesoft.swing.CornerScroller;

/**
 * 
 * @author alekstheod
 * Represent the gantt chart panel in 
 * promasi project. This class will draw the given EmployeeTasks
 * as a ganttchart diagram.
 */
public class GanttJPanel extends JPanel  implements ICompanyListener, IDepartmentListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String CONST_TITLE = "Scheduler";
	
	/**
	 * Lock object. Needed to synchronize
	 * the {@link = ICompanyListener} interface
	 * methods implementation.
	 */
	private Lock _lockObject;
	
	/**
	 * Date-Time when the project was assigned.
	 */
	private DateTime _projectAssignDate;
	
	/**
	 * Current date time received from the company.
	 */
	private DateTime _currentDate;
	
	/**
	 * Deadline marker needed in order to draw
	 * the project's start and deadlines.
	 */
	private  IntervalMarker<Date> _deadlineMarker;
	
	/**
	 * Instance of {@link = GanttChartPane} which will
	 * draw the gantt chart representation of the running
	 * projects.
	 */
	private GanttChart< Date, DefaultGanttEntry<Date> > _ganttChart;
	
	/**
	 * List of the running tasks.
	 */
	private Map<String, DefaultGanttEntry<Date> > _runningTasks;
	
	/**
	 * Represent the mapping of task to working on it employees.
	 */
	//private Map< DefaultGanttEntry<Date> , List< EmployeeModel > > _employees;
	
	/**
	 * Project step per hour multiplier. Needed in order
	 * to calculate the task duration in hours.
	 */
	public static final int CONST_DURATION_MULTIPLIER = 10;
	
	/**
	 * Constructor will initialize the object.
	 * While initialization of the instance it will be
	 * registered as Department and Company listener on the
	 * given {@link IGame} interface implementation instance.
	 * @param game instance of {@link IGame} interface implementation
	 * which represent the game on the ProMaSi system.
	 * @throws GuiException in case of invalid arguments.
	 */
	public GanttJPanel( final IGame game ) throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game");
		}

		_ganttChart= new DateGanttChart<DefaultGanttEntry<Date>>(new DefaultGanttModel<Date, DefaultGanttEntry<Date>>());
		_ganttChart.setShowGrid(false);
		
		_lockObject = new ReentrantLock();
		setBackground(Colors.LightBlue.alpha(1f));
		setBorder(BorderFactory.createTitledBorder(CONST_TITLE));
		setLayout(new BorderLayout());
		
        JScrollPane chartScroll = new JScrollPane(_ganttChart);
        chartScroll.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new CornerScroller(chartScroll));
        
		add(chartScroll, BorderLayout.CENTER);
		game.addCompanyListener(this);
		game.addDepartmentListener(this);
		
		//_employees = new TreeMap<>();
		_ganttChart.setEditable(false);
		_ganttChart.setOpaque(false);
		_ganttChart.setBackground(Colors.White.alpha(0f));
		_ganttChart.setSelectionBackground(Colors.White.alpha(0f)) ;
		_ganttChart.setShowGrid(true);
		_ganttChart.setEnabled(false);
		_ganttChart.getScaleArea().addPopupMenuCustomizer(new VisiblePeriodsPopupMenuCustomizer<Date>());
		_ganttChart.getScaleArea().addPopupMenuCustomizer(new ResizePeriodsPopupMenuCustomizer<Date>(_ganttChart));
		_ganttChart.setEditable(false);
		
		final JPopupMenu popup = new JPopupMenu();
		
		_ganttChart.add(popup);
		final JMenuItem clearItem = new JMenuItem("Clear");
		popup.add(clearItem);
		clearItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List< EmployeeTaskModel > tasks = new LinkedList<>();
				for( Map.Entry<String, DefaultGanttEntry<Date> > entry : _runningTasks.entrySet()   ){
					EmployeeTaskModel Model = new EmployeeTaskModel();
					Model.setProjectTaskName(entry.getKey());
					tasks.add(Model);
				}
				
				game.removeTasks(tasks);
			}
		});
		
		_ganttChart.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getButton() == MouseEvent.BUTTON3 ){
					if( _ganttChart.getSelectedRow() >= 0 ){
						popup.removeAll();
						popup.add(clearItem);
						JMenuItem removeTaskItem = new JMenuItem("Remove");
						removeTaskItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								DefaultGanttEntry<Date> entry = _ganttChart.getEntryAt(_ganttChart.getSelectedRow());
								EmployeeTaskModel Model = new EmployeeTaskModel();
								Model.setProjectTaskName(entry.getName());
								List<EmployeeTaskModel> tasks = new LinkedList<>();
								tasks.add(Model);
								game.removeTasks(tasks);
							}
						});
						
						popup.add(removeTaskItem);
					}
					
					popup.show(_ganttChart, arg0.getX(), arg0.getY());
				}
			}
		});

		_runningTasks = new TreeMap<>();
		
        final IntervalMarker<Date> todayMarker = new IntervalMarker<Date>(Color.RED, null, null){ 
            @Override
            public Date getStartInstant() {
                return _currentDate.toDate();
            }

            @Override
            public Date getEndInstant() {
                return _currentDate.toDate();
            }
        };
        
        _deadlineMarker = new IntervalMarker<Date>();
        _ganttChart.addPeriodBackgroundPainter(_deadlineMarker);
        
        _currentDate = new DateTime();
		_ganttChart.addPeriodBackgroundPainter(todayMarker);
		_ganttChart.setDefaultLabelRenderer(new DefaultGanttLabelRenderer());
		_ganttChart.setLabelPosition(SwingConstants.TRAILING);	
	}
	
	/**
	 * Will check if the gantt panel
	 * has assigned task with the same
	 * name as a given argument
	 * @param taskName task name to check
	 * @return true if task is already running, false otherwise.
	 */
	public boolean hasRunningTask( String taskName ){
		try{
			_lockObject.lock();
			return _runningTasks.containsKey(taskName);
		}finally{
			_lockObject.unlock();
		}
	}
	
	private boolean setTaskDuration(DefaultGanttEntry<Date> task, double completion ){
		boolean result = false;
		
		if( task != null ){
			if( completion >= 1 ){
				task.setCompletion(1);
			}else if( completion < 0 ){
				task.setCompletion(0);
			}else{
				task.setCompletion(completion);
			}
		}
		
		return result;
	}
	
	static boolean isTaskInList( String taskName, ProjectTasks tasks){
		boolean result = false;
		
		for( org.promasi.game.model.generated.ProjectModel.ProjectTasks.Entry entry : tasks.getEntry() ){
			if( entry.getKey().equals(taskName)){
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	static ProjectTaskModel getTask(String taskName, ProjectTasks tasks){
		ProjectTaskModel result = null;
		
		for( org.promasi.game.model.generated.ProjectModel.ProjectTasks.Entry entry : tasks.getEntry() ){
			if( entry.getKey().equals(taskName)){
				result = entry.getValue();
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * Will update the Gantt diagram presentation.
	 * @param scheduledTasks list of the scheduled tasks.
	 * @param assignedProject Instance of {@link = ProjectModel} which represent the
	 * assigned project.
	 * @param dateTime the current date-time.
	 */
	private void updateGanttDiagramm( Map<String, EmployeeTaskModel> scheduledTasks, ProjectModel assignedProject, DateTime dateTime ){
		try{
			_lockObject.lock();
			DefaultGanttModel<Date, DefaultGanttEntry<Date>> model = new DefaultGanttModel<Date, DefaultGanttEntry<Date>>();
	        DateScaleModel scaleModel = new DateScaleModel( DatePeriod.MONTH, DatePeriod.YEAR );
		    model.setScaleModel(scaleModel);

		    Calendar projectStartDate = Calendar.getInstance(Locale.getDefault());
		    projectStartDate.setTime(_projectAssignDate.toDate());
		    
		    Calendar projectEndDate = Calendar.getInstance(Locale.getDefault());
		    projectEndDate.setTime(_projectAssignDate.plusHours(assignedProject.getProjectDuration()/CONST_DURATION_MULTIPLIER).toDate());
            model.setRange(new TimeRange(projectStartDate, projectEndDate));
 
			if(_projectAssignDate != null ){
				_runningTasks.clear();
				Map<String, DefaultGanttEntry<Date>> ganttTasks = new TreeMap<String,  DefaultGanttEntry<Date>>();
				Map<Integer, DefaultGanttEntry<Date>> sortedTasks = new TreeMap<>();
				for (Map.Entry<String, EmployeeTaskModel> entry : scheduledTasks.entrySet() ){
					EmployeeTaskModel employeeTask = entry.getValue();
					Date startDate = _projectAssignDate.plusHours( employeeTask.getFirstStep() ).toDate();
					Calendar startTime = Calendar.getInstance(Locale.getDefault());
					startTime.setTime(startDate);
					
					Calendar endTime = Calendar.getInstance(Locale.getDefault());
					endTime.setTime(_projectAssignDate.plusHours( employeeTask.getLastStep()).toDate());
	
					DefaultGanttEntry<Date> newTask = new DefaultGanttEntry<Date>(employeeTask.getTaskName(), Date.class, new TimeRange(startTime, endTime), 0);
					ganttTasks.put(employeeTask.getTaskName(), newTask);
					
					if( assignedProject.getProjectTasks() != null && isTaskInList(employeeTask.getProjectTaskName(), assignedProject.getProjectTasks())){
						ProjectTaskModel prjTask = getTask(employeeTask.getProjectTaskName(), assignedProject.getProjectTasks() );
						setTaskDuration(newTask, prjTask.getProgress()/100.0);
					}

					_runningTasks.put(entry.getKey(), newTask);
					Integer value = employeeTask.getFirstStep();
					while( sortedTasks.containsKey(value)){
						value++;
					}
					
					sortedTasks.put(value, newTask);
				}
				
				for( Map.Entry<Integer, DefaultGanttEntry<Date> > entry : sortedTasks.entrySet()){
					model.addGanttEntry(entry.getValue());
				}
				
				for (Map.Entry<String, EmployeeTaskModel> entry : scheduledTasks.entrySet() ){
					EmployeeTaskModel employeeTask = entry.getValue();
					if(  employeeTask.getDependencies() != null ){
						for( String taskName : employeeTask.getDependencies() ){
							if( ganttTasks.containsKey(taskName) && ganttTasks.containsKey(employeeTask.getTaskName() ) ){
								model.getGanttEntryRelationModel().addEntryRelation(new DefaultGanttEntryRelation<DefaultGanttEntry<Date>>(ganttTasks.get(taskName), ganttTasks.get(employeeTask.getTaskName()),  GanttEntryRelation.ENTRY_RELATION_FINISH_TO_START));
							}
						}
					}
				}
				
				_ganttChart.setModel(model);
			}
		}finally{
			_lockObject.unlock();
		}
	}
	
	/**
	 * Called when the new project assigned.
	 */
	@Override
	public void projectAssigned(String owner, CompanyModel company,final ProjectModel project, final DateTime dateTime) {
		try{
			_lockObject.lock();
			_projectAssignDate = dateTime;
			_deadlineMarker.setStartInstant(_projectAssignDate.toDate());
			_deadlineMarker.setEndInstant(_projectAssignDate.plusHours(project.getProjectDuration()).toDate());
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					_ganttChart.getScaleArea().setStart(_projectAssignDate.toDate());
					_ganttChart.getScaleArea().setEnd(_projectAssignDate.plusHours(project.getProjectDuration()).toDate());
					updateGanttDiagramm( new TreeMap<String, EmployeeTaskModel>(), project, dateTime);
				}
			});
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void projectFinished(String owner, CompanyModel company,
			final ProjectModel project, final DateTime dateTime) {
		try{
			_lockObject.lock();
			_projectAssignDate = dateTime;
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					updateGanttDiagramm( new TreeMap<String, EmployeeTaskModel>(), project, dateTime);
				}
			});
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyModel company,
			ProjectModel assignedProject, DateTime dateTime) {
	}

	/**
	 * Called on step execution.
	 */
	@Override
	public void onExecuteWorkingStep(String owner, final CompanyModel company, final ProjectModel assignedProject, final DateTime dateTime) {
		SwingUtilities.invokeLater( new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					_currentDate = dateTime;
					final Map<String, EmployeeTaskModel> projectTasks = new TreeMap<String, EmployeeTaskModel>();
					if( company != null && company.getItDepartment() != null && company.getItDepartment().getEmployees() != null ){
						for (Entry entry : company.getItDepartment().getEmployees().getEntry()){
							List<org.promasi.game.model.generated.EmployeeModel.Tasks.Entry> tasks = entry.getValue().getTasks().getEntry();
							for (org.promasi.game.model.generated.EmployeeModel.Tasks.Entry taskEntry : tasks ){
								if( !projectTasks.containsKey(taskEntry.getValue().getTaskName())){
									projectTasks.put(taskEntry.getValue().getTaskName(), taskEntry.getValue() );
								}
							}
						}
					}

					boolean needUpdate = false;
					for( Map.Entry<String, EmployeeTaskModel> entry : projectTasks.entrySet()){
						if( !_runningTasks.containsKey(entry.getKey()) ){
							needUpdate = true;
							break;
						}
					}
					
					for( Map.Entry<String, DefaultGanttEntry<Date>> entry : _runningTasks.entrySet()){
						if( !projectTasks.containsKey(entry.getKey())){
							needUpdate = true;
							break;
						}
					}
					
					if( needUpdate ){
						updateGanttDiagramm(projectTasks, assignedProject, dateTime);
					}else{
						for( Map.Entry<String, DefaultGanttEntry<Date>> entry : _runningTasks.entrySet()){
							if( projectTasks.containsKey(entry.getKey())){
								String taskName = projectTasks.get(entry.getKey()).getProjectTaskName();
								if( assignedProject.getProjectTasks() != null && isTaskInList(taskName, assignedProject.getProjectTasks()) ){
									ProjectTaskModel prjTask = getTask(taskName, assignedProject.getProjectTasks());
									setTaskDuration(entry.getValue(), prjTask.getProgress()/100.0);
								}
							}
						}
					}
							
					_ganttChart.getScaleArea().setStart(_currentDate.minusDays(30).toDate());
					_ganttChart.repaint();
				}finally{
					_lockObject.unlock();
				}	
			}
		});
	}

	@Override
	public void companyAssigned(String owner, CompanyModel company) {}

	@Override
	public void employeeDischarged(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {}

	@Override
	public void employeeHired(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {}

	@Override
	public void tasksAssigned(String director, DepartmentModel department, DateTime dateTime) {}

	@Override
	public void tasksAssignFailed(String director, DepartmentModel department, DateTime dateTime) {}

	@Override
	public void departmentAssigned(String director, DepartmentModel department, DateTime dateTime) {}
}
