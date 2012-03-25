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
public class OnExecuteStepRequest extends Message
{
	/**
	 * 
	 */
	private ProjectMemento _project;
	
	/**
	 * 
	 */
	private String _dateTime;

	/**
	 * 
	 */
	private CompanyMemento _company;
	
	/**
	 * 
	 */
	public OnExecuteStepRequest(){}
	
	/**
	 * 
	 * @param project
	 * @param dateTime
	 */
	public OnExecuteStepRequest(ProjectMemento project, CompanyMemento company,  String dateTime){
		_project=project;
		_dateTime=dateTime;
		setCompany(company);
	}
	
	/**
	 * @param project the project to set
	 */
	public void setProject(ProjectMemento project) {
		_project = project;
	}

	/**
	 * @return the project
	 */
	public ProjectMemento getProject() {
		return _project;
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

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyMemento company) {
		_company = company;
	}

	/**
	 * @return the company
	 */
	public CompanyMemento getCompany() {
		return _company;
	}
}
