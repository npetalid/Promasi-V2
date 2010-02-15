/**
 *
 */
package org.promasi.tcpserver;
/**
 * @author m1cRo
 *
 */
public interface ITCPEventHandler
{
	public boolean onReceive(TCPClient tcpClient,String line);
	public boolean onConnect(TCPClient tcpClient);
	public void onDisconnect(TCPClient tcpClient);
	public void onConnectionError(TCPClient tcpClient);
}
