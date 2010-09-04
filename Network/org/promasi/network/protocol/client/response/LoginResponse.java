/**
 *
 */
package org.promasi.network.protocol.client.response;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.ProjectManager;

/**
 * @author m1cRo
 *
 */
public class LoginResponse extends AbstractResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private ProjectManager _projectManager;

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
