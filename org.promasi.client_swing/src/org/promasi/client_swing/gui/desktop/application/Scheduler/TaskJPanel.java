/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

import sun.font.FontStrike;

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
	private JComboBox _projectTasks;
	
	/**
	 * 
	 */
	private JList _runningTasks;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	private JLabel _timeLabel;
	
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
		schedulerPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Scheduler", schedulerPanel);
		_runningTasks = new JList();
		schedulerPanel.add(_runningTasks, BorderLayout.CENTER);
		
		JPanel hrPanel = new JPanel();
		hrPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("HR", hrPanel);
		_employeesList = new JList();
		JScrollPane scrollPane = new JScrollPane(_employeesList);
		_employeesList.setCellRenderer(new HtmlCellRenderer());
		_employeesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		hrPanel.add(scrollPane, BorderLayout.CENTER);
		
		JPanel taskDesignPanel = new JPanel();
		taskDesignPanel.setLayout(new BorderLayout());
		
		JPanel timePanel = new JPanel();
		timePanel.setLayout(new GridLayout(4,1));
		
		_timeLabel = new JLabel("Time");
		_timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		_timeLabel.setPreferredSize(new Dimension(100, 50));
		timePanel.add(_timeLabel);
		schedulerPanel.add(timePanel, BorderLayout.EAST);
		
		_projectTasks = new JComboBox();
		schedulerPanel.add(_projectTasks, BorderLayout.NORTH);
		
		add( tabbedPane, BorderLayout.CENTER);
		
		JPanel scheduledTaskNamePanel = new JPanel();
		add(scheduledTaskNamePanel, BorderLayout.SOUTH);
		scheduledTaskNamePanel.setBorder(BorderFactory.createTitledBorder("Task Name"));
		
		_game.addDepartmentListener(this);
		_game.addCompanyListener(this);
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
				try{
					_lockObject.lock();
					if( project != null && project.getProjectTasks() != null ){
						_projectTasks.removeAll();
						for(Map.Entry<String, ProjectTaskMemento> entry : project.getProjectTasks().entrySet() ){
							_projectTasks.addItem(new ProjectTask(entry.getValue()));
						}
					}
				} catch (GuiException e) {
					//TODO log
				} finally {
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
					_projectTasks.removeAll();
				} finally {
					_lockObject.unlock();
				}

			}
		});
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			final ProjectMemento assignedProject, final DateTime dateTime) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try{
					_lockObject.lock();
					_timeLabel.setText(dateTime.toString());
				} finally {
					_lockObject.unlock();
				}

			}
		});
		
	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
		// TODO Auto-generated method stub
		
	}
}
