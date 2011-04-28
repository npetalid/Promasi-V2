package org.promasi.client.gui.scheduler;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.promasi.client.gui.company.Project;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.project.SerializableProjectTask;
import org.promasi.utilities.exceptions.NullArgumentException;

public class TaskComposite extends Composite
{
	/**
	 * 
	 */
	protected Object _result;
	
	/**
	 * 
	 */
	private Combo _employeesCombo;
	
	/**
	 * 
	 */
	private Combo _tasksCombo;
	
	/**
	 * 
	 */
	private Combo _scheduledTasksCombo;
	
	/**
	 * 
	 */
	private Project _project;
	
	/**
	 * 
	 */
	private Map<String, SerializableEmployee> _availableEmployees;
	
	/**
	 * 
	 */
	private Map<String, ScheduledEmployee> _scheduledEmployees;
	
	/**
	 * 
	 */
	private Map<String, ScheduledTask> _scheduledTasks;
	
	/**
	 * 
	 */
	private LinkedList<ITaskDialogListener> _listeners;
	
	/**
	 * 
	 */
	private Spinner _workingDurationSpin;
	
	/**
	 * 
	 */
	private Table _scheduledEmployeesTable;
	
	/**
	 * 
	 */
	private Table _dependenciesTable;
	
	/**
	 * 
	 */
	private Browser _employeeInfoBrowser;
	
	/**
	 * 
	 */
	private Browser _taskInfoBrowser;
	
	/**
	 * 
	 */
	private Map<String, ScheduledTask> _dependencies;
	
	/**
	 * 
	 */
	public static final int CONST_DEFAULT_COMPOSITE_WIDTH=750;
	
	/**
	 * 
	 */
	public static final int CONST_DEFAULT_COMPOSITE_HEIGHT=550;
	
	/**
	 * 
	 */
	private Text _taskId;
	
	/**
	 * 
	 */
	private int _duration;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TaskComposite(Composite parent, int style,Project project, Map<String, SerializableEmployee> employees, Map<String, ScheduledTask> scheduledTasks, int dayDuration)throws NullArgumentException, IllegalArgumentException {
		super(parent, style);
		if(employees==null){
			throw new NullArgumentException("Wrong argument employees==null");
		}
		
		if(scheduledTasks==null){
			throw new NullArgumentException("Wrong argument exception==null");
		}
		
		if(project==null){
			throw new NullArgumentException("Wrong argument project==null");
		}
		
		if(dayDuration<=0){
			throw new IllegalArgumentException("Wrong argument dayDuration<=0");
		}
		
		for(Map.Entry<String, SerializableEmployee> entry : employees.entrySet()){
			if(entry.getKey()==null || entry.getValue()==null || !entry.getKey().equals(entry.getValue().getEmployeeId()) ){
				throw new IllegalArgumentException("Wrong argument employees invalid mapping");
			}
		}
		
		_project=project;
		_availableEmployees=new TreeMap<String, SerializableEmployee>(employees);
		_scheduledEmployees=new TreeMap<String, ScheduledEmployee>();
		_scheduledTasks=new TreeMap<String, ScheduledTask>(scheduledTasks);
		_listeners=new LinkedList<ITaskDialogListener>();
		_dependencies=new TreeMap<String, ScheduledTask>();
		_duration=dayDuration;
		setLayout(null);
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 0, CONST_DEFAULT_COMPOSITE_WIDTH, CONST_DEFAULT_COMPOSITE_HEIGHT-80);
		
		TabItem resourcesTab = new TabItem(tabFolder, SWT.NONE);
		resourcesTab.setText("Resources");
		
		Composite resourcesComposite = new Composite(tabFolder, SWT.NONE);
		resourcesTab.setControl(resourcesComposite);
		resourcesComposite.setLayout(null);
		
		_employeesCombo = new Combo(resourcesComposite, SWT.READ_ONLY);
		_employeesCombo.setBounds(11, 35, 630, 21);
		_employeesCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_employeesCombo.getSelectionIndex();
				if(selectionIndex>=0){
					String employeeId=_employeesCombo.getText();
					if(_availableEmployees.containsKey(employeeId)){
						SerializableEmployee employee=_availableEmployees.get(employeeId);
						_employeeInfoBrowser.setText(employee.getCurriculumVitae());
					}
				}else{
					_employeeInfoBrowser.setText("");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		Label lblEmployees = new Label(resourcesComposite, SWT.NONE);
		lblEmployees.setBounds(9, 15, 51, 13);
		lblEmployees.setText("Employees");
		
		Button addEmployeeButton = new Button(resourcesComposite, SWT.NONE);
		addEmployeeButton.setBounds(646, 35, 88, 23);
		addEmployeeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_employeesCombo.getSelectionIndex();
				if(selectionIndex>=0){
					try {
						String employeeId=_employeesCombo.getItem(selectionIndex);
						if(_availableEmployees.containsKey(employeeId) && !_scheduledEmployees.containsKey(employeeId)){
							SerializableEmployee employee=_availableEmployees.get(employeeId);
							_availableEmployees.remove(employeeId);
							String workingDuration=_workingDurationSpin.getText();
							int duration=Integer.parseInt(workingDuration);
							_scheduledEmployees.put(employeeId, new ScheduledEmployee(employeeId, employee.getFirstName(), employee.getLastName(), employee.getCurriculumVitae(),duration));
							updateDialog();
						}
					} catch (NullArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		addEmployeeButton.setText("Add");
		
		_workingDurationSpin = new Spinner(resourcesComposite, SWT.BORDER);
		_workingDurationSpin.setBounds(681, 73, 53, 21);
		_workingDurationSpin.setMaximum(5000);
		_workingDurationSpin.setMinimum(1);
		
		Label lblDuration = new Label(resourcesComposite, SWT.NONE);
		lblDuration.setBounds(589, 76, 74, 13);
		lblDuration.setText("Duration days :");
		
		_scheduledEmployeesTable = new Table(resourcesComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		_scheduledEmployeesTable.setBounds(13, 267, 721, 157);
		_scheduledEmployeesTable.setHeaderVisible(true);
		_scheduledEmployeesTable.setLinesVisible(true);
		
		_employeeInfoBrowser = new Browser(resourcesComposite, SWT.NONE);
		_employeeInfoBrowser.setBounds(11, 101, 723, 160);
		
		TableColumn tblclmnId = new TableColumn(_scheduledEmployeesTable, SWT.NONE);
		tblclmnId.setWidth(55);
		tblclmnId.setText("ID");
		
		TableColumn tblclmnFirstname = new TableColumn(_scheduledEmployeesTable, SWT.NONE);
		tblclmnFirstname.setWidth(156);
		tblclmnFirstname.setText("Firstname");
		
		TableColumn tblclmnLastname = new TableColumn(_scheduledEmployeesTable, SWT.NONE);
		tblclmnLastname.setWidth(182);
		tblclmnLastname.setText("Lastname");
		
		TableColumn tblclmnDuration = new TableColumn(_scheduledEmployeesTable, SWT.NONE);
		tblclmnDuration.setWidth(100);
		tblclmnDuration.setText("Duration");
		
		Label lblTaskid = new Label(this, SWT.NONE);
		lblTaskid.setBounds(10, 481, 42, 13);
		lblTaskid.setText("TaskId : ");
		
		TabItem dependenciesTab = new TabItem(tabFolder, SWT.NONE);
		dependenciesTab.setText("Dependencies");
		
		Composite dependenciesComposite = new Composite(tabFolder, SWT.NONE);
		dependenciesTab.setControl(dependenciesComposite);
		dependenciesComposite.setLayout(null);
		
		Label lblAlltasks = new Label(dependenciesComposite, SWT.NONE);
		lblAlltasks.setBounds(9, 107, 67, 13);
		lblAlltasks.setText("Dependencies");
		
		_scheduledTasksCombo = new Combo(dependenciesComposite, SWT.READ_ONLY);
		_scheduledTasksCombo.setBounds(11, 38, 726, 21);
		
		Label lblAllTasks = new Label(dependenciesComposite, SWT.NONE);
		lblAllTasks.setBounds(9, 19, 79, 13);
		lblAllTasks.setText("Scheduled Tasks");
		
		Button addDependencieButton = new Button(dependenciesComposite, SWT.NONE);
		addDependencieButton.setBounds(665, 77, 72, 23);
		addDependencieButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				addDependencie();
			}
		});
		addDependencieButton.setText("Add");
		
		Button btnRemove = new Button(dependenciesComposite, SWT.NONE);
		btnRemove.setBounds(593, 77, 60, 23);
		btnRemove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				removeDependencie();
			}
		});
		btnRemove.setText("Remove");
		
		_dependenciesTable = new Table(dependenciesComposite, SWT.BORDER | SWT.FULL_SELECTION);
		_dependenciesTable.setBounds(0, 127, 763, 372);
		_dependenciesTable.setHeaderVisible(true);
		_dependenciesTable.setLinesVisible(true);
		
		TableColumn tblclmnId_1 = new TableColumn(_dependenciesTable, SWT.NONE);
		tblclmnId_1.setWidth(100);
		tblclmnId_1.setText("ID");
		
		TableColumn tblclmnTaskName = new TableColumn(_dependenciesTable, SWT.NONE);
		tblclmnTaskName.setWidth(100);
		tblclmnTaskName.setText("Task Name");
		
		TabItem taskTab = new TabItem(tabFolder, SWT.NONE);
		taskTab.setText("Task");
		
		Composite taskComposite = new Composite(tabFolder, SWT.NONE);
		taskTab.setControl(taskComposite);
		taskComposite.setLayout(null);
		
		Label lblProject = new Label(taskComposite, SWT.NONE);
		lblProject.setBounds(10, 21, 65, 13);
		lblProject.setText("Project : ");
		
		_tasksCombo = new Combo(taskComposite, SWT.READ_ONLY);
		_tasksCombo.setBounds(11, 79, 726, 21);
		_tasksCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selectionIndex=_tasksCombo.getSelectionIndex();
				if(selectionIndex>=0){
					String projectTaskName=_tasksCombo.getText();
					Map<String, SerializableProjectTask> tasks=_project.getProjectTasks();
					if(tasks.containsKey(projectTaskName)){
						_taskInfoBrowser.setText(tasks.get(projectTaskName).getDescription());
					}
					
					int sameTasksCount=0;
					for(Map.Entry<String, ScheduledTask> entry : _scheduledTasks.entrySet()){
						if(entry.getValue().getTaskName().equals(projectTaskName)){
							sameTasksCount++;
						}
					}
					
					while(_scheduledTasks.containsKey(projectTaskName+" #"+sameTasksCount)){
						sameTasksCount++;
					}
					
					_taskId.setText(projectTaskName+" #"+sameTasksCount);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
		
		Label taskLabel = new Label(taskComposite, SWT.NONE);
		taskLabel.setBounds(10, 58, 65, 13);
		taskLabel.setText("Task");
		
		Label projectNameLabel = new Label(taskComposite, SWT.NONE);
		projectNameLabel.setBounds(81, 21, 65, 13);
		projectNameLabel.setText(_project.getProjectName());
		
		_taskInfoBrowser = new Browser(taskComposite, SWT.NONE);
		_taskInfoBrowser.setBounds(10, 106, 727, 389);
		
		Button createButton = new Button(this, SWT.NONE);
		createButton.setBounds(663, 476, 85, 23);
		createButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					int selectionIndex=_tasksCombo.getSelectionIndex();
					if(selectionIndex>=0){
						String taskName=new String(_tasksCombo.getText());
						ScheduledTask task;
						task = new ScheduledTask(_taskId.getText(),taskName, _project.getStartDate(),_scheduledEmployees , _dependencies, _scheduledTasks,_duration);

						for(ITaskDialogListener listener : _listeners){
							listener.taskCreated(task);
						}
					}

				} catch (IllegalArgumentException e) {
					//Logger
				} catch (NullArgumentException e) {
					//Logger
				}catch (EmployeeTaskConflictException e) {
			        MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.OK);      
			        messageBox.setText("Warning");
			        messageBox.setMessage("Employee with id = "+e.getEmployeeId()+ " already working on task = "+ e.getTaskName());
			        messageBox.open();
				}
			}
		});
		
		createButton.setText("Create");
		
		_taskId = new Text(this, SWT.BORDER);
		_taskId.setBounds(56, 478, 128, 19);
		this.setBounds(new Rectangle(0, 0, CONST_DEFAULT_COMPOSITE_WIDTH, CONST_DEFAULT_COMPOSITE_HEIGHT));
		updateDialog();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * 
	 */
	private void updateDialog(){
		_employeesCombo.removeAll();
		for(Map.Entry<String, SerializableEmployee> entry : _availableEmployees.entrySet()){
			_employeesCombo.add(entry.getKey());
			
		}
		
		if(_employeesCombo.getItemCount()>0){
			_employeesCombo.select(0);
			String employeeId=_employeesCombo.getText();
			if(_availableEmployees.containsKey(employeeId)){
				SerializableEmployee employee=_availableEmployees.get(employeeId);
				_employeeInfoBrowser.setText(employee.getCurriculumVitae());
			}
		}else{
			_employeeInfoBrowser.setText("");
		}
		
		_scheduledEmployeesTable.removeAll();
		for(Map.Entry<String, ScheduledEmployee> entry : _scheduledEmployees.entrySet()){
			TableItem item=new TableItem(_scheduledEmployeesTable,SWT.NONE);
			item.setText(0,entry.getValue().getEmployeeId());
			item.setText(1,entry.getValue().getFirstName());
			item.setText(2,entry.getValue().getLastName());
			item.setText(3,String.format("%d", entry.getValue().getWorkingDuration()));
		}
		
		int selectionIndex=_tasksCombo.getSelectionIndex();
		_tasksCombo.removeAll();
		Map<String, SerializableProjectTask> projectTasks=_project.getProjectTasks();
		for(Map.Entry<String, SerializableProjectTask> entry : projectTasks.entrySet()){
			if(!_dependencies.containsKey(entry.getKey())){
				_tasksCombo.add(entry.getKey());
			}
		}
		
		if(_tasksCombo.getItemCount()>=selectionIndex && _tasksCombo.getSelectionIndex()<0){
			_tasksCombo.select(selectionIndex);
			String projectTaskName=_tasksCombo.getText();
			if(projectTasks.containsKey(projectTaskName)){
				_taskInfoBrowser.setText(projectTasks.get(projectTaskName).getDescription());
				int sameTasksCount=0;
				for(Map.Entry<String, ScheduledTask> entry : _scheduledTasks.entrySet()){
					if(entry.getValue().getTaskName().equals(projectTaskName)){
						sameTasksCount++;
					}
				}
				
				_taskId.setText(projectTaskName+" #"+sameTasksCount);
			}
		}
		
		if(_scheduledEmployeesTable.getItemCount()!=0){
			_scheduledEmployeesTable.setSelection(0);
		}
		
		if(_employeesCombo.getItemCount()!=0 && _employeesCombo.getSelectionIndex()<0){
			_employeesCombo.select(0);
		}
		
		for(Map.Entry<String, ScheduledTask> entry : _scheduledTasks.entrySet()){
			_scheduledTasksCombo.add(entry.getKey());
		}
		
		if(_scheduledTasksCombo.getItemCount()!=0 && _scheduledTasksCombo.getSelectionIndex()<0){
			_scheduledTasksCombo.select(0);
		}
		
		_scheduledTasksCombo.removeAll();
		for(Map.Entry<String, ScheduledTask> entry : _scheduledTasks.entrySet()){
			_scheduledTasksCombo.add(entry.getKey());
		}
		
		_dependenciesTable.removeAll();
		for(Map.Entry<String, ScheduledTask> entry : _dependencies.entrySet()){
			TableItem item=new TableItem(_dependenciesTable,SWT.NONE);
			item.setText(0,entry.getKey());
			item.setText(1,entry.getValue().getTaskName());
		}
	}
	
	/**
	 * 
	 */
	public synchronized void addDependencie(){
		if(_scheduledTasksCombo.getSelectionIndex()>=0){
			String taskId=_scheduledTasksCombo.getItem(_scheduledTasksCombo.getSelectionIndex());
			if(_scheduledTasks.containsKey(taskId)){
				ScheduledTask task=_scheduledTasks.get(taskId);
				if(!_dependencies.containsKey(taskId)){
					_dependencies.put(taskId, task);
					updateDialog();
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public synchronized void removeDependencie(){
		if(_dependenciesTable.getSelectionIndex()>=0){
			TableItem selItem=_dependenciesTable.getItem(_dependenciesTable.getSelectionIndex());
			String taskId=selItem.getText(0);
			if(_dependencies.containsKey(taskId)){
				if(!_scheduledTasks.containsKey(taskId)){
					ScheduledTask task=_dependencies.get(taskId);
					_scheduledTasks.put(taskId, task);
					_dependencies.remove(taskId);
					updateDialog();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param listener
	 * @return
	 * @throws NullArgumentException
	 */
	public synchronized boolean addListener(ITaskDialogListener listener)throws NullArgumentException{
		if(listener==null){
			throw new NullArgumentException("Wrong argument listener==null");
		}
		
		if(_listeners.contains(listener)){
			return false;
		}
		
		_listeners.add(listener);
		return true;
	}

}
