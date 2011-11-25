/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.project.ProjectMemento;
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
	private ProjectMemento _project;

	/**
	 * 
	 */
	public ProjectFinishedRequest(){}
	
	/**
	 * 
	 * @param project
	 */
	public ProjectFinishedRequest(ProjectMemento project){
		_project=project;
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
}
