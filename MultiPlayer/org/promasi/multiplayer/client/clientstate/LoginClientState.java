package org.promasi.multiplayer.client.clientstate;

import java.io.IOException;
import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.ProjectManager;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.server.clientstate.JoinGameClientState;
import org.promasi.network.protocol.client.request.LoginRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.LoginResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.shell.IPlayMode;
import org.promasi.ui.promasiui.promasidesktop.playmode.StorySelectorFrame;

public class LoginClientState extends AbstractClientState {
	
	IPlayMode _currentPlayMode;
	
	/**
	 * 
	 * @param client
	 * @param projectManager
	 * @throws NullArgumentException
	 */
	public LoginClientState(IPlayMode playMode)throws NullArgumentException{
		if(playMode==null)
		{
			throw new NullArgumentException("Wrong argument playMode==null");
		}
		
		_currentPlayMode=playMode;
	}
	
	
	@Override
	public void onReceive(ProMaSiClient client, String recData) throws NullArgumentException 
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof LoginResponse)
			{
				LoginResponse loginResponse=(LoginResponse)object;
				ProjectManager projectManager=loginResponse.getProjectManager();
				if(projectManager!=null)
				{
					try {
						StorySelectorFrame storySelector = new StorySelectorFrame( projectManager,_currentPlayMode );
						storySelector.setVisible( true );
						
						changeClientState(client,new ChooseGameClientState(_currentPlayMode));
						client.sendMessage(new LoginResponse(projectManager).toXML());
						
					} catch (NullArgumentException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else
			{
				client.sendMessage(new WrongProtocolResponse().toXML());
				client.disconnect();
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toXML());
			client.disconnect();
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
			client.disconnect();
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
			client.disconnect();
		}
	}

}
