/**
 *
 */
package org.promasi.protocol.client;

import java.util.LinkedList;
import java.util.List;
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
	private List< IClientListener > _listeners;
	
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
	public ProMaSiClient(TcpClient client, ICompression compression)throws NetworkException{
		if(client==null){
			throw new NetworkException("Wrong client argument");
		}

		_client=client;
		_client.addListener(this);
		_listeners = new LinkedList<>();
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
	 * @param listener
	 */
	public boolean addListener(IClientListener listener){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(listener!=null && !_listeners.contains(listener)){
				result = _listeners.add(listener);
			}
		}finally{
			_lockObject.unlock();
		}
		
		return result;
	}

	/**
	 * 
	 * @param listener
	 */
	public boolean removeListener(IClientListener listener){
		boolean result = false;
		
		try{
			_lockObject.lock();
			if(listener!=null && _listeners.contains(listener)){
				result = _listeners.remove(listener);
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
			for( IClientListener listener : _listeners ){
				listener.onDisconnect(this);
			}
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
        	_lockObject.lock();
        	List< IClientListener > listeners = new LinkedList<>(_listeners);
    		for( IClientListener listener : listeners ){
    			if( _compression != null ){
    				listener.onReceive(this, new String(_compression.deCompress(messageByte)));
    			}else{
    				listener.onReceive(this, new String(messageByte));
    			}
    		}
			
		} catch (CompressionException e) {
			// TODO log
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void onConnect() {
		try{
			_lockObject.lock();
			for( IClientListener listener : _listeners ){
				listener.onConnect(this);
			}
		}finally{
			_lockObject.unlock();
		}

	}

	@Override
	public void onDisconnect() {
		try{
			_lockObject.lock();
			for( IClientListener listener : _listeners ){
				listener.onDisconnect(this);
			}
		}finally{
			_lockObject.unlock();
		}
	}

	@Override
	public void onConnectionError() {
		try{
			_lockObject.lock();
			for( IClientListener listener : _listeners ){
				listener.onConnectionError(this);
			}
		}finally{
			_lockObject.unlock();
		}
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

