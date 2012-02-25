/**
 *
 */
package org.promasi.protocol.client;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.binary.Base64;
import org.promasi.network.tcp.ITcpClientListener;
import org.promasi.network.tcp.NetworkException;
import org.promasi.network.tcp.TcpClient;
import org.promasi.protocol.compression.CompressionException;
import org.promasi.protocol.compression.ICompression;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 * Represent the network client on ProMaSi system.
 * This class will communicate with the ProMaSi server, also
 * this class provide the state machine engine so user can 
 * change the states of the client in order to receive messages.
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
	private ICompression _compression;
	
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
	public ProMaSiClient(TcpClient client,IClientState clientState, ICompression compression)throws NetworkException{
		if(client==null){
			throw new NetworkException("Wrong client argument");
		}
		
		if(clientState==null){
			throw new NetworkException("Wrong clientState argument");
		}

		_client=client;
		_client.addListener(this);
		_clientState=clientState; 
		_clientState.onSetState(this, clientState);
		_lockObject = new ReentrantLock();
		_compression = compression;
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
				byte[] outputMessage = message.getBytes();
				if( _compression != null ){
					outputMessage = _compression.compress( message.getBytes() );
				}
				
				Base64 base64=new Base64();
				byte data[]=base64.encode(outputMessage);
				String temp=new String(data);
				StringBuilder builder = new StringBuilder();
				builder.append(temp.replaceAll("\n", "").replaceAll("\r", ""));
				builder.append("\r\n");
				result = _client.sendMessage(builder.toString());
			}
		} catch (CompressionException e) {
			//TODO log
		}finally{
			_lockObject.unlock();
		}

		return result;
	}
	
	/**
	 * 
	 * @param clientState
	 */
	public boolean changeState(IClientState clientState){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(clientState!=null){
				_clientState=clientState;
				_clientState.onSetState(this, clientState);
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
        Base64 base64= new Base64();
        byte[] messageByte = base64.decode(line.getBytes());
        try {
			_clientState.onReceive(this, new String(_compression.deCompress(messageByte)));
		} catch (CompressionException e) {
			// TODO log
		}
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

