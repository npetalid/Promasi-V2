/**
 *
 */
package org.promasi.multiplayer;

import java.net.ProtocolException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.game.Game;
import org.promasi.multiplayer.game.GamesPool;
import org.promasi.multiplayer.server.UserManager;
import org.promasi.multiplayer.server.clientstate.LoginClientState;
import org.promasi.network.tcp.TcpClient;
import org.promasi.shell.ui.Story.Story;

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
	private GamesPool _games;

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
		_games=new GamesPool();
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
	public synchronized List<Story> retreiveGames()
	{
		return _games.retreiveGames();
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
		return _games.getGame(gameId);
	}

	/**
	 *
	 * @param game
	 * @throws IllegalArgumentException
	 * @throws NullArgumentException
	 */
	public synchronized void createNewGame(String gameId,Game game)throws IllegalArgumentException,NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		if(game==null)
		{
			throw new NullArgumentException("Wrong argument game==null");
		}
		
		_games.addNewGame(gameId,game);
	}
	
	public synchronized boolean joinGame(ProMaSiClient client,String gameId)
	{
		return _games.joinGame(client, gameId);
	}
}
