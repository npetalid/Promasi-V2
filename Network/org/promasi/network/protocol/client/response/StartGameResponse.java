/**
 *
 */
package org.promasi.network.protocol.client.response;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.shell.ui.Story.Story;

/**
 * @author m1cRo
 *
 */
public class StartGameResponse extends AbstractResponse
{
	/**
	 * 
	 */
	private Story _gameStory;
	
	/**
	 * 
	 */
	StartGameResponse()
	{
		_gameStory=new Story();
	}
	
	/**
	 * 
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public StartGameResponse(Story gameStory)throws NullArgumentException
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
	public synchronized void setGameStory(Story gameStory)throws NullArgumentException
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
	public synchronized Story getGameStory()
	{
		return _gameStory;
	}
}
