/**
 *
 */
package org.promasi.network.tcp;

/**
 * @author m1cRo
 *
 */
public interface ITcpClientListener
{
	/**
	 * 
	 * @param line
	 */
	public void onReceive(String line);

	/**
	 * 
	 */
	public void onConnect();

	/**
	 * 
	 */
	public void onDisconnect();

	/**
	 * 
	 */
	public void onConnectionError();
}
