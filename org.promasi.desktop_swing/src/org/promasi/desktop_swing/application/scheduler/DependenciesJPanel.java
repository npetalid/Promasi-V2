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
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.DepartmentModel;
import org.promasi.game.model.generated.DepartmentModel.Employees.Entry;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.EmployeeTaskModel;
import org.promasi.game.model.generated.ProjectModel;
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
	private JList<CheckBoxListEntry<EmployeeTaskModel>> _tasksList;
	
	/**
	 * 
	 */
	private Map<String, CheckBoxListEntry<EmployeeTaskModel>> _tasks;
	
	/**
	 * 
	 */
	private Lock _lockObject;
	
	/**
	 * 
	 */
	public DependenciesJPanel(IGame game){
		_tasksList = new JList<CheckBoxListEntry<EmployeeTaskModel>>();
		_tasks= new TreeMap<String, CheckBoxListEntry<EmployeeTaskModel>>();
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
				CheckBoxListEntry<EmployeeTaskModel> entry = _tasksList.getSelectedValue();
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
			for( Map.Entry<String, CheckBoxListEntry<EmployeeTaskModel>> entry : _tasks.entrySet()){
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
	public void projectAssigned(String owner, CompanyModel company,
			ProjectModel project, DateTime dateTime) {
	}

	@Override
	public void projectFinished(String owner, CompanyModel company,
			ProjectModel project, DateTime dateTime) {
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyModel company,
			ProjectModel assignedProject, DateTime dateTime) {
	}

	@Override
	public void onExecuteWorkingStep(String owner, final CompanyModel company,
			ProjectModel assignedProject, DateTime dateTime) {
		if( 	company != null && 
				company.getItDepartment() != null && 
				company.getItDepartment().getEmployees() != null){
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					try{
						_lockObject.lock();
						
						boolean newTasksFound = false;
						Map<String, EmployeeTaskModel> scheduledTasks = new TreeMap<String, EmployeeTaskModel>();
						for( Entry entry : company.getItDepartment().getEmployees().getEntry() ){
							if( entry != null ){
								for( org.promasi.game.model.generated.EmployeeModel.Tasks.Entry taskEntry : entry.getValue().getTasks().getEntry()){
									if( !scheduledTasks.containsKey( taskEntry.getValue().getTaskName() ) ){
										scheduledTasks.put(taskEntry.getKey(), taskEntry.getValue());
									}
								}
							}
						}
						
						for( Map.Entry<String, EmployeeTaskModel> entry : scheduledTasks.entrySet() ){
							if(!_tasks.containsKey(entry.getKey())){
								_tasks.put(entry.getKey(), new CheckBoxListEntry<EmployeeTaskModel>(entry.getValue(), entry.getKey()));
								newTasksFound = true;
							}
						}
						
						Map<String, CheckBoxListEntry<EmployeeTaskModel>> tmp = new TreeMap<String, CheckBoxListEntry<EmployeeTaskModel>>(_tasks);
						for(Map.Entry<String, CheckBoxListEntry<EmployeeTaskModel>> entry : tmp.entrySet()){
							if( !scheduledTasks.containsKey(entry.getKey())){
								_tasks.remove(entry.getKey());
								newTasksFound = true;
							}
						}
						
						if( newTasksFound){
							Vector<CheckBoxListEntry<EmployeeTaskModel>> tasks = new Vector<CheckBoxListEntry<EmployeeTaskModel>>();
							for( Map.Entry<String,  CheckBoxListEntry<EmployeeTaskModel>> entry : _tasks.entrySet()){
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
	public void companyAssigned(String owner, CompanyModel company) {
	}

	@Override
	public void employeeDischarged(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {
	}

	@Override
	public void employeeHired(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {
	}

	@Override
	public void tasksAssigned(String director, DepartmentModel department, DateTime dateTime) {
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentModel department, DateTime dateTime) {
	}

	@Override
	public void departmentAssigned(String director, DepartmentModel department, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}
}
