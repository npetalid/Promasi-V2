/**
 *
 */
package org.promasi.server.client.state;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.core.AbstractClientState;
import org.promasi.server.core.ProMaSi;
import org.promasi.server.core.ProMaSiClient;

/**
 * @author m1cRo
 *
 */
public class PlayingClientState extends AbstractClientState {

	/**
	 *
	 */
	private ProMaSi _promasi;

	/**
	 *
	 */
	public PlayingClientState(ProMaSi promasi)
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
	public void onReceive(ProMaSiClient client, String recData) throws ProtocolException,NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
	}

}
