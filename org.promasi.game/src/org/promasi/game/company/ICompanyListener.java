/**
 * 
 */
package org.promasi.game.company;

import org.joda.time.DateTime;
import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.ProjectModel;
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
	 * @param company instance of {@link = CompanyModel} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectModel} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void projectAssigned( String owner, CompanyModel company, ProjectModel project, DateTime dateTime );
	
	/**
	 * Will be called by in case if project finished.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyModel} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectModel} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void projectFinished( String owner, CompanyModel company, ProjectModel project, DateTime dateTime);
	
	/**
	 * Will be called by in case if the company
	 * is insolvent.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyModel} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectModel} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void companyIsInsolvent( String owner, CompanyModel company, ProjectModel assignedProject, DateTime dateTime);

	/**
	 * Will be called by in case of execution working step.
	 * @param owner Company owner id.
	 * @param company instance of {@link = CompanyModel} which
	 * represent the company.
	 * @param assignedProject instance of {@link = ProjectModel} which
	 * represent the assigned project.
	 * @param dateTime the current DateTime.
	 */
	public void onExecuteWorkingStep( String owner, CompanyModel company, ProjectModel assignedProject, DateTime dateTime);
	
	/**
	 * Will inform the listener about his registration.
	 * @param owner the company owner Id.
	 * @param company instance of {@link = CompanyModel} which
	 * represent the company.
	 */
	public void companyAssigned ( String owner, CompanyModel company );
}
