/**
 *
 */
package org.promasi.protocol.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.core.GameModel;

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
	private GameModel _gameModel;

	/**
	 *
	 */
	private String _gameId;

	/**
	 *
	 * @param gameId
	 * @param gameModel
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public CreateNewGameRequest(String gameId,GameModel gameModel)throws NullArgumentException,IllegalArgumentException
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
		_gameId=gameId;
		_gameModel=gameModel;
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
			throw new IllegalArgumentException("Wrong argument gameId.isEmpty()");
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

	/**
	 *
	 * @return
	 */
	public GameModel getGameModel()
	{
		return _gameModel;
	}
}
