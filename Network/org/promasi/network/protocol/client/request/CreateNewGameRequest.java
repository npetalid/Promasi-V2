/**
 *
 */
package org.promasi.network.protocol.client.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.GameStory;
import org.promasi.multiplayer.game.GameModel;

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
	private GameStory _gameStory;
	
	/**
	 *
	 */
	private GameModel _gameModel;

	/**
	 *
	 */
	private String _gameId;

	/**
	 * 
	 */
	public CreateNewGameRequest()
	{
		_gameId=new String();
		_gameModel=new GameModel();
		_gameStory=new GameStory();
	}
	
	/**
	 *
	 * @param gameId
	 * @param gameModel
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public CreateNewGameRequest(String gameId,GameModel gameModel,GameStory gameStory)throws NullArgumentException,IllegalArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}

		if(gameId.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument gameId.isEmpty()");
		}

		if(gameModel==null)
		{
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameId=gameId;
		_gameModel=gameModel;
		_gameStory=gameStory;
	}

	/**
	 *
	 * @param gameId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void setGameId(String gameId)throws NullArgumentException,IllegalArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}

		if(gameId.isEmpty())
		{
			throw new IllegalArgumentException("Wrong argument gameId.isEmpty()");
		}
		_gameId=gameId;
	}

	/**
	 *
	 * @return
	 */
	public synchronized String getGameId()
	{
		return _gameId;
	}

	/**
	 *
	 * @return
	 */
	public synchronized GameModel getGameModel()
	{
		return _gameModel;
	}
	
	/**
	 * 
	 * @param gameModel
	 * @throws NullArgumentException
	 */
	public synchronized void setGameModel(GameModel gameModel)throws NullArgumentException
	{
		if(gameModel==null)
		{
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		_gameModel=gameModel;
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
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_gameStory=gameStory;
	}
}
