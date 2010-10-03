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
public class JoinGameResponse extends AbstractResponse {
	/**
	 * 
	 */
	private GameStory _gameStory;
	/**
	 *
	 */
	private String _responseString;

	
	/**
	 *
	 */
    public JoinGameResponse()
    {
    	_responseString=null;
    }

    
	/**
	 *
	 * @param responseString
	 * @throws NullArgumentException
	 */
	public JoinGameResponse(GameStory gameStory, String responseString)throws NullArgumentException
	{
		if(responseString==null)
		{
			throw new NullArgumentException("Wrong argument responseString==null");
		}
		
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameStory=gameStory;
		_responseString=responseString;
	}

	/**
	 *
	 * @return
	 */
	public String getResponseString()
	{
		return _responseString;
	}

	
	/**
	 *
	 * @param responseString
	 * @throws NullArgumentException
	 */
	public void SetResponseString(String responseString)throws NullArgumentException
	{
		if(responseString==null)
		{
			throw new NullArgumentException("Wrong argument responseString==null");
		}
		_responseString=responseString;
	}

	
	/**
	 * 
	 * @return
	 */
	public boolean isJoinSuccessed()
	{
		if(_responseString!=null)
		{
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public GameStory getGameStory()
	{
		return _gameStory;
	}
}
