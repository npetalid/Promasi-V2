/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.company.CompanyMemento;
import org.promasi.game.project.ProjectMemento;

/**
 * @author m1cRo
 *
 */
public class ProjectAssignedRequest extends Message 
{
	/**
	 * 
	 */
	private ProjectMemento _project;

	/**
	 * 
	 */
	private CompanyMemento _company;

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
	 * @param project
	 */
	public ProjectAssignedRequest(CompanyMemento company, ProjectMemento project, String dateTime){
		_project=project;
		_company=company;
		setDateTime(dateTime);
	}
	
	/**
	 * @return the project
	 */
	public ProjectMemento getProject() {
		return _project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(ProjectMemento project) {
		_project = project;
	}
	
	/**
	 * @return the company
	 */
	public CompanyMemento getCompany() {
		return _company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyMemento company) {
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
