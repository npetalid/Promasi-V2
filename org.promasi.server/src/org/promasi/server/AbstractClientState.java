/**
 *
 */
package org.promasi.server;

import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public abstract class AbstractClientState implements IClientState
{
	/**
	 * 
	 * @param client
	 * @param clientState
	 * @throws NullArgumentException
	 */
	public void changeClientState(ProMaSiClient client,IClientState clientState) throws NullArgumentException
	{
		if(clientState==null)
		{
			throw new NullArgumentException("Wrong argument clientState==null");
		}

		if(client==null)
		{
			throw new NullArgumentException("Wrong argument promasiClient==null");
		}
		
		client.changeState(clientState);
	}
}
