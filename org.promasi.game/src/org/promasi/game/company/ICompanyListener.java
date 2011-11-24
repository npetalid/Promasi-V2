/**
 * 
 */
package org.promasi.game.company;

import org.promasi.game.project.SerializableProject;

/**
 * @author m1cRo
 *
 */
public interface ICompanyListener {
	/**
	 * 
	 * @param project
	 */
	public void projectAssigned(final String owner, final SerializableCompany company, final SerializableProject project );
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished(final String owner, final SerializableCompany company, final SerializableProject project);
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final String owner, final SerializableCompany company, final SerializableProject assignedProject);

	/**
	 * 
	 * @param company
	 * @param assignedProject
	 */
	public void onExecuteWorkingStep(final String owner, final SerializableCompany company, final SerializableProject assignedProject);
}
