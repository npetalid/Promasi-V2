/**
 *
 */
package org.promasi.protocol.response;

/**
 * @author m1cRo
 *
 */
public class CreateNewGameResponse extends AbstractResponse {

	/**
	 *
	 */
	private boolean _result;

	/**
	 *
	 * @param result
	 */
	public CreateNewGameResponse(boolean result)
	{
		_result=result;
	}

	/**
	 *
	 * @param result
	 */
	public void SetResult(boolean result)
	{
		_result=result;
	}

	/**
	 *
	 * @return
	 */
	public boolean GetResult()
	{
		return _result;
	}
}
