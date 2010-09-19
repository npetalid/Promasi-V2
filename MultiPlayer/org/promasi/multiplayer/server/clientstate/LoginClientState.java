/**
 *
 */
package org.promasi.multiplayer.server.clientstate;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.ProjectManager;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSi;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.LoginRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.LoginResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;


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
				client.setClientId(loginRequest.getFistName());
				_promasi.addUser(client);
				changeClientState(client,new JoinGameClientState(_promasi));
				ProjectManager projectManager=new ProjectManager(loginRequest.getFistName(),loginRequest.getLastName());
				LoginResponse loginResponse=new LoginResponse(projectManager);
				client.sendMessage(loginResponse.toProtocolString());
			}
			else
			{
				client.sendMessage(new WrongProtocolResponse().toProtocolString());
				client.disconnect();
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toProtocolString());
			client.disconnect();
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
	}
}
