/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.tcp.TcpClient;
import org.promasi.server.clientstate.IClientState;
import org.promasi.server.clientstate.LoginClientState;

/**
 * @author m1cRo
 * ProMaSiClient class.
 */
public class ProMaSiClient
{
	/**
	 *	TCP communication port for current user.
	 */
	private TcpClient _client;

	/**
	 *	Client states defined by protocol logic.
	 */
	private IClientState _clientState;

	/**
	 *	ProMasi registered user id.
	 */
	private String _clientId;

	/**
	 *
	 * @param client
	 * @param promasi
	 * @throws NullArgumentException
	 */
	public ProMaSiClient(TcpClient client,ProMaSi promasi)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong client argument");
		}

		if(promasi==null)
		{
			throw new NullArgumentException("Wrong promasi argument");
		}
		_clientId=null;
		_client=client;
		_clientState=new LoginClientState(promasi);
	}

	/**
	 *
	 * @param recData
	 * @throws ProtocolException
	 */
	public void onReceiveData(String recData)
	{
		_clientState.onReceive(this, recData);
	}

	/**
	 *
	 * @param clientState
	 */
	protected void changeState(IClientState clientState)
	{
		_clientState=clientState;
	}

	/**
	 *
	 * @param userName
	 * @throws NullArgumentException
	 */
	public void setClientId(String userName)throws NullArgumentException
	{
		synchronized(this)
		{
			_clientId=userName;
		}
	}

	/**
	 *
	 * @return
	 */
	public String getClientId()
	{
		synchronized(this)
		{
			return _clientId;
		}
	}

	/**
	 *
	 * @param message
	 * @return true if message was sent, false otherwise.
	 */
	public boolean sendMessage(String message)
	{
		return _client.sendMessage(message);
	}

	/**
	 * This method will close the connection with current user and will terminate the receive thread.
	 * @return true if the connection was successfully closed, false otherwise.
	 */
	public boolean disonnect()
	{
		return _client.disconnect();
	}
}
