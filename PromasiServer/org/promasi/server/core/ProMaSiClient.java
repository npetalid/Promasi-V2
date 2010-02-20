/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.client.state.IClientState;
import org.promasi.server.client.state.LoginClientState;
import org.promasi.tcpserver.TCPClient;

/**
 * @author m1cRo
 *
 */
public class ProMaSiClient
{
	private TCPClient _client;

	private IClientState _clientState;

	private String _clientId;

	public ProMaSiClient(TCPClient client,ProMaSi promasi)throws NullArgumentException
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

	public void onReceiveData(String recData)throws ProtocolException
	{
		_clientState.onReceive(this, recData);
	}

	protected void changeState(IClientState clientState)
	{
		_clientState=clientState;
	}

	public void setClientId(String userName)throws NullArgumentException
	{
		synchronized(this)
		{
			_clientId=userName;
		}
	}

	public String getClientId()
	{
		synchronized(this)
		{
			return _clientId;
		}
	}

	public void sendData(String sData)
	{
		_client.sendMessage(sData);
	}

	public boolean disonnect()
	{
		return _client.disconnect();
	}
}
