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
public class StartGameRequest extends AbstractRequest
{

	/**
	 * 
	 */
	Story _gameStory;
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public StartGameRequest()
	{
		_gameStory=new Story();
	}
	
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public StartGameRequest(Story gameStory)throws NullArgumentException
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
			throw new NullArgumentException("Wrong argument gameStory");
		}
		
		_gameStory=gameStory;
	}

}
