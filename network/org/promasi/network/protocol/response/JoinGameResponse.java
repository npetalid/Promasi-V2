/**
 *
 */
package org.promasi.network.protocol.response;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class JoinGameResponse extends AbstractResponse {
	/**
	 *
	 */
	private String _responseString;

	/**
	 *
	 */
    public JoinGameResponse()
    {
    	_responseString=null;
    }

	/**
	 *
	 * @param responseString
	 * @throws NullArgumentException
	 */
	public JoinGameResponse(String responseString)throws NullArgumentException
	{
		if(responseString==null)
		{
			throw new NullArgumentException("Wrong argument responseString==null");
		}
		_responseString=responseString;
	}

	/**
	 *
	 * @return
	 */
	public String getResponseString()
	{
		return _responseString;
	}

	/**
	 *
	 * @param responseString
	 * @throws NullArgumentException
	 */
	public void SetResponseString(String responseString)throws NullArgumentException
	{
		if(responseString==null)
		{
			throw new NullArgumentException("Wrong argument responseString==null");
		}
		_responseString=responseString;
	}

	public boolean isJoinSuccessed()
	{
		if(_responseString!=null)
		{
			return false;
		}
		return true;
	}
}
