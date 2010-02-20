/**
 * 
 */
package org.promasi.server.core;

import java.net.ProtocolException;

/**
 * @author m1cRo
 *
 */
public class DisconnectedClientState implements IClientState {

	/* (non-Javadoc)
	 * @see org.promasi.server.core.IProtocolState#OnReceive(org.promasi.server.core.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)
			throws ProtocolException {
		// TODO Auto-generated method stub

	}

}
