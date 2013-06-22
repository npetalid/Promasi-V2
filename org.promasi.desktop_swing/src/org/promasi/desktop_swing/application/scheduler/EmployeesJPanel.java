/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.desktop_swing.application.Employee;
import org.promasi.desktop_swing.application.EmployeeCellRenderer;
import org.promasi.game.IGame;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.DepartmentModel;
import org.promasi.game.model.generated.EmployeeModel;
import org.promasi.game.model.generated.ProjectModel;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class EmployeesJPanel extends JPanel implements ICompanyListener, IDepartmentListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String CONST_SITENAME = "Human Resources";
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private JList<Employee> _employeesList;
	
	/**
	 * 
	 */
	public EmployeesJPanel( IGame game ) throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_employeesList = new JList<Employee>();
		JScrollPane scrollPane = new JScrollPane(_employeesList);
		
		_employeesList.setCellRenderer(new EmployeeCellRenderer());
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		
		_game = game;
		_game.addCompanyListener(this);
		_game.addDepartmentListener(this);
	}

	@Override
	public void projectAssigned(String owner, CompanyModel company,
			ProjectModel project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(String owner, CompanyModel company,
			ProjectModel project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String owner, CompanyModel company,
			ProjectModel assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String owner, CompanyModel company,
			ProjectModel assignedProject, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param employees
	 */
	private void updateEmployeeList( final DepartmentModel.Employees employees ){
		if( employees != null ){
			Vector<Employee> dataSet = new Vector<Employee>();
			for(DepartmentModel.Employees.Entry entry : employees.getEntry() ){
				if( entry.getValue() !=null && entry.getValue().getEmployeeId() != null ){
					try {
						dataSet.add(new Employee(entry.getValue()));
					} catch (GuiException e) {
						e.printStackTrace();
					}
				}
			}
			
			_employeesList.setListData(dataSet);
		}
	}
	
	@Override
	public void companyAssigned(final String owner, final CompanyModel company) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if( company != null && company.getItDepartment() != null){
					updateEmployeeList(company.getItDepartment().getEmployees() );
				}
			}
		});
	}

	@Override
	public void employeeDischarged(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void employeeHired(String director, DepartmentModel department, EmployeeModel employee, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void tasksAssigned(String director, DepartmentModel department, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentModel department, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void departmentAssigned(String director, DepartmentModel department, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}
	
	Map<String, EmployeeModel> getSelectedEmployees(){
		Map<String, EmployeeModel> result = new TreeMap<String, EmployeeModel>();
		List<Employee> employees =_employeesList.getSelectedValuesList();
		for( Object employee : employees){
			if( employee instanceof Employee ){
				Employee emp = (Employee)employee;
				result.put(emp.getEmployeeMemento().getEmployeeId(), emp.getEmployeeMemento());
			}
		}
		
		return result;
	}
}
