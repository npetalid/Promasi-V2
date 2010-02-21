/**
 *
 */
package org.promasi.tcpserver;

/**
 * @author m1cRo
 *
 */
public class TCPStrategy
{
	/**
	 *
	 */
	private ITCPEventHandler _tcpEventHandler;

	/**
	 *
	 */
	public TCPStrategy(){
	}

	/**
	 *
	 * @param tcpEventHandler
	 * @return
	 */
	public void registerTcpEventHandler(ITCPEventHandler tcpEventHandler)
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
	public boolean onReceive(TCPClient tcpClient,String line)
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
	public void onDisconnect(TCPClient tcpClient)
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
	public boolean onConnect(TCPClient tcpClient)
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
	public void onConnectionError(TCPClient tcpClient)
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
	public ITCPEventHandler getTcpEventHandler()
	{
		synchronized(this)
		{
			return _tcpEventHandler;
		}
	}
}
