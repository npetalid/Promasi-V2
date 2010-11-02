/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class GetGameStatsRequest extends AbstractRequest
{
	/**
	 *
	 */
	private String _userId;

	/**
	 *
	 */
	private String _gameId;

	/**
	 *
	 * @param userId
	 * @throws NullArgumentException
	 */
	public void setUserId(String userId)throws NullArgumentException
	{
		if(userId==null)
		{
			throw new NullArgumentException("Wrong argument userId==null");
		}
		_userId=userId;
	}

	/**
	 *
	 * @return
	 */
	public String getUserId()
	{
		return _userId;
	}

	/**
	 *
	 * @param gameId
	 * @throws NullArgumentException
	 */
	public void setGameId(String gameId)throws NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
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
}
