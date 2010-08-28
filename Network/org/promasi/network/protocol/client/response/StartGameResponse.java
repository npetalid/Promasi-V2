/**
 *
 */
package org.promasi.network.protocol.client.response;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class StartGameResponse extends AbstractResponse
{
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
	public StartGameResponse(String gameId)throws NullArgumentException,IllegalArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		if(gameId.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument gameId.isEmtpy()");
		}
		_gameId=gameId;
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
			throw new IllegalArgumentException("Wrong argument gameId.isEmtpy()");
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
