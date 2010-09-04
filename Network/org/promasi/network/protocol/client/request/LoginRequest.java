/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class LoginRequest  extends AbstractRequest
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	public String _fistName;

	/**
	 *
	 */
	public String _lastname;

	/**
	 * Default constructor.
	 */
	public LoginRequest()
	{
		_fistName="FirstName";
		_lastname="LastName";
	}
	/**
	 *
	 * @param firstName
	 * @param lastName
	 * @throws NullArgumentException
	 */
	public LoginRequest(String firstName,String lastName)throws NullArgumentException
	{
		if(firstName==null)
		{
			throw new NullArgumentException("Wrong argument userName==null");
		}

		if(lastName==null)
		{
			throw new NullArgumentException("Wrong argument userName==null");
		}
		
		_fistName=firstName;
		_lastname=lastName;
	}

	/**
	 * UserName getter.
	 * @return
	 */
	public String getFistName()
	{
		return _fistName;
	}

	/**
	 * Password getter.
	 * @return
	 */
	public String getLastName()
	{
		return _lastname;
	}

	/**
	 *
	 * @param firstName
	 * @throws NullArgumentException
	 */
	public void setFirstName(String firstName)throws NullArgumentException,IllegalArgumentException
	{
		if(firstName==null)
		{
			throw new NullArgumentException("Wrong argument userName==null");
		}

		if(firstName.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument userName.isEmpty()");
		}
		_fistName=firstName;
	}

	/**
	 *
	 * @param lastName
	 * @throws NullArgumentException
	 */
	public void setLastName(String lastName)throws NullArgumentException
	{
		if(lastName==null)
		{
			throw new NullArgumentException("Wrong argument password==null");
		}

		_lastname=lastName;
	}
}
