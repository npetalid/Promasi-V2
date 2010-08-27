/**
 *
 */
package org.promasi.server.core.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;


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

	private Map<ProMaSiClient,Game> _players;

	/**
	 * Default constructor.
	 */
	public GameList()
	{
		_gameList=new HashMap<String,Game>();
		_players=new HashMap<ProMaSiClient,Game>();
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
	 * @throws NullArgumentException
	 */
	public Game getGame(String gameId)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId == null");
		}
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

	/**
	 *
	 * @param client
	 * @param gameId
	 * @return
	 */
	public boolean joinGame(ProMaSiClient client,String gameId)throws NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}

		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		if(gameId.isEmpty())
		{
			return false;
		}

		if(_players.containsKey(client))
		{
			return false;
		}
		synchronized(this)
		{
			if(!_gameList.containsKey(gameId))
			{
				return false;
			}

			Game game=_gameList.get(gameId);
			//ToDo add user.
			_players.put(client, game);
		}
		return true;
	}
}
