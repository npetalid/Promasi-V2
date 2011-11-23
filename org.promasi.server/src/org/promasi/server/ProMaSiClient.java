/**
 *
 */
package org.promasi.server;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.binary.Base64;
import org.promasi.network.tcp.ITcpClientListener;
import org.promasi.network.tcp.NetworkException;
import org.promasi.network.tcp.TcpClient;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 * ProMaSiClient class.
 */
public class ProMaSiClient implements ITcpClientListener
{
	/**
	 *	TCP communication port for current user.
	 */
	private TcpClient _client;

	/**
	 *	Client states defined in the protocol logic.
	 */
	private IClientState _clientState;
	
	/**
	 * 
	 */
	private Lock _lockObject;

	/**
	 * 
	 * @param client
	 * @throws NullArgumentException
	 * @throws NetworkException 
	 */
	public ProMaSiClient(TcpClient client,IClientState clientState)throws NullArgumentException, NetworkException{
		if(client==null){
			throw new NullArgumentException("Wrong client argument");
		}
		
		if(clientState==null){
			throw new NullArgumentException("Wrong clientState argument");
		}

		_client=client;
		_client.addListener(this);
		_clientState=clientState; 
		_clientState.onSetState(this);
		_lockObject = new ReentrantLock();
	}

	/**
	 *
	 * @param message
	 * @return true if message was sent, false otherwise.
	 */
	public boolean sendMessage(String message){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(message !=null ){
				//System.out.print("Message "+message);
				Base64 base64=new Base64();
				byte data[]=base64.encode(message.getBytes());
				String temp=new String(data);
				StringBuilder builder = new StringBuilder();
				for(int i=0;i<temp.length();i++)
				{
					char ch=temp.charAt(i);
					if(ch!='\n' && ch!='\r')
					{
						builder.append(ch);
					}
				}
				
				builder.append("\r\n");
				//System.out.print("Encoded to "+result+"\n");
				result = _client.sendMessage(builder.toString());
			}
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param clientState
	 */
	protected boolean changeState(IClientState clientState){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(clientState!=null){
				_clientState=clientState;
				_clientState.onSetState(this);
				result = true;
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/**
	 * This method will close the connection with current user and will terminate the receive thread.
	 * @return true if the connection was successfully closed, false otherwise.
	 */
	public boolean disconnect(){
		boolean result = false;
		
		try{
			_lockObject.lock();
			result = _client.disconnect();
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	@Override
	public void onReceive(String line) {
		//System.out.print("Received : "+line+"\n");
		byte[] recBytes=Base64.decodeBase64(line.getBytes());
		//System.out.print("Decoded to - "+new String(recBytes)+"\n");
		_clientState.onReceive(this, new String(recBytes));
	}

	@Override
	public void onConnect() {
		System.out.print("Client connected\n");
		_clientState.onConnect(this);
	}

	@Override
	public void onDisconnect() {
		System.out.print("Client disconnected\n");
		_clientState.onDisconnect(this);
	}

	@Override
	public void onConnectionError() {
		System.out.print("Client connection error\n");
		_clientState.onConnectionError(this);
	}
	
	/**
	 * 
	 * @param client
	 * @return
	 */
	public boolean equals(TcpClient client){
		boolean result = false;
		
		try{
			_lockObject.lock();
			result = (client == _client);
		}finally{
			_lockObject.unlock();
		}
	
		return result;
	}
	
}

