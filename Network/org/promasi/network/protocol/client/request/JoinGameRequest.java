/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class JoinGameRequest extends AbstractRequest
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String _gameId;

	/**
	 *
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public JoinGameRequest(String gameId)throws NullArgumentException,IllegalArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		if(gameId.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument gameId.isEmpty()");
		}
		_gameId=gameId;
	}

	/**
	 *
	 * @return
	 */
	public String getGameId()
	{
		return _gameId;
	}

	/**
	 *
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void setGameId(String gameId)throws NullArgumentException,IllegalArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		if(gameId.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument gameId.isEmpty()");
		}
		_gameId=gameId;
	}
}
