/**
 *
 */
package org.promasi.multiplayer.game;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;

/**
 * @author m1cRo
 *
 */
public class Game
{
	/**
	 *
	 */
	private String _gameId;

	/**
	 *
	 */
	private ProMaSiClient _gameMaster;

	/**
	 *
	 */
	private Map<ProMaSiClient,GameModel> _gameModels;

	/**
	 * 
	 */
	private String _gameModel;

	/**
	 *
	 * @param gameId
	 * @param gameMaster
	 * @param promasiModel
	 * @throws NullArgumentException
	 */
	public Game(String gameId,ProMaSiClient gameMaster,String gameModel)throws NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}

		if(gameMaster==null)
		{
			throw new NullArgumentException("Wrong argument gameMaster==null");
		}

		if(gameModel==null)
		{
			throw new NullArgumentException("Wrong argument promasiModel==null");
		}
		
		_gameId=gameId;
		_gameMaster=gameMaster;
		_gameModels=new HashMap<ProMaSiClient,GameModel>();
		_gameModel=gameModel;
		_gameModels.put(gameMaster, new GameModel(gameModel));
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
	 * @param playerId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void addPlayer(ProMaSiClient player)throws NullArgumentException,IllegalArgumentException
	{
		if(player==null)
		{
			throw new NullArgumentException("Wrong argument playerId==null");
		}
		
		synchronized(this)
		{
			if(_gameModels.containsKey(player) || _gameMaster.getClientId()==player.getClientId())
			{
				throw new IllegalArgumentException("Wrong argument playerId is already in game");
			}
			
			_gameModels.put(player, new GameModel(_gameModel)); //ToDo change GameModel.
		}
	}

	/**
	 *
	 * @return
	 */
	public ProMaSiClient getGameMaster()
	{
		return _gameMaster;
	}

	/**
	 *
	 * @param client
	 * @return
	 */
	public boolean isGameMaster(ProMaSiClient client)
	{
		return client==_gameMaster;
	}

	/**
	 *
	 * @param message
	 * @return
	 */
	public boolean startGame(String message)
	{
		synchronized(this)
		{
			if(_gameModels.size()<2)
			{
				return false;
			}
			
			for(Map.Entry<ProMaSiClient,GameModel> entry:_gameModels.entrySet())
			{
				entry.getKey().onReceiveData(message);
			}
			
			
		}
		
		return true;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public HashMap<String,Double> setGameValues(HashMap<String,Double> values)
	{
		synchronized(this)
		{
			//ToDo
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public HashMap<String,Double> getGameValues()
	{
		return null;
	}
}
