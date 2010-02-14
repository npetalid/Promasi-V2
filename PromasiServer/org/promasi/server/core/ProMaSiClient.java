/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.tcpserver.TCPClient;

/**
 * @author m1cRo
 *
 */
public class ProMaSiClient
{
	private TCPClient _client;

	private ProMaSi _promasi;

	private IProtocolState _protocolState;

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
		_promasi=promasi;
		_client=client;
		_protocolState=new LoginProtocolState(promasi);
	}

	public void OnReceiveData(String recData)throws ProtocolException
	{
		_protocolState.OnReceive(this, recData);
	}

	protected void ChangeState(IProtocolState protocolState)
	{
		_protocolState=protocolState;
	}

	protected void SetClientId(String userName)throws NullArgumentException
	{
		synchronized(this)
		{
			_clientId=userName;
		}
	}

	protected String GetClientId()
	{
		synchronized(this)
		{
			return _clientId;
		}
	}

	public void SendData(String sData)
	{
		_client.SendMessage(sData);
	}
}
