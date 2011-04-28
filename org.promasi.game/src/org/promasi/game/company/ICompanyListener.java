/**
 * 
 */
package org.promasi.game.company;

import java.util.List;

import org.promasi.game.project.SerializableProject;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public interface ICompanyListener {
	/**
	 * 
	 * @param project
	 * @throws SerializationException 
	 */
	public void projectAssigned(final SerializableCompany company, final SerializableProject project) throws SerializationException;
	
	/**
	 * 
	 * @param project
	 * @throws SerializationException 
	 */
	public void projectFinished(final SerializableCompany company, final SerializableProject project) throws SerializationException;
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @throws SerializationException
	 */
	public void employeeHired(final SerializableCompany company, final SerializableEmployee employee) throws SerializationException;
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @throws SerializationException
	 */
	public void employeeDischarged(final SerializableCompany company, final SerializableEmployee employee) throws SerializationException;
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final SerializableCompany company, final SerializableProject assignedProject);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 */
	public void employeeTasksAttached(final SerializableCompany company, final SerializableEmployee employee, final List<SerializableEmployeeTask> employeeTasks);
	
	/**
	 * 
	 * @param company
	 * @param employee
	 * @param employeeTask
	 * @throws SerializationException 
	 */
	public void employeeTaskDetached(final SerializableCompany company, final SerializableEmployee employee, final SerializableEmployeeTask employeeTask) throws SerializationException;
	
	/**
	 * 
	 * @param company
	 * @param assignedProject
	 * @throws SerializationException 
	 */
	public void onExecuteWorkingStep(final SerializableCompany company, final SerializableProject assignedProject) throws SerializationException;
}
