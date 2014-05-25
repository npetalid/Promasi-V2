package org.promasi.ui.exportwizard.resources;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author antoxron
 *
 */
public class Employees {
	
	
	private List<EmployeeModel> _employees;
	
	public Employees() {
		_employees = new ArrayList<EmployeeModel>();
	}
	
	
	public void setEmployees(List<EmployeeModel> employees) {
		_employees = employees;
	}
	public List<EmployeeModel> getEmployees() {
		return _employees;
	}
	public void addEmployee(EmployeeModel employee) {
		_employees.add(employee);
	}

}
