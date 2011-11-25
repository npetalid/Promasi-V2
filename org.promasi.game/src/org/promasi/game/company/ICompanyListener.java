/**
 * 
 */
package org.promasi.game.company;

import org.promasi.game.project.ProjectMemento;

/**
 * @author m1cRo
 *
 */
public interface ICompanyListener {
	/**
	 * 
	 * @param project
	 */
	public void projectAssigned(final String owner, final CompanyMemento company, final ProjectMemento project );
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished(final String owner, final CompanyMemento company, final ProjectMemento project);
	
	/**
	 * 
	 */
	public void companyIsInsolvent(final String owner, final CompanyMemento company, final ProjectMemento assignedProject);

	/**
	 * 
	 * @param company
	 * @param assignedProject
	 */
	public void onExecuteWorkingStep(final String owner, final CompanyMemento company, final ProjectMemento assignedProject);
}
