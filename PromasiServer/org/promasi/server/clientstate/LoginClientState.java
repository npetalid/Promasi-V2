/**
 *
 */
package org.promasi.server.clientstate;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.server.ProMaSi;
import org.promasi.network.protocol.request.LoginRequest;
import org.promasi.network.protocol.request.RequestBuilder;
import org.promasi.network.protocol.response.InternalErrorResponse;
import org.promasi.network.protocol.response.LoginResponse;
import org.promasi.network.protocol.response.WrongProtocolResponse;


/**
 * @author m1cRo
 *
 */
public class LoginClientState extends AbstractClientState
{
	/**
	 *
	 */
	private ProMaSi _promasi;

	/**
	 *
	 * @param promasi
	 */
	public LoginClientState(ProMaSi promasi)
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi");
		}
		_promasi=promasi;
	}

	/**
	 *
	 */
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof LoginRequest)
			{
				LoginRequest loginRequest=(LoginRequest)object;
				client.setClientId(loginRequest.getUserName());
				_promasi.addUser(client);
				changeClientState(client,new JoinGameClientState(_promasi));
				client.sendMessage(new LoginResponse().toXML());
			}
			else
			{
				client.sendMessage(new WrongProtocolResponse().toXML());
				client.disonnect();
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toXML());
			client.disonnect();
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
			client.disonnect();
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
			client.disonnect();
		}
	}
}
