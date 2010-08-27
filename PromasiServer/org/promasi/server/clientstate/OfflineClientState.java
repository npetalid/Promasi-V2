/**
 *
 */
package org.promasi.server.clientstate;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.protocol.request.RequestBuilder;
import org.promasi.protocol.response.InternalErrorResponse;
import org.promasi.protocol.response.WrongProtocolResponse;
import org.promasi.server.core.AbstractClientState;
import org.promasi.server.core.ProMaSiClient;

/**
 * @author m1cRo
 *
 */
public class OfflineClientState extends AbstractClientState {

	/* (non-Javadoc)
	 * @see org.promasi.server.core.IProtocolState#OnReceive(org.promasi.server.core.ProMaSiClient, java.lang.String)
	 */
	@Override
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
			RequestBuilder.buildRequest(recData);
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
