/**
 *
 */
package org.promasi.multiplayer.game;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.model.MarketPlace;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.StartGameRequest;
import org.promasi.shell.Story.Story;



/**
 * @author m1cRo
 *
 */
public class Game implements Runnable
{
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
	private GameModel _gameModel;
	
	/**
	 * 
	 */
	private Thread _gameThread;
	
	/**
	 * 
	 */
	private boolean _isRunning;
	
	/**
	 *
	 * @param gameId
	 * @param gameMaster
	 * @param promasiModel
	 * @throws NullArgumentException
	 */
	public Game(ProMaSiClient gameMaster,GameModel gameModel)throws NullArgumentException,IllegalArgumentException
	{
		if(gameMaster==null)
		{
			throw new NullArgumentException("Wrong argument gameMaster==null");
		}

		if(gameModel==null)
		{
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		_isRunning=false;
		_gameMaster=gameMaster;
		_gameModels=new HashMap<ProMaSiClient,GameModel>();
		_gameModel=gameModel;
	}

	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Company getCompany() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException
	{
		return _gameModel.getCompany();
	}
	
	/**
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public MarketPlace getMarketPlace() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException
	{
		return _gameModel.getMarketPlace();
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getName()
	{
		return _gameModel.getName();
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getDescription()
	{
		return _gameModel.getDescription();
	}
	
	/**
	 * @return 
	 * 
	 */
	public synchronized boolean isRunning()
	{
		return _isRunning;
	}
	
	/**
	 *
	 * @param playerId
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public synchronized GameModel joinGame(ProMaSiClient player)throws NullArgumentException,IllegalArgumentException
	{
		if(player==null)
		{
			throw new NullArgumentException("Wrong argument playerId==null");
		}

		if(_gameModels.containsKey(player) || _gameMaster.getClientId()==player.getClientId())
		{
			throw new IllegalArgumentException("Wrong argument playerId is already in game");
		}
		
		if(_isRunning)
		{
			return null;
		}
		
		try {
			Story clonedStory=(Story)BeanUtils.cloneBean(_gameModel.getGameStory());
			GameModel newModel=new GameModel(clonedStory);
			_gameModels.put(player, newModel);
			return newModel;
		} catch (IllegalAccessException e) {
			return null;
		} catch (InstantiationException e) {
			return null;
		} catch (InvocationTargetException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
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
	public synchronized boolean startGame()
	{
		//if(_gameModels.size()<2)
		//{
		//	return false;
		//}
			
		for(Map.Entry<ProMaSiClient, GameModel> curEntry : _gameModels.entrySet())
		{
			try {
				curEntry.getKey().sendMessage(new StartGameRequest(_gameModel.getCompany(), _gameModel.getMarketPlace() ).toProtocolString());
			} catch (NullArgumentException e) {
				return false;
			} catch (IllegalAccessException e) {
				return false;
			} catch (InstantiationException e) {
				return false;
			} catch (InvocationTargetException e) {
				return false;
			} catch (NoSuchMethodException e) {
				return false;
			}
		}

		_isRunning=true;
		_gameThread=new Thread(this);
		_gameThread.start();
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public synchronized boolean stopGame()
	{
		if(!_isRunning)
		{
			return false;
		}
		
		_isRunning=false;
		
		return true;
	}

	@Override
	public void run() 
	{
		while(_isRunning)
		{
			try 
			{
				for(Map.Entry<ProMaSiClient,GameModel> entry:_gameModels.entrySet())
				{
					entry.getValue().executeStep();
				}
				
				Thread.sleep(100);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
