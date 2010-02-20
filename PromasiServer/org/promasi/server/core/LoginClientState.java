/**
 *
 */
package org.promasi.server.core;

import java.beans.XMLDecoder;
import java.net.ProtocolException;
import org.apache.commons.lang.NullArgumentException;
import org.promasi.protocol.request.LoginRequest;
import org.xml.sax.InputSource;


/**
 * @author m1cRo
 *
 */
public class LoginClientState implements IClientState
{
	private ProMaSi _promasi;

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
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws ProtocolException
	{
		InputSource source=new InputSource(recData);
		source.getByteStream();
		XMLDecoder decoder=new XMLDecoder(source.getByteStream());
		try
		{
			Object object=decoder.readObject();
			if(object instanceof LoginRequest)
			{
				LoginRequest loginRequest=(LoginRequest)object;
				client.setClientId(loginRequest.getUserName());
				_promasi.addUser(client);
				client.changeState(new SelectGameClientState(_promasi));
			}
			else
			{
				throw new ProtocolException("Wrong protocol");
			}
			decoder.close();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new ProtocolException("Wrong protocol - " + e.getMessage());
		}
		catch(NullArgumentException e)
		{
			throw new ProtocolException("Wrong protocol - " + e.getMessage());
		}
		catch(IllegalArgumentException e)
		{
			throw new ProtocolException("Wrong protocol - " + e.getMessage());
		}
	}
}
