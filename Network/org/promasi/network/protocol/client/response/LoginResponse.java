/**
 *
 */
package org.promasi.network.protocol.client.response;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.ProjectManager;

/**
 * @author m1cRo
 *
 */
public class LoginResponse extends AbstractResponse
{
	/**
	 * 
	 */
	private ProjectManager _projectManager;

	/**
	 * 
	 */
	public LoginResponse()
	{
		_projectManager=new ProjectManager();
	}
	
	/**
	 *
	 * @param loginIsDone
	 */
	public LoginResponse(ProjectManager projectManager)
	{
		_projectManager=projectManager;
	}

	/**
	 *
	 * @param message
	 * @throws NullArgumentException
	 */
	public void setProjectManager(ProjectManager projectManager)
	{
		_projectManager=projectManager;
	}

	/**
	 *
	 * @return
	 */
	public ProjectManager getProjectManager()
	{
		return _projectManager;
	}
}
