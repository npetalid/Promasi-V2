/**
 *
 */
package org.promasi.server;
import java.net.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.IllegalArgumentException;;

/**
 * @author m1cRo
 *
 */
public class TCPClient implements Runnable
{

	private Socket _clientSocket;

	private boolean _isConnected;

	private TCPStrategy _tcpStrategy;

	private Thread _recvThread;

	public  static final int CONST_RECV_BUFFER_LENGTH=2048;


	public TCPClient(Socket socket) throws IllegalArgumentException{
		if(socket==null){
			throw new IllegalArgumentException("Wrong socket==null");
		}
		_clientSocket=socket;
		_isConnected=true;
		_tcpStrategy=new TCPStrategy();
		_recvThread=new Thread(this);
		_recvThread.start();
	}


	public void run(){
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));;
			if(!_tcpStrategy.OnConnect(this)){
				String line=null;
				do{
					line=reader.readLine();
					if( line != null && !_tcpStrategy.OnReceive(this, line))
					{
						reader.close();
						_clientSocket.shutdownInput();
						_clientSocket.shutdownOutput();
						_clientSocket.close();
						break;
					}
				}while(line!=null);
			}
		}catch(IOException e){
			_tcpStrategy.OnConnectionError(this);
		}
		_tcpStrategy.OnDisconnect(this);
		_isConnected=false;
	}


	public boolean IsConnected(){
		return _isConnected;
	}


	public boolean RegisterTcpEventHandler(ITCPEventHandler tcpEventHandler){
		return _tcpStrategy.RegisterTcpEventHandler(tcpEventHandler);
	}


	public boolean Disconnect(){
		try{
			_clientSocket.close();
		}catch(IOException e){
			return false;
		}
		return true;
	}


	public boolean SendMessage(String message){
		try{
			BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(_clientSocket.getOutputStream()));
			writer.write(message);
		}catch(IOException e){
			return false;
		}
		return true;
	}
}
