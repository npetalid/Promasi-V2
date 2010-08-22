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
	public void registerTcpEventHandler(ITcpEventHandler tcpEventHandler)
	{
		synchronized(this)
		{
			_tcpEventHandler=tcpEventHandler;
		}
	}

	/**
	 *
	 * @param tcpClient
	 * @param line
	 * @return
	 */
	public boolean onReceive(TcpClient tcpClient,String line)
	{
		synchronized(this)
		{
			if(_tcpEventHandler!=null)
			{
				return _tcpEventHandler.onReceive(tcpClient,line);
			}
		}
		return true;
	}

	/**
	 *
	 * @param tcpClient
	 * @return
	 */
	public void onDisconnect(TcpClient tcpClient)
	{
		synchronized(this)
		{
			_tcpEventHandler.onDisconnect(tcpClient);
		}
	}

	/**
	 *
	 * @param tcpClient
	 * @return
	 */
	public boolean onConnect(TcpClient tcpClient)
	{
		synchronized(this)
		{
			if(_tcpEventHandler!=null)
			{
				return _tcpEventHandler.onConnect(tcpClient);
			}
		}
		return true;
	}

	/**
	 *
	 * @param tcpClient
	 */
	public void onConnectionError(TcpClient tcpClient)
	{
		synchronized(this)
		{
			_tcpEventHandler.onConnectionError(tcpClient);
		}
	}

	/**
	 *
	 * @return
	 */
	public ITcpEventHandler getTcpEventHandler()
	{
		synchronized(this)
		{
			return _tcpEventHandler;
		}
	}
}
