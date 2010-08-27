/**
 *
 */
package org.promasi.network.tcp;
import java.io.IOException;
import java.net.*;


/**
 * @author m1cRo
 *
 */
public class TcpServer implements Runnable,IServer
{
	/**
	 *
	 */
	private TcpClientManager _clientManager;

	/**
	 *
	 */
	private TcpStrategy _tcpStrategy;

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
	public TcpServer()
	{
		_clientManager=new TcpClientManager();
		_tcpStrategy=new TcpStrategy();
		_acceptThread=null;
		_listening=false;
	}

	/**
	 *
	 * @param tcpEventHandler
	 */
	public void registerTcpEventHandler(ITcpEventHandler tcpEventHandler)
	{
		_tcpStrategy.registerTcpEventHandler(tcpEventHandler);
		_clientManager.registerTcpEventHandler(tcpEventHandler);
	}


	/* (non-Javadoc)
	 * @see Server.ITCPServer#Start(int)
	 */
	@Override
	public synchronized boolean start(int portNumber)
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

		return true;
	}


	/* (non-Javadoc)
	 * @see Server.ITCPServer#Stop()
	 */
	@Override
	public synchronized boolean stop()
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
				TcpClient client=new TcpClient(clientSocket);
				client.registerTcpEventHandler(_tcpStrategy.getTcpEventHandler());
				_clientManager.addClient(client);
			}
			catch(IOException e)
			{

			}
		}
	}
}
