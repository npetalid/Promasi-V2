/**
 * 
 */
package org.promasi.network.tcp;

/**
 * @author m1cRo
 *
 */
public interface ITcpServerListener 
{
	/**
	 * 
	 * @param portNumber
	 */
	public void serverStarted(int portNumber);
	
	/**
	 * 
	 * @param portNumber
	 */
	public void serverStopped();
	
	/**
	 * 
	 * @param client
	 */
	public void clientConnected(TcpClient client);
	
	/**
	 * 
	 * @param client
	 */
	public void clientDisconnected(TcpClient client);
}
