/**
 *
 */
package org.promasi.tcpserver;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public interface ITCPEventHandler
{
	/**
	 *
	 * @param tcpClient
	 * @param line
	 * @return true if you want to keep the current connection, false otherwise.
	 */
	public boolean onReceive(TCPClient tcpClient,String line)throws NullArgumentException;

	/**
	 *
	 * @param tcpClient
	 * @return true if you want to keep the current connection, false otherwise.
	 */
	public boolean onConnect(TCPClient tcpClient)throws NullArgumentException;

	/**
	 *
	 * @param tcpClient
	 */
	public void onDisconnect(TCPClient tcpClient)throws NullArgumentException;

	/**
	 *
	 * @param tcpClient
	 */
	public void onConnectionError(TCPClient tcpClient)throws NullArgumentException;
}
