/**
 * 
 */
package org.promasi.desktop_swing.application.marketplace;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.desktop_swing.application.Employee;
import org.promasi.desktop_swing.application.EmployeesPanel;
import org.promasi.game.IGame;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.model.CompanyModel;
import org.promasi.game.model.DepartmentModel;
import org.promasi.game.model.EmployeeModel;
import org.promasi.game.model.ProjectModel;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 *
 */
public class HumanResourcesJPanel extends JPanel implements ICompanyListener, IDepartmentListener {
	
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
	private EmployeesPanel _employeesPanel;
	
	/**
	 * 
	 */
	public HumanResourcesJPanel( IGame game ) throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		setOpaque(false);
		setBackground(Colors.White.alpha(0f));
		_employeesPanel = new EmployeesPanel();
		
		setLayout(new BorderLayout());
		add(_employeesPanel, BorderLayout.CENTER);
		
		JPanel marketPlaceMenu = new JPanel();
		marketPlaceMenu.setLayout(new BorderLayout());
		marketPlaceMenu.setBackground(Colors.LightBlue.alpha(1f));
		
		JButton dischargeButton = new JButton("Discharge Employee");
		dischargeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Employee> employees =_employeesPanel.getSelectedEmployees();
				for( Object employee : employees){
					if( employee instanceof Employee){
						 _game.dischargeEmployee(((Employee)employee).getEmployeeMemento().getEmployeeId());
					}
				}
			}
		});
		
		marketPlaceMenu.add(dischargeButton, BorderLayout.EAST);
		
		add( marketPlaceMenu, BorderLayout.SOUTH );
		
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
	public void companyIsInsolvent(String owner, final CompanyModel company,
			ProjectModel assignedProject, DateTime dateTime) {
		if( company != null && company.getItDepartment() != null){
			updateEmployeeList(company.getItDepartment().getEmployees() );
		}
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
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					Vector<Employee> dataSet = new Vector<Employee>();
					
					for(DepartmentModel.Employees.Entry entry : employees.getEntry() ){
						if(entry.getValue() !=null && entry.getValue().getEmployeeId() != null){
							try {
								dataSet.add(new Employee(entry.getValue()));
							} catch (GuiException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					_employeesPanel.updateList(dataSet);
				}
			});

		}
	}
	
	@Override
	public void companyAssigned(final String owner, final CompanyModel company) {
		if( company != null && company.getItDepartment() != null){
			updateEmployeeList(company.getItDepartment().getEmployees() );
		}
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
}
