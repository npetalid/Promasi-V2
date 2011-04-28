/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.company.SerializableCompany;
import org.promasi.game.project.SerializableProject;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class ProjectAssignedRequest extends SerializableObject 
{
	/**
	 * 
	 */
	private SerializableProject _project;

	/**
	 * 
	 */
	private SerializableCompany _company;

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
	public ProjectAssignedRequest(SerializableCompany company, SerializableProject project, String dateTime){
		_project=project;
		_company=company;
		setDateTime(dateTime);
	}
	
	/**
	 * @return the project
	 */
	public SerializableProject getProject() {
		return _project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(SerializableProject project) {
		_project = project;
	}
	
	/**
	 * @return the company
	 */
	public SerializableCompany getCompany() {
		return _company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(SerializableCompany company) {
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
