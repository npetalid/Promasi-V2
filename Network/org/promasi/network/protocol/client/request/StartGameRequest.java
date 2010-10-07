/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.GameStory;


/**
 * @author m1cRo
 *
 */
public class StartGameRequest extends AbstractRequest
{

	/**
	 * 
	 */
	GameStory _gameStory;
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public StartGameRequest()
	{
		_gameStory=new GameStory();
	}
	
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public StartGameRequest(GameStory gameStory)throws NullArgumentException
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
	
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public synchronized void setGameStory(GameStory gameStory)throws NullArgumentException
	{
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory");
		}
		
		_gameStory=gameStory;
	}

}
