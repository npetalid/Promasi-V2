package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

import org.promasi.network.tcp.NetworkException;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.LoginFailedResponse;
import org.promasi.protocol.messages.LoginRequest;
import org.promasi.protocol.messages.LoginResponse;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.ProMaSiServer;

/**
 * 
 * @author m1cRo
 * Represent the Login State.
 * In this state user should insert his credentials in order to 
 * income to the ProMaSi system.
 */
public class LoginClientState implements IClientListener {

	/**
	 * Instance of {@link ProMaSiServer}
	 */
	private ProMaSiServer _server;
	
	/**
	 * Instance of {@link ProMaSiClient}.
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 * @param server
	 * @throws NetworkException
	 */
	public LoginClientState(ProMaSiServer server, ProMaSiClient client)throws NetworkException{
		if(server==null){
			throw new NetworkException("Wrong argument server==null");
		}
		
		if( client == null ){
			throw new NetworkException("Wrong argument client==null");
		}
		
		_client = client;
		_server=server;
	}
	
	/**
	 * Only the LoginRequest message is
	 * accepted. In case of invalid message
	 * client will be disconnected.
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof LoginRequest){
				LoginRequest request=(LoginRequest)object;
				if(request.getClientId()==null || request.getPassword()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}else{
					if(_server.login(request.getClientId(), client)){
						LoginResponse response=new LoginResponse(request.getClientId(), _server.getAvailableGames());
						client.removeListener(this);
						client.addListener( new ChooseGameClientState(_server, client, request.getClientId()) );
						client.sendMessage(response.serialize());
					}else{
						client.sendMessage(new LoginFailedResponse().serialize());
					}
				}
				
			}else{
				
			}
		}catch(NetworkException e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		_client.removeListener(this);
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		_client.removeListener(this);
		
	}
}
