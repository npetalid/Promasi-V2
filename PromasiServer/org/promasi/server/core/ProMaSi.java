/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.Game;
import org.promasi.tcpserver.TCPClient;

/**
 * @author m1cRo
 *
 */
public class ProMaSi
{
	/**
	 *
	 */
	private Map<TCPClient,ProMaSiClient> _clients;

	/**
	 *
	 */
	private Game _game;

	/**
	 *
	 */
	private ClientList _userList;

	/**
	 *
	 */
	public ProMaSi()
	{
		_clients=new HashMap<TCPClient,ProMaSiClient>();
		_userList=new ClientList();
	}

	/**
	 *
	 */
	public void OnReceiveData(TCPClient client,String recData)throws NullArgumentException,ProtocolException,IllegalArgumentException
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
			_clients.get(client).OnReceiveData(recData);
		}
	}

	/**
	 *
	 */
	public void OnConnect(TCPClient client)
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client");
		}
		ProMaSiClient promasiClient=new ProMaSiClient(client,this);
		synchronized(_clients)
		{
			_clients.put(client, promasiClient);
		}
	}

	/**
	 *
	 */
	public void OnDisconnect(TCPClient client)throws NullArgumentException,IllegalArgumentException
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
				_userList.RemoveUser(promasiClient.GetClientId());
			}
			catch(IllegalArgumentException e)
			{

			}
			_clients.remove(client);
		}
	}

	/**
	 *
	 */
	public void OnConnectionError(TCPClient client)throws NullArgumentException,IllegalArgumentException
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
				_userList.RemoveUser(promasiClient.GetClientId());
			}
			catch(IllegalArgumentException e)
			{

			}
			_clients.remove(client);
		}
	}

	/**
	 *
	 */
	public boolean IsClientInList(String clientId)
	{
		return _userList.IsUserInList(clientId);
	}
}
