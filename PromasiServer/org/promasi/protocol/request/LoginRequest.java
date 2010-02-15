/**
 *
 */
package org.promasi.protocol.request;

import org.apache.commons.lang.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class LoginRequest implements IRequest
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

}
