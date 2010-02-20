/**
 *
 */
package org.promasi.tcpserver;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.io.IOException;
import java.net.*;


/**
 * @author m1cRo
 *
 */
public class TCPServer implements Runnable,IServer
{
	/**
	 *
	 */
	private TCPClientManager _clientManager;

	/**
	 *
	 */
	private TCPStrategy _tcpStrategy;

	/**
	 *
	 */
	private ServerSocket _serverSocket;

	/**
	 *
	 */
	private Thread _acceptThread;

	/**
	 *
	 */
	private boolean _listening;

	/**
	 *
	 */
	public TCPServer()
	{
		_clientManager=new TCPClientManager();
		_tcpStrategy=new TCPStrategy();
		_acceptThread=null;
		_listening=false;
	}

	/**
	 *
	 * @param tcpEventHandler
	 */
	public void registerTcpEventHandler(ITCPEventHandler tcpEventHandler)
	{
		_tcpStrategy.registerTcpEventHandler(tcpEventHandler);
		_clientManager.registerTcpEventHandler(tcpEventHandler);
	}


	/* (non-Javadoc)
	 * @see Server.ITCPServer#Start(int)
	 */
	@Override
	public boolean start(int portNumber)
	{
		synchronized(this)
		{
			try
			{
				_clientManager.clear();
				_serverSocket=new ServerSocket(portNumber);
				_listening=true;
				_acceptThread=new Thread(this);
				_acceptThread.start();
			}
			catch(IOException e)
			{
				return false;
			}
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see Server.ITCPServer#Stop()
	 */
	@Override
	public boolean stop()
	{
		synchronized(this)
		{
			try{
				_clientManager.clear();
				_serverSocket.close();
				_listening=false;
				while(_acceptThread.isAlive())
				{
					try
					{
						Thread.sleep(100);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				_acceptThread=null;
			}
			catch(IOException e)
			{
				return false;
			}
		}
		return true;
	}


	@Override
	public void run()
{
		while(_listening)
		{
			try
			{
				Socket clientSocket=_serverSocket.accept();
				TCPClient client=new TCPClient(clientSocket);
				client.registerTcpEventHandler(_tcpStrategy.getTcpEventHandler());
				_clientManager.addClient(client);
			}
			catch(IOException e)
			{

			}
		}
	}
}
