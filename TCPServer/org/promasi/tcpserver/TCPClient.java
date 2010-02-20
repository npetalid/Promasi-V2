/**
 *
 */
package org.promasi.tcpserver;
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
public class TCPClient implements Runnable
{
	/*
	 * Client socket.
	 */
	private Socket _clientSocket;

	private boolean _isConnected;

	private TCPStrategy _tcpStrategy;

	private Thread _recvThread;

	public TCPClient(Socket socket) throws IllegalArgumentException
	{
		if(socket==null)
		{
			throw new IllegalArgumentException("Wrong socket==null");
		}
		_clientSocket=socket;
		_isConnected=true;
		_tcpStrategy=new TCPStrategy();
		_recvThread=new Thread(this);
		_recvThread.start();
	}


	public void run(){
		try
		{
			BufferedReader reader=new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
			if(_tcpStrategy.onConnect(this))
			{
				String line=null;
				do
				{
					line=reader.readLine();
					if( line != null && !_tcpStrategy.onReceive(this, line))
					{
						reader.close();
						_clientSocket.shutdownInput();
						_clientSocket.shutdownOutput();
						_clientSocket.close();
						break;
					}
				}
				while(line!=null);
			}
		}
		catch(IOException e)
		{
			_tcpStrategy.onConnectionError(this);
		}
		_tcpStrategy.onDisconnect(this);
		_isConnected=false;
	}


	public boolean isConnected()
	{
		return _isConnected;
	}


	public void registerTcpEventHandler(ITCPEventHandler tcpEventHandler)
	{
		_tcpStrategy.registerTcpEventHandler(tcpEventHandler);
	}


	public boolean disconnect()
	{
		try
		{
			_clientSocket.close();
		}
		catch(IOException e)
		{
			return false;
		}
		return true;
	}


	public boolean sendMessage(String message){
		try
		{
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(_clientSocket.getOutputStream()));
			writer.write(message);
		}
		catch(IOException e)
		{
			return false;
		}
		return true;
	}
}
