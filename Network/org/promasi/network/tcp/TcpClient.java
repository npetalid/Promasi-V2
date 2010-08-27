/**
 *
 */
package org.promasi.network.tcp;
import java.net.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.IllegalArgumentException;;

/**
 * @author m1cRo
 * TCPClient class.
 *
 */
public class TcpClient implements Runnable
{
	/**
	 *
	 */
	private Socket _clientSocket;

	/**
	 *
	 */
	private boolean _isConnected;

	/**
	 *
	 */
	private TcpStrategy _tcpStrategy;

	/**
	 *
	 */
	private Thread _recvThread;

	/**
	 *
	 */
	private BufferedReader _socketStreamReader;

	/**
	 *
	 */
	private BufferedWriter _socketStreamWriter;
	
	/**
	 * 
	 * @param hostname
	 * @param portNumber
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public TcpClient(String hostname,int portNumber)throws IllegalArgumentException, IOException, UnknownHostException{
		_clientSocket=new Socket(hostname,portNumber);
		_socketStreamReader=new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
		_socketStreamWriter=new BufferedWriter(new OutputStreamWriter(_clientSocket.getOutputStream()));
		_isConnected=true;
		_tcpStrategy=new TcpStrategy();
		_recvThread=new Thread(this);
		_recvThread.start();
	}
	
	/**
	 *
	 * @param socket
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public TcpClient(Socket socket) throws IllegalArgumentException, IOException
	{
		if(socket==null)
		{
			throw new IllegalArgumentException("Wrong socket==null");
		}
		
		_clientSocket=socket;
		_socketStreamReader=new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
		_socketStreamWriter=new BufferedWriter(new OutputStreamWriter(_clientSocket.getOutputStream()));
		_isConnected=true;
		_tcpStrategy=new TcpStrategy();
		_recvThread=new Thread(this);
		_recvThread.start();
	}


	/**
	 *
	 */
	public void run(){

			try
			{
				if(_tcpStrategy.onConnect(this))
				{
					String line=null;
					do
					{
						line=_socketStreamReader.readLine();
						synchronized(this)
						{
							if( line != null && !_tcpStrategy.onReceive(this, line))
							{
								_socketStreamReader.close();
								_clientSocket.shutdownInput();
								_clientSocket.shutdownOutput();
								_clientSocket.close();
								break;
							}
						}
					}while(line!=null);
					
					_tcpStrategy.onDisconnect(this);
				}
			}
			catch(IOException e)
			{
				_tcpStrategy.onConnectionError(this);
			}
			
			_isConnected=false;
	}

	/**
	 *
	 * @return
	 */
	public synchronized boolean isConnected()
	{
		return _isConnected;
	}

	/**
	 *
	 * @param tcpEventHandler
	 */
	public void registerTcpEventHandler(ITcpEventHandler tcpEventHandler)
	{
		_tcpStrategy.registerTcpEventHandler(tcpEventHandler);
	}

	/**
	 *
	 * @return
	 */
	public synchronized boolean disconnect()
	{
		try
		{
			_socketStreamReader.close();
			_socketStreamWriter.close();
			_clientSocket.shutdownInput();
			_clientSocket.shutdownOutput();
			_clientSocket.close();
		}
		catch(IOException e)
		{
			return false;
		}
		
		return true;
	}

	/**
	 *
	 * @param message
	 * @return
	 */
	public synchronized boolean sendMessage(String message){
		if(message==null)
		{
			return false;
		}

		try
		{
			_socketStreamWriter.write(message);
			_socketStreamWriter.flush();
		}
		catch(IOException e)
		{
			return false;
		}
		
		return true;
	}
}
