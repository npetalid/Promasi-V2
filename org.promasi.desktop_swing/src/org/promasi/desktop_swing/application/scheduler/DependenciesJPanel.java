/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utils_swing.components.jlist.CheckBoxCellRenderer;
import org.promasi.utils_swing.components.jlist.CheckBoxListEntry;

/**
 * @author alekstheod
 *
 */
public class DependenciesJPanel extends JPanel implements ICompanyListener, IDepartmentListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JList<CheckBoxListEntry<EmployeeTaskMemento>> _tasksList;
	
	/**
	 * 
	 */
	private Map<String, CheckBoxListEntry<EmployeeTaskMemento>> _tasks;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	public DependenciesJPanel(IGame game){
		_tasksList = new JList<CheckBoxListEntry<EmployeeTaskMemento>>();
		_tasks= new TreeMap<String, CheckBoxListEntry<EmployeeTaskMemento>>();
		_tasksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		_tasksList.setCellRenderer(new CheckBoxCellRenderer<Object>());
		_tasksList.addMouseListener(new MouseListener() {
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
				CheckBoxListEntry<EmployeeTaskMemento> entry = _tasksList.getSelectedValue();
				if( entry != null ){
					entry.onClick();
					_tasksList.repaint();
				}
			}
		});
		
		setLayout(new BorderLayout());
		add(_tasksList, BorderLayout.CENTER);
		_lockObject = new ReentrantLock();
		setBorder(BorderFactory.createTitledBorder("Dependencies"));
		game.addCompanyListener(this);
		game.addDepartmentListener(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getSelectedDependencies(){
		List<String> result = new LinkedList<String>();
		
		try{
			_lockObject.lock();
			for( Map.Entry<String, CheckBoxListEntry<EmployeeTaskMemento>> entry : _tasks.entrySet()){
				if( entry.getValue().isSelected() ){
					result.add(entry.getKey());
				}
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
	}

	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		if( 	company != null && 
				company.getITDepartment() != null && 
				company.getITDepartment().getEmployees() != null){
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					try{
						_lockObject.lock();
						
						boolean newTasksFound = false;
						Map<String, EmployeeTaskMemento> scheduledTasks = new TreeMap<String, EmployeeTaskMemento>();
						for( Map.Entry<String, EmployeeMemento> employeeEntry : company.getITDepartment().getEmployees().entrySet() ){
							if( employeeEntry.getValue() != null ){
								for( Map.Entry<String, EmployeeTaskMemento> taskEntry : employeeEntry.getValue().getTasks().entrySet()){
									if( !scheduledTasks.containsKey( taskEntry.getValue().getTaskName() ) ){
										scheduledTasks.put(taskEntry.getKey(), taskEntry.getValue());
									}
								}
							}
						}
						
						for( Map.Entry<String, EmployeeTaskMemento> entry : scheduledTasks.entrySet() ){
							if(!_tasks.containsKey(entry.getKey())){
								_tasks.put(entry.getKey(), new CheckBoxListEntry<EmployeeTaskMemento>(entry.getValue(), entry.getKey()));
								newTasksFound = true;
							}
						}
						
						Map<String, CheckBoxListEntry<EmployeeTaskMemento>> tmp = new TreeMap<String, CheckBoxListEntry<EmployeeTaskMemento>>(_tasks);
						for(Map.Entry<String, CheckBoxListEntry<EmployeeTaskMemento>> entry : tmp.entrySet()){
							if( !scheduledTasks.containsKey(entry.getKey())){
								_tasks.remove(entry.getKey());
								newTasksFound = true;
							}
						}
						
						if( newTasksFound){
							Vector<CheckBoxListEntry<EmployeeTaskMemento>> tasks = new Vector<CheckBoxListEntry<EmployeeTaskMemento>>();
							for( Map.Entry<String,  CheckBoxListEntry<EmployeeTaskMemento>> entry : _tasks.entrySet()){
								tasks.add(entry.getValue());
							}
							
							_tasksList.setListData(tasks);
						}

					}finally{
						_lockObject.unlock();
					}
				}
			});
		}

	}

	@Override
	public void companyAssigned(String owner, CompanyMemento company) {
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department, DateTime dateTime) {
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department, DateTime dateTime) {
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}
}
