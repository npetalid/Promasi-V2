/**
 *
 */
package org.promasi.server.core;

import java.net.ProtocolException;


/**
 * @author m1cRo
 *
 */
public interface IProtocolState
{
	/**
	 *
	 * @param client
	 * @param recData
	 * @throws ProtocolException
	 */
	public void onReceive(ProMaSiClient client,String recData)throws ProtocolException;
}
