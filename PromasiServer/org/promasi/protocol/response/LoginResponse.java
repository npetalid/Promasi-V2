/**
 *
 */
package org.promasi.protocol.response;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;

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
	private String _message;

	/**
	 *
	 */
	public LoginResponse()
	{
		_loginIsDone=false;
		_message="Login failed - Wrong username or password";
	}

	/**
	 *
	 * @param loginIsDone
	 */
	public LoginResponse(boolean loginIsDone,String message)throws NullArgumentException
	{
		if(message==null)
		{
			throw new NullArgumentException("Wrong argument message==null");
		}
		_loginIsDone=loginIsDone;
		_message=message;
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

	/**
	 *
	 * @param message
	 * @throws NullArgumentException
	 */
	public void SetMessage(String message)throws NullArgumentException
	{
		if(message==null)
		{
			throw new NullArgumentException("Wrong argument message==null");
		}
		_message=message;
	}

	/**
	 *
	 * @return
	 */
	public String GetMessage()
	{
		return _message;
	}
}
