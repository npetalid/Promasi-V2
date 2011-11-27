/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.company.EmployeeMemento;

/**
 * 
 * @author alekstheod
 *
 */
public class Employee{
	
	/**
	 * 
	 */
	private EmployeeMemento _employee;
	
	/**
	 * 
	 * @param employee
	 */
	public Employee(EmployeeMemento employee )throws GuiException{
		if( employee == null ){
			throw new GuiException("Wrong argument employee == null");
		}
		
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
