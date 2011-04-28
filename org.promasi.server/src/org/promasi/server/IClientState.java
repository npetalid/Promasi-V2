/**
 *
 */
package org.promasi.server;

import java.net.ProtocolException;



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
	public void onReceive(ProMaSiClient client,String recData);
	
	/**
	 * 
	 * @param client
	 */
	public void onSetState(ProMaSiClient client);
	
	/**
	 * 
	 * @param client
	 */
	public void onDisconnect(ProMaSiClient client);
	
	/**
	 * 
	 * @param client
	 */
	public void onConnect(ProMaSiClient client);
	
	/**
	 * 
	 * @param client
	 */
	public void onConnectionError(ProMaSiClient client);
}
