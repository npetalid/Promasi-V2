/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.Scheduler;

import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.client_swing.components.JList.CheckBoxCellRenderer;
import org.promasi.client_swing.components.JList.CheckBoxListEntry;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;

/**
 * @author alekstheod
 *
 */
public class ScheduledTasksJPanel extends JPanel implements ICompanyListener, IDepartmentListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JList _tasksList;
	
	/**
	 * 
	 */
	private boolean _needToUpdate;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	public ScheduledTasksJPanel(IGame game){
		_tasksList = new JList();
		_tasksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		_tasksList.setCellRenderer(new CheckBoxCellRenderer());
		setLayout(new BorderLayout());
		add(_tasksList, BorderLayout.CENTER);
		_lockObject = new ReentrantLock();
		setBorder(BorderFactory.createTitledBorder("Running tasks"));
		_needToUpdate = false;
		game.addCompanyListener(this);
		game.addDepartmentListener(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getRunningTasks(){
		List<String> result = new LinkedList<String>();
		
		try{
			_lockObject.lock();
			
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void projectFinished(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void onExecuteWorkingStep(String owner, final CompanyMemento company,
			ProjectMemento assignedProject, DateTime dateTime) {
		if( 	company != null && 
				company.getITDepartment() != null && 
				company.getITDepartment().getEmployees() != null && 
				!company.getITDepartment().getEmployees().isEmpty()){
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					try{
						_lockObject.lock();
						if( _needToUpdate ){
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
							
							List<CheckBoxListEntry> tasks = new LinkedList<CheckBoxListEntry>();
							for( Map.Entry<String, EmployeeTaskMemento> entry : scheduledTasks.entrySet()){
								tasks.add(new CheckBoxListEntry(entry.getValue(), entry.getKey()));
							}
							
							_tasksList.setListData(tasks.toArray());
							_needToUpdate= false;
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
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department) {
		try{
			_lockObject.lock();
			_needToUpdate = true;
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department) {
		// TODO Auto-generated method stub
		
	}
}
