/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class LoginProtocolState implements IProtocolState
{
	private ProMaSi _promasi;

	public LoginProtocolState(ProMaSi promasi)
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi");
		}
		_promasi=promasi;
	}

	/* (non-Javadoc)
	 * @see org.promasi.protocol.state.IProtocolState#OnReceive(org.promasi.server.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void OnReceive(ProMaSiClient client, String recData)throws ProtocolException
	{
		client.SetClientId("Unknown");
	}
}
