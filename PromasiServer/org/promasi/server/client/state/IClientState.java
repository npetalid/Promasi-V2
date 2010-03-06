/**
 *
 */
package org.promasi.server.client.state;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.core.ProMaSiClient;


/**
 * @author m1cRo
 *
 */
public interface IClientState
{
	/**
	 *
	 * @param client
	 * @param recData
	 * @throws ProtocolException
	 */
	public void onReceive(ProMaSiClient client,String recData)throws NullArgumentException;

	/**
	 *
	 * @param client
	 * @param clientState
	 */
	public void changeClientState(ProMaSiClient client,IClientState clientState)throws NullArgumentException;
}
