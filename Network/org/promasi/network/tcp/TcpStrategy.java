/**
 *
 */
package org.promasi.network.tcp;

/**
 * @author m1cRo
 *
 */
public class TcpStrategy
{
	/**
	 *
	 */
	private ITcpEventHandler _tcpEventHandler;

	/**
	 *
	 */
	public TcpStrategy(){
	}

	/**
	 *
	 * @param tcpEventHandler
	 * @return
	 */
	public synchronized void registerTcpEventHandler(ITcpEventHandler tcpEventHandler)
	{
		_tcpEventHandler=tcpEventHandler;
	}

	/**
	 *
	 * @param tcpClient
	 * @param line
	 * @return
	 */
	public synchronized boolean onReceive(TcpClient tcpClient,String line)
	{
		if(_tcpEventHandler!=null)
		{
			return _tcpEventHandler.onReceive(tcpClient,line);
		}
		
		return true;
	}

	/**
	 *
	 * @param tcpClient
	 * @return
	 */
	public synchronized void onDisconnect(TcpClient tcpClient)
	{
		_tcpEventHandler.onDisconnect(tcpClient);
	}

	/**
	 *
	 * @param tcpClient
	 * @return
	 */
	public synchronized boolean onConnect(TcpClient tcpClient)
	{
		if(_tcpEventHandler!=null)
		{
			return _tcpEventHandler.onConnect(tcpClient);
		}
		
		return true;
	}

	/**
	 *
	 * @param tcpClient
	 */
	public synchronized void onConnectionError(TcpClient tcpClient)
	{
		_tcpEventHandler.onConnectionError(tcpClient);
	}

	/**
	 *
	 * @return
	 */
	public synchronized ITcpEventHandler getTcpEventHandler()
	{
		return _tcpEventHandler;
	}
	
}
