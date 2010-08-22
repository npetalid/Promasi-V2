/**
 *
 */
package org.promasi.network.tcp;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public interface ITcpEventHandler
{
	/**
	 *
	 * @param tcpClient
	 * @param line
	 * @return true if you want to keep the current connection, false otherwise.
	 */
	public boolean onReceive(TcpClient tcpClient,String line)throws NullArgumentException;

	/**
	 *
	 * @param tcpClient
	 * @return true if you want to keep the current connection, false otherwise.
	 */
	public boolean onConnect(TcpClient tcpClient)throws NullArgumentException;

	/**
	 *
	 * @param tcpClient
	 */
	public void onDisconnect(TcpClient tcpClient)throws NullArgumentException;

	/**
	 *
	 * @param tcpClient
	 */
	public void onConnectionError(TcpClient tcpClient)throws NullArgumentException;
}
