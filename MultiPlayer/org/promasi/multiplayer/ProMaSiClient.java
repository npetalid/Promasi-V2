/**
 *
 */
package org.promasi.multiplayer;

import java.io.IOException;
import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.server.clientstate.IClientState;
import org.promasi.network.tcp.TcpClient;
import org.w3c.tools.codec.Base64Encoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
	 * @throws NullArgumentException
	 */
	public ProMaSiClient(TcpClient client,IClientState clientState)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong client argument");
		}
		
		if(clientState==null){
			throw new NullArgumentException("Wrong clientState argument");
		}

		_clientId=null;
		_client=client;
		_clientState=clientState; 
	}

	/**
	 *
	 * @param recData
	 * @return 
	 * @throws ProtocolException
	 */
	public boolean onReceiveData(String recData)
	{
		try 
		{
			byte[] recBytes=new BASE64Decoder().decodeBuffer(recData);
			System.out.print("Decoded to - "+new String(recBytes)+"\n");
			_clientState.onReceive(this, new String(recBytes));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		catch(NullArgumentException e)
		{
			return false;
		}
		
		
		return true;
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
	public synchronized void setClientId(String userName)throws NullArgumentException
	{
		_clientId=userName;
	}

	/**
	 *
	 * @return
	 */
	public synchronized String getClientId()
	{
		return _clientId;
	}

	public synchronized TcpClient getTcpClient()
	{
		return _client;
	}

	/**
	 * This method will close the connection with current user and will terminate the receive thread.
	 * @return true if the connection was successfully closed, false otherwise.
	 */
	public boolean disconnect()
	{
		return _client.disconnect();
	}
}
