/**
 *
 */
package org.promasi.server.core;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.client.state.IClientState;

/**
 * @author m1cRo
 *
 */
public abstract class AbstractClientState implements IClientState
{
	@Override
	public void changeClientState(ProMaSiClient client,IClientState clientState)throws NullArgumentException
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
