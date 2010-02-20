/**
 *
 */
package org.promasi.server.core.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class GameList
{
	/**
	 * List of games.
	 */
	private Map<String,Game> _gameList;

	/**
	 * Default constructor.
	 */
	public GameList()
	{
		_gameList=new HashMap<String,Game>();
	}

	/**
	 *
	 * @param gameList
	 * @throws NullArgumentException
	 */
	public void setGameList(Map<String,Game> gameList)throws NullArgumentException
	{
		synchronized(this)
		{
			if(gameList==null)
			{
				throw new NullArgumentException("Wrong argument gameList==null");
			}
			_gameList=gameList;
		}
	}

	/**
	 *
	 * @return
	 */
	public Map<String,Game> getGameList()
	{
		synchronized(this)
		{
			return _gameList;
		}
	}

	/**
	 *
	 * @param gameId
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Game getGame(String gameId)throws IllegalArgumentException
	{
		synchronized(this)
		{
			if(!_gameList.containsKey(gameId))
			{
				throw new IllegalArgumentException("Wrong argument gameId == " + gameId + " is not in game list");
			}
			else
			{
				return _gameList.get(gameId);
			}
		}
	}

	/**
	 *
	 * @param gameId
	 * @param game
	 * @throws IllegalArgumentException
	 */
	public void addNewGame(Game game)throws IllegalArgumentException
	{
		synchronized(this)
		{
			if(_gameList.containsKey(game.getGameId()))
			{
				throw new IllegalArgumentException("Wrong argument gameId == " + game.getGameId() + " is already in list");
			}
			_gameList.put(game.getGameId(),game);
		}
	}

	/**
	 *
	 * @return
	 */
	public LinkedList<String> retreiveGames()
	{
		LinkedList<String> result=new LinkedList<String>();
		synchronized(this)
		{
			for(Game game : _gameList.values())
			{
				result.push(game.getGameId());
			}
		}
		return result;
	}
}
