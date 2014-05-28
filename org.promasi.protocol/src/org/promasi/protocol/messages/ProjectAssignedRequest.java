/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.model.generated.CompanyModel;
import org.promasi.game.model.generated.ProjectModel;

/**
 * @author m1cRo
 *
 */
public class ProjectAssignedRequest extends Message 
{
	/**
	 * 
	 */
	private ProjectModel _project;

	/**
	 * 
	 */
	private CompanyModel _company;

	/**
	 * 
	 */
	private String _dateTime;
	
	/**
	 * 
	 */
	public ProjectAssignedRequest(){}
	
	/**
	 * 
     * @param company
	 * @param project
     * @param dateTime
	 */
	public ProjectAssignedRequest(CompanyModel company, ProjectModel project, String dateTime){
		_project=project;
		_company=company;
		_dateTime = dateTime;
	}
	
	/**
	 * @return the project
	 */
	public ProjectModel getProject() {
		return _project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(ProjectModel project) {
		_project = project;
	}
	
	/**
	 * @return the company
	 */
	public CompanyModel getCompany() {
		return _company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyModel company) {
		_company = company;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		_dateTime = dateTime;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return _dateTime;
	}
}
