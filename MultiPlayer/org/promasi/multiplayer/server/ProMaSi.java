/**
 *
 */
package org.promasi.multiplayer.server;

import java.net.ProtocolException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.Game;
import org.promasi.multiplayer.game.GameList;
import org.promasi.multiplayer.server.clientstate.LoginClientState;
import org.promasi.network.tcp.TcpClient;

/**
 * @author m1cRo
 *
 */
public class ProMaSi
{
	/**
	 *
	 */
	private Map<TcpClient,ProMaSiClient> _clients;

	/**
	 *
	 */
	private GameList _gameList;

	/**
	 *
	 */
	private UserManager _userManager;

	/**
	 *
	 */
	public ProMaSi()
	{
		_clients=new HashMap<TcpClient,ProMaSiClient>();
		_userManager=new UserManager();
		_gameList=new GameList();
	}

	/**
	 *
	 * @param client
	 * @param recData
	 * @throws NullArgumentException
	 * @throws ProtocolException
	 * @throws IllegalArgumentException
	 */
	public void onReceiveData(TcpClient client,String recData)throws NullArgumentException,IllegalArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client");
		}

		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument recData");
		}

		if(!_clients.containsKey(client))
		{
			throw new IllegalArgumentException("Illegal argument client");
		}
		
		synchronized(_clients)
		{
			_clients.get(client).onReceiveData(recData);
		}
	}

	/**
	 *
	 * @param client
	 */
	public void onConnect(TcpClient client)
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client");
		}
		ProMaSiClient promasiClient=new ProMaSiClient(client,new LoginClientState(this));
		synchronized(_clients)
		{
			_clients.put(client, promasiClient);
		}
	}

	/**
	 *
	 * @param client
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void onDisconnect(TcpClient client)throws NullArgumentException,IllegalArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client");
		}

		synchronized(_clients)
		{
			if(!_clients.containsKey(client))
			{
				throw new IllegalArgumentException("IllegalArgument client");
			}
			ProMaSiClient promasiClient=_clients.get(client);
			try
			{
				_userManager.removeUser(promasiClient.getClientId());
			}
			catch(IllegalArgumentException e)
			{

			}
			_clients.remove(client);
		}
	}

	/**
	 *
	 * @param client
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void onConnectionError(TcpClient client)throws NullArgumentException,IllegalArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client");
		}

		synchronized(_clients)
		{
			if(!_clients.containsKey(client))
			{
				throw new IllegalArgumentException("IllegalArgument client");
			}
			ProMaSiClient promasiClient=_clients.get(client);
			try
			{
				_userManager.removeUser(promasiClient.getClientId());
			}
			catch(IllegalArgumentException e)
			{

			}
			_clients.remove(client);
		}
	}

	/**
	 * isClientInList method.
	 * @param clientId
	 * @return true if client with the same clientId was found in _userList "{@link UserManager}", false otherwise.
	 */
	public boolean isClientInList(String clientId)
	{
		return _userManager.isUserInList(clientId);
	}

	/**
	 * addUser method.
	 * @param client
	 * Instance of {@link ProMaSiClient} client that identify the client who from the {@link LoginRequest} was received.
	 */
	public void addUser(ProMaSiClient client)throws IllegalArgumentException,NullArgumentException
	{
		_userManager.addUser(client.getClientId(),client);
	}

	/**
	 *
	 * @return
	 */
	public LinkedList<String> retreiveGames()
	{
		return _gameList.retreiveGames();
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
		return _gameList.getGame(gameId);
	}

	/**
	 *
	 * @param game
	 * @throws IllegalArgumentException
	 * @throws NullArgumentException
	 */
	public void createNewGame(Game game)throws IllegalArgumentException,NullArgumentException
	{
		_gameList.addNewGame(game);
	}
	
	public synchronized boolean joinGame(ProMaSiClient client,String gameId)
	{
		return _gameList.joinGame(client, gameId);
	}
}
