/**
 *
 */
package org.promasi.server.client.state;

import java.net.ProtocolException;

import org.promasi.server.core.AbstractClientState;
import org.promasi.server.core.ProMaSiClient;

/**
 * @author m1cRo
 *
 */
public class DisconnectedClientState extends AbstractClientState {

	/* (non-Javadoc)
	 * @see org.promasi.server.core.IProtocolState#OnReceive(org.promasi.server.core.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)
			throws ProtocolException {
		// TODO Auto-generated method stub

	}

}
