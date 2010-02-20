/**
 *
 */
package org.promasi.protocol.request;

import java.io.Serializable;
import org.apache.commons.lang.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class LoginRequest  extends AbstractRequest  implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public String _userName;

	/**
	 *
	 */
	public String _password;

	/**
	 * Default constructor.
	 */
	public LoginRequest()
	{
		_userName="username";
		_password="password";
	}
	/**
	 *
	 * @param userName
	 * @param password
	 * @throws NullArgumentException
	 */
	public LoginRequest(String userName,String password)throws NullArgumentException
	{
		if(userName==null)
		{
			throw new NullArgumentException("Wrong argument userName==null");
		}

		if(password==null)
		{
			throw new NullArgumentException("Wrong argument password==null");
		}
		_userName=userName;
		_password=password;
	}

	/**
	 * UserName getter.
	 * @return
	 */
	public String getUserName()
	{
		return _userName;
	}

	/**
	 * Password getter.
	 * @return
	 */
	public String getPassword()
	{
		return _password;
	}

	/**
	 * UserName setter.
	 * @param userName
	 */
	public void setUserName(String userName)
	{
		if(userName==null)
		{
			throw new NullArgumentException("Wrong argument userName==null");
		}
		_userName=userName;
	}

	/**
	 * Password setter.
	 * @param password
	 */
	public void setPassword(String password)
	{
		if(password==null)
		{
			throw new NullArgumentException("Wrong argument password==null");
		}
		_password=password;
	}
}
