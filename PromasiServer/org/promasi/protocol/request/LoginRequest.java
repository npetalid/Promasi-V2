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
public class LoginRequest implements Serializable,IRequest
{
	public String _userName;

	public String _password;

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

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserName()
	{
		return _userName;
	}

	public String getPassword()
	{
		return _password;
	}

	public void setUserName(String userName)
	{
		if(userName==null)
		{
			throw new NullArgumentException("Wrong argument userName==null");
		}
		_userName=userName;
	}

	public void setPassword(String password)
	{
		if(password==null)
		{
			throw new NullArgumentException("Wrong argument password==null");
		}
		_password=password;
	}
}
