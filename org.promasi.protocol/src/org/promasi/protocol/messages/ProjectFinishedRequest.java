/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.project.SerializableProject;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class ProjectFinishedRequest extends SerializableObject
{
	/**
	 * 
	 */
	private SerializableProject _project;

	/**
	 * 
	 */
	public ProjectFinishedRequest(){}
	
	/**
	 * 
	 * @param project
	 */
	public ProjectFinishedRequest(SerializableProject project){
		_project=project;
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
}
