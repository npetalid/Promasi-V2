/**
 * 
 */
package org.promasi.desktop_swing.application.marketplace;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.joda.time.DateTime;
import org.promasi.desktop_swing.application.Employee;
import org.promasi.desktop_swing.application.scheduler.EmployeeCellRenderer;
import org.promasi.game.IGame;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.DepartmentMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.ICompanyListener;
import org.promasi.game.company.IDepartmentListener;
import org.promasi.game.project.ProjectMemento;
import org.promasi.utils_swing.AlphaContainer;
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
	private JList<Employee> _employeesList;
	
	/**
	 * 
	 */
	public HumanResourcesJPanel( IGame game ) throws GuiException{
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		setOpaque(false);
		setBackground(Colors.White.alpha(0f));
		_employeesList = new JList<Employee>();
		JScrollPane scrollPane = new JScrollPane(_employeesList);
		_employeesList.setCellRenderer(new EmployeeCellRenderer());
		
		setLayout(new BorderLayout());
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		add(new AlphaContainer(scrollPane), BorderLayout.CENTER);
		
		JPanel marketPlaceMenu = new JPanel();
		marketPlaceMenu.setLayout(new BorderLayout());
		
		JButton dischargeButton = new JButton("Discharge Employee");
		dischargeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if( !_employeesList.isSelectionEmpty() ){
					List<Employee> employees =_employeesList.getSelectedValuesList();
					for( Object employee : employees){
						if( employee instanceof Employee){
							 _game.dischargeEmployee(((Employee)employee).getEmployeeMemento().getEmployeeId());
						}
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
	public void projectAssigned(String owner, CompanyMemento company,
			ProjectMemento project, DateTime dateTime) {
		// TODO Auto-generated method stub
		
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

	/**
	 * 
	 * @param employees
	 */
	private void updateEmployeeList( final Map<String, EmployeeMemento> employees ){
		if( employees != null ){
			Vector<Employee> dataSet = new Vector<Employee>();
			for(Map.Entry<String,EmployeeMemento> entry : employees.entrySet() ){
				if( entry.getValue() !=null && entry.getValue().getEmployeeId() != null ){
					try {
						dataSet.add(new Employee(entry.getValue()));
					} catch (GuiException e) {
						// TODO log
					}
				}
			}
			
			_employeesList.setListData(dataSet);
		}
	}
	
	@Override
	public void companyAssigned(final String owner, final CompanyMemento company) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if( company != null && company.getITDepartment() != null){
					updateEmployeeList(company.getITDepartment().getEmployees() );
				}
			}
		});
	}

	@Override
	public void employeeDischarged(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void employeeHired(String director, DepartmentMemento department, EmployeeMemento employee, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void tasksAssigned(String director, DepartmentMemento department, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void tasksAssignFailed(String director, DepartmentMemento department, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}

	@Override
	public void departmentAssigned(String director, DepartmentMemento department, DateTime dateTime) {
		if( department != null ){
			updateEmployeeList(department.getEmployees());
		}
	}
}
