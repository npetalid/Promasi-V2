/**
 *
 */
package org.promasi.network.protocol.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class SetGameValuesRequest extends AbstractRequest {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private HashMap<String,Double> _values;

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
	public HashMap<String,Double> getValues()
	{
		return _values;
	}

	/**
	 *
	 * @param values
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void setValues(HashMap<String,Double> values)throws NullArgumentException,IllegalArgumentException
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
