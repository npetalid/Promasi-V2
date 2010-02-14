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
	public boolean OnReceive(TCPClient tcpClient,String line);
	public boolean OnConnect(TCPClient tcpClient);
	public void OnDisconnect(TCPClient tcpClient);
	public void OnConnectionError(TCPClient tcpClient);
}
