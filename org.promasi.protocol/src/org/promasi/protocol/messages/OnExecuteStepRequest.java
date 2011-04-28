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
public class OnExecuteStepRequest extends SerializableObject
{
	/**
	 * 
	 */
	private SerializableProject _project;
	
	/**
	 * 
	 */
	private String _dateTime;

	/**
	 * 
	 */
	private SerializableCompany _company;
	
	/**
	 * 
	 */
	public OnExecuteStepRequest(){}
	
	/**
	 * 
	 * @param project
	 * @param dateTime
	 */
	public OnExecuteStepRequest(SerializableProject project, SerializableCompany company,  String dateTime){
		_project=project;
		_dateTime=dateTime;
		setCompany(company);
	}
	
	/**
	 * @param project the project to set
	 */
	public void setProject(SerializableProject project) {
		_project = project;
	}

	/**
	 * @return the project
	 */
	public SerializableProject getProject() {
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
	public void setCompany(SerializableCompany company) {
		_company = company;
	}

	/**
	 * @return the company
	 */
	public SerializableCompany getCompany() {
		return _company;
	}
}
