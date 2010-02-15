/**
 *
 */
package org.promasi.protocol.request;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class GetGameStatsRequest
{
	private String _userId;

	private String _gameId;

	public void setUserId(String userId)throws NullArgumentException
	{
		if(userId==null)
		{
			throw new NullArgumentException("Wrong argument userId==null");
		}
		_userId=userId;
	}

	public String getUserId()
	{
		return _userId;
	}

	public void setGameId(String gameId)throws NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		_gameId=gameId;
	}

	public String getGameId()
	{
		return _gameId;
	}
}
