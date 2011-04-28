/**
 *
 */
package org.promasi.server;

import org.apache.commons.codec.binary.Base64;
import org.promasi.network.tcp.ITcpClientListener;
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
	 * @param client
	 * @throws NullArgumentException
	 */
	public ProMaSiClient(TcpClient client,IClientState clientState)throws NullArgumentException{
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
	}

	/**
	 *
	 * @param message
	 * @return true if message was sent, false otherwise.
	 */
	public synchronized boolean sendMessage(String message){
		if(message==null){
			return false;
		}
		
		//System.out.print("Message "+message);
		Base64 base64=new Base64();
		byte data[]=base64.encode(message.getBytes());
		String temp=new String(data);
		String result=new String("");
		for(int i=0;i<temp.length();i++)
		{
			char ch=temp.charAt(i);
			if(ch!='\n' && ch!='\r')
			{
				result=result+ch;
			}
		}
		
		result=result+"\r\n";
		//System.out.print("Encoded to "+result+"\n");
		return _client.sendMessage(result);
	}
	
	/**
	 * 
	 * @param clientState
	 * @throws NullArgumentException
	 */
	protected synchronized void changeState(IClientState clientState)throws NullArgumentException{
		if(clientState==null){
			throw new NullArgumentException("Wrong argument clientState==null");
		}
		
		_clientState=clientState;
		_clientState.onSetState(this);
	}

	/**
	 * This method will close the connection with current user and will terminate the receive thread.
	 * @return true if the connection was successfully closed, false otherwise.
	 */
	public synchronized boolean disconnect(){
		return _client.disconnect();
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
	 * @throws NullArgumentException
	 */
	public synchronized boolean equals(TcpClient client)throws NullArgumentException{
		if(client==_client){
			return true;
		}
		
		return false;
	}
	
}

