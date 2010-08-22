/**
 *
 */
package org.promasi.network.tcp;

import java.util.LinkedList;

/**
 * @author m1cRo
 *
 */
public class TcpClientManager implements Runnable
{
	/**
	 *
	 */
	private LinkedList<TcpClient> _clients;

	/**
	 *
	 */
	private Thread _managementThread;

	/**
	 *
	 */
	private boolean _running;

	/**
	 * Default constructor.
	 */
	TcpClientManager()
	{
		_clients=new LinkedList<TcpClient>();
		_running=true;
		_managementThread=new Thread(this);
		_managementThread.start();
	}

	@Override
	public void run()
	{
		while(_running)
		{
			synchronized(this)
			{
				for(TcpClient client:_clients)
				{
					if(!client.isConnected())
					{
						_clients.remove(client);
						break;
					}
				}
			}

			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 *
	 */
	public void finalize()
	{
		_running=false;
	}

	/**
	 *
	 * @param client
	 * @return
	 */
	public boolean addClient(TcpClient client)
	{
		synchronized(this)
		{
			return _clients.add(client);
		}
	}

	/**
	 *
	 * @param client
	 * @return
	 */
	public boolean removeClient(TcpClient client)
	{
		synchronized(this)
		{
			return _clients.remove(client);
		}
	}

	/**
	 *
	 * @param tcpEventHandler
	 */
	public void registerTcpEventHandler(ITcpEventHandler tcpEventHandler){
		synchronized(this)
		{
			for(TcpClient client:_clients)
			{
				client.registerTcpEventHandler(tcpEventHandler);
			}
		}
	}

	/**
	 *
	 * @return
	 */
	public boolean clear()
	{
		synchronized(this)
		{
			for(TcpClient client:_clients)
			{
				if(!client.disconnect())
				{
					return false;
				}
			}
			_clients.clear();
		}
		return true;
	}

}
