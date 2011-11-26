/**
 * 
 */
package org.promasi.game.company;

import org.joda.time.DateTime;
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
	public void projectAssigned( String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime );
	
	/**
	 * 
	 * @param project
	 */
	public void projectFinished( String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime);
	
	/**
	 * 
	 */
	public void companyIsInsolvent( String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime);

	/**
	 * 
	 * @param company
	 * @param assignedProject
	 */
	public void onExecuteWorkingStep( String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime);
}
