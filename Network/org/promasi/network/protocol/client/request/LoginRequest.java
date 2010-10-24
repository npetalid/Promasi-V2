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
	public String _firstName;

	/**
	 *
	 */
	public String _lastName;

	/**
	 * Default constructor.
	 */
	public LoginRequest()
	{
		_firstName=new String("FirstName");
		_lastName=new String("LastName");
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
			throw new NullArgumentException("Wrong argument firstName==null");
		}

		if(lastName==null)
		{
			throw new NullArgumentException("Wrong argument lastName==null");
		}
		
		_firstName=firstName;
		_lastName=lastName;
	}

	/**
	 * UserName getter.
	 * @return
	 */
	public String getFirstName()
	{
		return _firstName;
	}

	/**
	 * LastName getter.
	 * @return
	 */
	public String getLastName()
	{
		return _lastName;
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
			throw new NullArgumentException("Wrong argument firstName==null");
		}
		
		_firstName=firstName;
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
			throw new NullArgumentException("Wrong argument lastName==null");
		}

		_lastName=lastName;
	}
}
