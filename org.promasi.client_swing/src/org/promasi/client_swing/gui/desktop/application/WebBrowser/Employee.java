/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application.WebBrowser;

import org.promasi.game.company.EmployeeMemento;

/**
 * 
 * @author alekstheod
 *
 */
class Employee{
	
	private EmployeeMemento _employee;
	
	public Employee(EmployeeMemento employee ){ 
		_employee = employee;
	}
	
	@Override
	public String toString(){
		return _employee.getCurriculumVitae();
	}
	
	/**
	 * 
	 * @return
	 */
	public EmployeeMemento getEmployeeMemento(){
		return _employee;
	}
}
