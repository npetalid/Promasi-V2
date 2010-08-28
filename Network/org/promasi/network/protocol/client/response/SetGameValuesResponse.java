/**
 *
 */
package org.promasi.network.protocol.client.response;

import java.util.HashMap;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class SetGameValuesResponse extends AbstractResponse {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private HashMap<String,Double> _invalidValues;

	/**
	 *
	 */
	public SetGameValuesResponse()
	{
		_invalidValues=null;
	}

	/**
	 *
	 * @param invalidValues
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public SetGameValuesResponse(HashMap<String,Double> invalidValues)throws NullArgumentException,IllegalArgumentException
	{
		if(invalidValues==null)
		{
			throw new NullArgumentException("Wrong argument values==null");
		}

		if(invalidValues.size()==0)
		{
			throw new IllegalArgumentException("Wronga argument values.size()==0");
		}
		_invalidValues=invalidValues;
	}

	/**
	 *
	 * @return
	 */
	public HashMap<String,Double> getInvalidValues()
	{
		return _invalidValues;
	}

	/**
	 *
	 * @param invalidValues
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void setInvalidValues(HashMap<String,Double> invalidValues)throws NullArgumentException,IllegalArgumentException
	{
		if(invalidValues==null)
		{
			throw new NullArgumentException("Wrong argument values==null");
		}

		if(invalidValues.size()==0)
		{
			throw new IllegalArgumentException("Wronga argument values.size()==0");
		}

		_invalidValues=invalidValues;
	}
}
