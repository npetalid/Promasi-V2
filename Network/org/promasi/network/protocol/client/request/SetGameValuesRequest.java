/**
 *
 */
package org.promasi.network.protocol.client.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class SetGameValuesRequest extends AbstractRequest 
{
	/**
	 *
	 */
	private Map<String,Double> _values;

	public SetGameValuesRequest()
	{
		_values=new HashMap<String, Double>();
	}
	/**
	 *
	 * @param values
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public SetGameValuesRequest(HashMap<String,Double> values)throws NullArgumentException,IllegalArgumentException
	{
		if(values==null)
		{
			throw new NullArgumentException("Wrong argument values==null");
		}

		if(values.size()==0)
		{
			throw new IllegalArgumentException("Wronga argument values.size()==0");
		}

		_values=values;
	}

	/**
	 *
	 * @return
	 */
	public synchronized Map<String,Double> getValues()
	{
		return _values;
	}

	/**
	 *
	 * @param values
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setValues(HashMap<String,Double> values)throws NullArgumentException,IllegalArgumentException
	{
		if(values==null)
		{
			throw new NullArgumentException("Wrong argument values==null");
		}

		if(values.size()==0)
		{
			throw new IllegalArgumentException("Wronga argument values.size()==0");
		}

		_values=values;
	}
}
