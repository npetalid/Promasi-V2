/**
 * 
 */
package org.promasi.game.company;

import org.joda.time.DateTime;
import org.promasi.game.project.ProjectMemento;

/**
 * @author m1cRo
 * Represent an interface of
 * an Company listener. Listener should be informed
 * for any major company state change.
 */
public interface ICompanyListener {
	/**
	 * Will be called in case if a new project was assigned to 
	 * the company.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyMemento} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectMemento} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void projectAssigned( String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime );
	
	/**
	 * Will be called by in case if project finished.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyMemento} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectMemento} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void projectFinished( String owner, CompanyMemento company, ProjectMemento project, DateTime dateTime);
	
	/**
	 * Will be called by in case if the company
	 * is insolvent.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyMemento} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectMemento} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void companyIsInsolvent( String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime);

	/**
	 * Will be called by in case of execution working step.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyMemento} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectMemento} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void onExecuteWorkingStep( String owner, CompanyMemento company, ProjectMemento assignedProject, DateTime dateTime);
	
	/**
	 * Will inform the listener about his registration.
	 * @param owner the company owner Id.
	 * @param company instance of {@link = CompanyMemento} which
	 * represent the company.
	 */
	public void companyAssigned ( String owner, CompanyMemento company );
}
