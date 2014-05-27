/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.model.generated.ProjectModel;

/**
 * @author m1cRo
 *
 */
public class ProjectFinishedRequest extends Message
{
	/**
	 * 
	 */
	private ProjectModel _project;

	/**
	 * 
	 */
	public ProjectFinishedRequest(){}
	
	/**
	 * 
	 * @param project
	 */
	public ProjectFinishedRequest(ProjectModel project){
		_project=project;
	}
	
	/**
	 * @param project the project to set
	 */
	public void setProject(ProjectModel project) {
		_project = project;
	}

	/**
	 * @return the project
	 */
	public ProjectModel getProject() {
		return _project;
	}
}
