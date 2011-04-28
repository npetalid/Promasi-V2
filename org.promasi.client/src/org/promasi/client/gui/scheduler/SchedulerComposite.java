package org.promasi.client.gui.scheduler;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import org.eclipse.nebula.widgets.ganttchart.GanttChart;
import org.eclipse.nebula.widgets.ganttchart.GanttEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.List;
import org.promasi.client.gui.company.Project;
import org.promasi.game.IGame;
import org.promasi.game.company.Company;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;
import org.eclipse.swt.SWT;
import org.joda.time.DateTime;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;

public class SchedulerComposite extends Composite implements ITaskDialogListener{

	/**
	 * 
	 */
	protected Object _result;
	
	/**
	 * 
	 */
	private GanttChart _ganttChart;
	
	/**
	 * 
	 */
	private Map<String, ScheduledTask> _scheduledProjectTasks;
	
	/**
	 * 
	 */
	private Map<String, GanttEvent> _ganttEvents;
	
	/**
	 * 
	 */
	private Button _newTaskButton ;
	
	/**
	 * 
	 */
	private Company _company;
	
	/**
	 * 
	 */
	private Project _project;
	
	/**
	 * 
	 */
	private List _tasksList;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private Decorations _taskDecoration;
	
	/**
	 * 
	 */
	private Button _removeTaskButton;
	
	/**
	 * 
	 */
	private Button _applyButton;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SchedulerComposite(Composite parent, int style, IGame game, Company company, Project project )throws NullArgumentException, IllegalArgumentException {
		super(parent, style);

		if(game==null){
			throw new NullArgumentException("Wrong argument game==null");
		}
		
		if(company==null){
			throw new NullArgumentException("Wrong argument project==null");
		}
		
		if(project==null){
			throw new NullArgumentException("Wrong argument project==null");
		}
		
		_company=company;
		_project=project;
		_scheduledProjectTasks=new TreeMap<String, ScheduledTask>();
		_ganttEvents=new TreeMap<String, GanttEvent>();
		_game=game;
		
		_tasksList = new List(this, SWT.BORDER);
		
		_applyButton = new Button(this, SWT.NONE);
		_applyButton.setEnabled(false);
		_applyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				applyScheduledTasks();
			}
		});
		_applyButton.setText("Apply");
		
		_newTaskButton = new Button(this, SWT.NONE);
		_newTaskButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				openTaskDialog();
			}
		});
		
		_newTaskButton.setText("New Task");
		
		_removeTaskButton = new Button(this, SWT.NONE);
		_removeTaskButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selectionIndex=_tasksList.getSelectionIndex();
				if(selectionIndex>=0){
					String taskId=_tasksList.getItem(selectionIndex);
					removeTask(taskId);
				}
			}
		});
		
		_removeTaskButton.setText("Remove");
		
		Button clearButton = new Button(this, SWT.NONE);
		clearButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				_scheduledProjectTasks.clear();
				_applyButton.setEnabled(false);
				updateDialog();
			}
		});
		
		clearButton.setText("Clear");
		
		Composite composite = new Composite(this, SWT.NONE);
		GroupLayout gl__shell = new GroupLayout(this);
		gl__shell.setHorizontalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.addContainerGap()
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(_removeTaskButton, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
						.add(gl__shell.createParallelGroup(GroupLayout.TRAILING)
							.add(_tasksList, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.add(_newTaskButton, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)))
					.add(18)
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(GroupLayout.TRAILING, gl__shell.createSequentialGroup()
							.add(clearButton, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.UNRELATED)
							.add(_applyButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.add(composite, GroupLayout.DEFAULT_SIZE, 707, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl__shell.setVerticalGroup(
			gl__shell.createParallelGroup(GroupLayout.LEADING)
				.add(gl__shell.createSequentialGroup()
					.addContainerGap()
					.add(gl__shell.createParallelGroup(GroupLayout.LEADING)
						.add(gl__shell.createSequentialGroup()
							.add(_tasksList, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
							.addPreferredGap(LayoutStyle.RELATED)
							.add(_newTaskButton))
						.add(composite, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
					.addPreferredGap(LayoutStyle.RELATED)
					.add(gl__shell.createParallelGroup(GroupLayout.BASELINE)
						.add(_applyButton)
						.add(clearButton)
						.add(_removeTaskButton))
					.addContainerGap())
		);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		_ganttChart=new GanttChart(composite, SWT.MULTI);
		_ganttChart.setVisible(true);
		
		this.setLayout(gl__shell);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 * 
	 * @param taskId
	 */
	private void removeTask(String taskId){
		if(_scheduledProjectTasks.containsKey(taskId)){
			_scheduledProjectTasks.remove(taskId);
			GanttEvent event=_ganttEvents.get(taskId);
			_ganttEvents.remove(taskId);
			event.dispose();
			updateDialog();
			_applyButton.setEnabled(true);
		}
	}
	
	/**
	 * 
	 */
	private synchronized void openTaskDialog(){
		try {
			/*_taskDialog = new TaskDialog( this.getShell(), SWT.DIALOG_TRIM, _project, _company.getEmployees(), _scheduledProjectTasks, _company.getDayDuration() );
			_taskDialog.addListener(this);
			_taskDialog.open();
			_taskDialog=null;*/
			
			try {
				_taskDecoration = new Decorations(this.getParent().getParent(), SWT.CLOSE);
				_taskDecoration.setLayoutData(new GridData(GridData.FILL_BOTH));
				_taskDecoration.setLayout(new FillLayout());
				
			    TaskComposite taskComposite=new TaskComposite(_taskDecoration,SWT.FILL,_project, _company.getEmployees(), _scheduledProjectTasks, _company.getDayDuration());
			    taskComposite.addListener(this);
			    _taskDecoration.setBounds(new Rectangle(0,0,TaskComposite.CONST_DEFAULT_COMPOSITE_WIDTH+10,TaskComposite.CONST_DEFAULT_COMPOSITE_HEIGHT));
			    _taskDecoration.setVisible(true);
			    _taskDecoration.setText("Scheduler");
			    _taskDecoration.setFocus();
			    _taskDecoration.layout();

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NullArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//} catch (NullArgumentException e) {
			//Logger
		}catch (SerializationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public synchronized void taskCreated(ScheduledTask task) {
		_scheduledProjectTasks.put(task.getTaskId(), task);

		DateTime endDate=task.getEndDate();
		DateTime startDate=task.getStartDate();
		Calendar cStartDate=startDate.toCalendar(Locale.getDefault());
		Calendar cEndDate=endDate.toCalendar(Locale.getDefault());
		GanttEvent event=new GanttEvent(_ganttChart, task.getTaskName(),cStartDate,cEndDate,0);
		_ganttEvents.put(task.getTaskId(), event);
		event.setLocked(true);
		
		Map<String, ScheduledTask> dependencies=task.getDependencies();
		for(Map.Entry<String, ScheduledTask> entry : dependencies.entrySet()){
			if(_ganttEvents.containsKey(entry.getKey())){
				_ganttChart.addConnection(_ganttEvents.get(entry.getKey()), event);
			}
		}
		
		_tasksList.removeAll();
		for(Map.Entry<String, ScheduledTask> entry : _scheduledProjectTasks.entrySet()){
			_tasksList.add(entry.getKey());
		}
		
		_taskDecoration.dispose();
		_applyButton.setEnabled(true);
	}
	
	/**
	 * 
	 */
	private void updateDialog(){
		
		for(Map.Entry<String, GanttEvent> entry : _ganttEvents.entrySet()){
			if(!this.getShell().isDisposed() && !this.getShell().getDisplay().isDisposed()){
				entry.getValue().dispose();
			}
		}
		
		_tasksList.removeAll();
		_ganttEvents.clear();
		for(Map.Entry<String, ScheduledTask> entry : _scheduledProjectTasks.entrySet()){
			_tasksList.add(entry.getKey());
			DateTime endDate=entry.getValue().getEndDate();
			DateTime startDate=entry.getValue().getStartDate();
			Calendar cStartDate=startDate.toCalendar(Locale.getDefault());
			Calendar cEndDate=endDate.toCalendar(Locale.getDefault());
			GanttEvent event=new GanttEvent(_ganttChart, entry.getValue().getTaskName(),cStartDate,cEndDate,0);
			_ganttEvents.put(entry.getKey(), event);
			event.setLocked(true);
		}
		
		for(Map.Entry<String, ScheduledTask> entry : _scheduledProjectTasks.entrySet()){
			Map<String, ScheduledTask> dependencies=entry.getValue().getDependencies();
			GanttEvent event=_ganttEvents.get(entry.getKey());
			for(Map.Entry<String, ScheduledTask> depEndtry : dependencies.entrySet()){
				GanttEvent depEvent=_ganttEvents.get(depEndtry.getKey());
				_ganttChart.addConnection(depEvent, event);
			}
		}
		
		if(_tasksList.getItemCount()>0){
			_tasksList.select(0);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean applyScheduledTasks(){
		Map<String, Vector<SerializableEmployeeTask> > tasksPerEmployee=new TreeMap<String, Vector<SerializableEmployeeTask>>();
		for(Map.Entry<String, ScheduledTask> entry : _scheduledProjectTasks.entrySet()){
			try {
				Map<String, SerializableEmployeeTask> employeeTasks=entry.getValue().getEmployeeTasks(_project.getStartDate());
				for( Map.Entry<String, SerializableEmployeeTask> tasksEntry : employeeTasks.entrySet() ){
					if( tasksPerEmployee.containsKey(tasksEntry.getKey()) ){
						tasksPerEmployee.get(tasksEntry.getKey()).add(tasksEntry.getValue());
					}else{
						tasksPerEmployee.put( tasksEntry.getKey() , new Vector<SerializableEmployeeTask>());
						tasksPerEmployee.get(tasksEntry.getKey()).add(tasksEntry.getValue());
					}
				}
				
				_applyButton.setEnabled(false);
			} catch (IllegalArgumentException e) {
				return false;
			} catch (NullArgumentException e) {
				return false;
			}
		}
		
		// assign tasks
		for(Entry<String, Vector<SerializableEmployeeTask>> entry : tasksPerEmployee.entrySet()){
			try {
				_game.assignTasks(entry.getKey(), entry.getValue());
			} catch (NullArgumentException e) {
				return false;
			}
		}
		
		return true;
	}
}
