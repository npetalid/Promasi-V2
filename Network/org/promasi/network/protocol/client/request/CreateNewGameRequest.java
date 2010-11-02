/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class CreateNewGameRequest extends AbstractRequest
{
	/**
	 * 
	 */
	private String _storyId;
    
	/**
	 * 
	 */
	private String _gameName;
	
	/**
	 * 
	 */
	public CreateNewGameRequest()
	{
		_storyId=new String();
		_gameName=new String();
	}
	
	/**
	 *
	 * @param gameId
	 * @param gameModel
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public CreateNewGameRequest(String storyId, String gameName)throws NullArgumentException,IllegalArgumentException
	{
		if(storyId==null)
		{
			throw new NullArgumentException("Wrong argument storyId==null");
		}
		
		if(gameName==null)
		{
			throw new NullArgumentException("Wrong argument gameName==null");
		}

		_storyId=storyId;
		_gameName=gameName;
	}

	/**
	 * 
	 * @return
	 */
	public synchronized String getStoryId()
	{
		return _storyId;
	}
	
	/**
	 * 
	 * @param gameStory
	 * @throws NullArgumentException
	 */
	public synchronized void setStoryId(String storyId)throws NullArgumentException
	{
		if(storyId==null)
		{
			throw new NullArgumentException("Wrong argument storyId==null");
		}
		
		_storyId=storyId;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getGameName()
	{
		return _gameName;
	}
	
	/**
	 * 
	 * @param gameName
	 * @throws NullArgumentException
	 */
	public synchronized void setGameName(String gameName)throws NullArgumentException
	{
		if(gameName==null)
		{
			throw new NullArgumentException("Wrong argument gameName==null");
		}
		
		_gameName=gameName;
	}
}
