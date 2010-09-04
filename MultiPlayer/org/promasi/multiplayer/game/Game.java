/**
 *
 */
package org.promasi.multiplayer.game;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.dtos.GameDto;

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
	private String _gameModelString;
	
	/**
	 * 
	 */
	private GameModel _gameModel;
	
	/**
	 *
	 * @param gameId
	 * @param gameMaster
	 * @param promasiModel
	 * @throws NullArgumentException
	 */
	public Game(String gameId,ProMaSiClient gameMaster,String gameModelString)throws NullArgumentException,IllegalArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}

		if(gameMaster==null)
		{
			throw new NullArgumentException("Wrong argument gameMaster==null");
		}

		if(gameModelString==null)
		{
			throw new NullArgumentException("Wrong argument promasiModel==null");
		}
		
		_gameId=gameId;
		_gameMaster=gameMaster;
		_gameModels=new HashMap<ProMaSiClient,GameModel>();
		_gameModel=new GameModel(gameModelString);
		_gameModelString=gameModelString;
		_gameModels.put( gameMaster, new GameModel(gameModelString) );
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
	 * @param playerId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized void addPlayer(ProMaSiClient player)throws NullArgumentException,IllegalArgumentException
	{
		if(player==null)
		{
			throw new NullArgumentException("Wrong argument playerId==null");
		}

		if(_gameModels.containsKey(player) || _gameMaster.getClientId()==player.getClientId())
		{
			throw new IllegalArgumentException("Wrong argument playerId is already in game");
		}
			
		_gameModels.put(player, new GameModel(_gameModelString)); //ToDo change GameModel.
	}

	/**
	 *
	 * @return
	 */
	public synchronized ProMaSiClient getGameMaster()
	{
		return _gameMaster;
	}

	/**
	 *
	 * @param client
	 * @return
	 */
	public synchronized boolean isGameMaster(ProMaSiClient client)
	{
		return client==_gameMaster;
	}

	/**
	 *
	 * @param message
	 * @return
	 */
	public synchronized boolean startGame(String message)
	{
		if(_gameModels.size()<2)
		{
			return false;
		}
			
		for(Map.Entry<ProMaSiClient,GameModel> entry:_gameModels.entrySet())
		{
			entry.getKey().onReceiveData(message);
		}

		return true;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public synchronized HashMap<String,Double> setGameValues(HashMap<String,Double> values)
	{
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized HashMap<String,Double> getGameValues()
	{
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized GameDto getGameDto()
	{
		return new GameDto(_gameModel.getCompany(),_gameId,_gameModel.getGameDescription(),_gameModels.size());
	}
}
