/**
 *
 */
package org.promasi.multiplayer.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.multiplayer.GameStory;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.shell.ui.playmode.StoriesPool;
import org.promasi.shell.ui.playmode.Story;


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
	public synchronized LinkedList<GameStory> retreiveGames()
	{

		
		LinkedList<GameStory> result=new LinkedList<GameStory>();
		for(Game game : _gameList.values())
		{
			GameStory gameStory=game.getGameStory();
			result.add(gameStory);
		}

		List<Story> stories=StoriesPool.getAllStories();
		for(Story story : stories)
		{
			Company newCompany=story.getCompany();
			newCompany.setAccountant(story.getAccountant());
			newCompany.setBoss(story.getBoss());
			newCompany.setAdministrator(story.getAdministrator());
			GameStory gameStory=new GameStory(story.getCompany(),story.getName(),story.getName(),0,story.getMarketPlace());
			result.add(gameStory);
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
			//ToDo add user.
		_players.put(client, game);
		
		return true;
	}
}
