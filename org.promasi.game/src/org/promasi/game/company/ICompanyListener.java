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
	public void projectAssigned( String owner, CompanyMemento company, ProjectMemento project );
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished( String owner, CompanyMemento company, ProjectMemento project);
	
	/**
	 * 
	 */
	public void companyIsInsolvent( String owner, CompanyMemento company, ProjectMemento assignedProject);

	/**
	 * 
	 * @param company
	 * @param assignedProject
	 */
	public void onExecuteWorkingStep( String owner, CompanyMemento company, ProjectMemento assignedProject);
}
