/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.shell.ui.Story.Story;

/**
 * @author m1cRo
 *
 */
public class CreateNewGameRequest extends AbstractRequest
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Story _gameStory;
    
	/**
	 * 
	 */
	public CreateNewGameRequest()
	{
		_gameStory=null;
	}
	
	/**
	 *
	 * @param gameId
	 * @param gameModel
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public CreateNewGameRequest(Story gameStory)throws NullArgumentException,IllegalArgumentException
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
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public synchronized void setGameStory(Story gameStory)throws NullArgumentException
	{
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameStory=gameStory;
	}
}
