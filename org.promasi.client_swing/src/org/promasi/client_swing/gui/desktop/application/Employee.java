/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.company.EmployeeMemento;

/**
 * 
 * @author alekstheod
 * Represent the employee on GUI application.
 * This class needed in order to represent the
 * employee as CV by overriding the toString() method.
 */
public class Employee{
	
	/**
	 * Instance of {@link = EmployeeMemento} which represent
	 * the employee.
	 */
	private EmployeeMemento _employee;
	
	/**
	 * Constructor will initialize the object.
	 * @param employee Instance of {@link = EmployeeMemento} which represent
	 * the employee.
	 * @exception {@link = GuiException} will be thrown in case of
	 * initialization error.
	 */
	public Employee(EmployeeMemento employee )throws GuiException{
		if( employee == null || employee.getCurriculumVitae() == null ){
			throw new GuiException("Wrong argument employee == null");
		}
		
		_employee = employee;
	}
	
	@Override
	public String toString(){
		return _employee.getCurriculumVitae();
	}
	
	/**
	 * Will return the containing instance of {@link = EmployeeMemento}
	 * @return EmployeeMemento.
	 */
	public EmployeeMemento getEmployeeMemento(){
		return _employee;
	}
}
