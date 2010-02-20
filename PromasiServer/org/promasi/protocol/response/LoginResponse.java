/**
 *
 */
package org.promasi.protocol.response;

import java.io.Serializable;

/**
 * @author m1cRo
 *
 */
public class LoginResponse extends AbstractResponse implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private boolean _loginIsDone;

	/**
	 *
	 */
	public LoginResponse()
	{
		_loginIsDone=false;
	}

	/**
	 *
	 * @param loginIsDone
	 */
	public LoginResponse(boolean loginIsDone)
	{
		_loginIsDone=loginIsDone;
	}

	/**
	 *
	 * @return
	 */
	public boolean isLoginComplete()
	{
		return _loginIsDone;
	}

	/**
	 *
	 * @param loginIsDone
	 */
	public void loginComplete(boolean loginIsDone)
	{
		_loginIsDone=loginIsDone;
	}
}
