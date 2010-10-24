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
public class CreateNewGameResponse extends AbstractResponse {

	/**
	 * 
	 */
	private Story _gameStory;
	
	/**
	 * 
	 */
	public CreateNewGameResponse()
	{
		_gameStory=new Story();
	}
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public CreateNewGameResponse(Story gameStory)throws NullArgumentException
	{
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameStory=gameStory;
	}
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public void setGameStory(Story gameStory)throws NullArgumentException
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
	public Story getGameStory()
	{
		return _gameStory;
	}
}
