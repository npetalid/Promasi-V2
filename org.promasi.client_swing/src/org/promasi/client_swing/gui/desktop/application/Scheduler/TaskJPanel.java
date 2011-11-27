/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.components.HtmlCellRenderer;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.Employee;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.game.project.ProjectTaskMemento;

/**
 * @author alekstheod
 *
 */
public class TaskJPanel extends JPanel implements ICompanyListener ,IDepartmentListener{

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
	private JList _employeesList;
	
	/**
	 * 
	 */
	private JList _projectTasks;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
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
	
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.SOUTH);
		
		JButton createTaskButton = new JButton("Add Task");
		controlPanel.add(createTaskButton, BorderLayout.EAST);
		
		
		JButton backButton = new JButton("Back");
		controlPanel.add(backButton, BorderLayout.WEST);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_application.setPanel(_prevPanel);
			}
		});
				
		JTabbedPane tabbedPane = new JTabbedPane();
		add(tabbedPane);
		
		JPanel schedulerPanel = new JPanel();
		tabbedPane.addTab("Scheduler", schedulerPanel);
		
		JPanel hrPanel = new JPanel();
		hrPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("HR", hrPanel);
		_employeesList = new JList();
		JScrollPane scrollPane = new JScrollPane(_employeesList);
		_employeesList.setCellRenderer(new HtmlCellRenderer());
		_employeesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		hrPanel.add(scrollPane, BorderLayout.CENTER);
		
		_projectTasks = new JList();
		
		add( tabbedPane, BorderLayout.CENTER);
		_game.addDepartmentListener(this);
	}

	@Override
	public void employeeDischarged(String director, final DepartmentMemento department) {
		try{
			_lockObject.lock();
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					updateDepartment(department);
				}
			});
			
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void employeeHired(String director, final DepartmentMemento department) {
		try{
			_lockObject.lock();
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					updateDepartment(department);
				}
			});
			
		}finally{
			_lockObject.unlock();
		}

	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					_application.setPanel(_prevPanel);
				}
			});
		} catch (InterruptedException e) {
			// TODO log
		} catch (InvocationTargetException e) {
			// TODO log
		}
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		// TODO log
	}

	/**
	 * 
	 * @param company
	 */
	private boolean updateDepartment( DepartmentMemento department ){
		boolean result = false;
		try{
			_lockObject.lock();
			if( department != null  && department.getEmployees() != null ){
				Vector<Employee> employees = new Vector<Employee>();
				for( Map.Entry<String, EmployeeMemento> entry : department.getEmployees().entrySet()){
					employees.add(new Employee(entry.getValue()));
				}
				
				_employeesList.setListData(employees);
				result = true;
			}
		}catch(GuiException e){
			result = false;
		}finally{
			_lockObject.unlock();
		}

		return result;
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company, final ProjectMemento project, DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if( project != null && project.getProjectTasks() != null ){
					Vector<String> tasks = new Vector<String>();
					for(Map.Entry<String, ProjectTaskMemento> entry : project.getProjectTasks().entrySet() ){
						tasks.add(entry.getValue().getName());
					}
					
					_projectTasks.setListData(tasks);
				}
			}
		});
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}
}
