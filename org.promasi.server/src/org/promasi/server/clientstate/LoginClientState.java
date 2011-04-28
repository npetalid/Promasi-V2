package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.LoginFailedResponse;
import org.promasi.protocol.messages.LoginRequest;
import org.promasi.protocol.messages.LoginResponse;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.AbstractClientState;
import org.promasi.server.ProMaSiClient;
import org.promasi.server.ProMaSiServer;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * 
 * @author m1cRo
 *
 */
public class LoginClientState extends AbstractClientState {

	/**
	 * 
	 */
	private ProMaSiServer _server;
	
	/**
	 * 
	 * @param server
	 * @throws NullArgumentException
	 */
	public LoginClientState(ProMaSiServer server)throws NullArgumentException{
		if(server==null){
			throw new NullArgumentException("Wrong argument server==null");
		}
		
		_server=server;
	}
	
	@Override
	public synchronized void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof LoginRequest){
				LoginRequest request=(LoginRequest)object;
				if(request.getClientId()==null || request.getPassword()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}else{
					if(_server.login(request.getClientId(), client)){
						LoginResponse response=new LoginResponse(_server.getAvailableGames());
						changeClientState(client, new ChooseGameClientState(_server,request.getClientId()));
						client.sendMessage(response.serialize());
					}else{
						client.sendMessage(new LoginFailedResponse().serialize());
					}
				}
				
			}else{
				
			}
		}catch(NullArgumentException e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

}
