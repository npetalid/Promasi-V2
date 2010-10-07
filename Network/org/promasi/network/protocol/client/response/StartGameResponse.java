/**
 *
 */
package org.promasi.network.protocol.client.response;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.GameStory;

/**
 * @author m1cRo
 *
 */
public class StartGameResponse extends AbstractResponse
{
	/**
	 * 
	 */
	private GameStory _gameStory;
	
	/**
	 * 
	 */
	StartGameResponse()
	{
		_gameStory=new GameStory();
	}
	
	/**
	 * 
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public StartGameResponse(GameStory gameStory)throws NullArgumentException
	{
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameStory=gameStory;
	}
	
	/**
	 * 
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setGameStory(GameStory gameStory)throws NullArgumentException
	{
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameStory=gameStory;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized GameStory getGameStory()
	{
		return _gameStory;
	}
}
