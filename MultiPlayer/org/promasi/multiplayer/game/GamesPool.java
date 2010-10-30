/**
 *
 */
package org.promasi.multiplayer.game;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;



/**
 * @author m1cRo
 *
 */
public class GamesPool
{
	/**
	 * List of games.
	 */
	private Map<String,Game> _gameList;

	/**
	 * 
	 */
	private Map<ProMaSiClient,Game> _players;

	
	/**
	 * Default constructor.
	 */
	public GamesPool()
	{
		_gameList=new HashMap<String,Game>();
		_players=new HashMap<ProMaSiClient,Game>();
	}

	
	/**
	 *
	 * @param gameList
	 * @throws NullArgumentException
	 */
	public synchronized void setGameList(Map<String,Game> gameList)throws NullArgumentException
	{
		if(gameList==null)
		{
			throw new NullArgumentException("Wrong argument gameList==null");
		}
		
		_gameList=gameList;
	}

	
	/**
	 *
	 * @return
	 */
	public synchronized Map<String,Game> getGameList()
	{
		return _gameList;
	}

	
	/**
	 *
	 * @param gameId
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NullArgumentException
	 */
	public synchronized Game getGame(String gameId)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId == null");
		}
		
		if(!_gameList.containsKey(gameId))
		{
			throw new IllegalArgumentException("Wrong argument gameId == " + gameId + " is not in game list");
		}
		else
		{
				return _gameList.get(gameId);
		}
	}

	
	/**
	 *
	 * @param gameId
	 * @param game
	 * @throws IllegalArgumentException
	 */
	public synchronized void addNewGame(String gameId,Game game)throws IllegalArgumentException
	{
		if(_gameList.containsKey(gameId))
		{
			throw new IllegalArgumentException("Wrong argument gameId == " + gameId + " is already in list");
		}
			
		_gameList.put(gameId,game);
	}

	
	/**
	 *
	 * @return
	 */
	public synchronized Map<String,String> retreiveGames()
	{
		Map<String,String>result=new TreeMap<String,String>();
		for(Game game : _gameList.values())
		{
			result.put(game.getName(), game.getDescription());
		}
		
		return result;
	}

	
	/**
	 *
	 * @param client
	 * @param gameId
	 * @return
	 */
	public synchronized boolean joinGame(ProMaSiClient client,String gameId)throws NullArgumentException
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
		
		if(!_gameList.containsKey(gameId))
		{
			return false;
		}

		Game game=_gameList.get(gameId);
		game.joinGame(client);
		_players.put(client, game);
		
		return true;
	}
}
