/**
 * 
 */
package org.promasi.multiplayer.client.clientstate;



import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.GetGamesListRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.network.protocol.server.request.UpdateGamesListRequest;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.playmodes.singleplayerscoremode.StoriesPool;

/**
 * @author m1cRo
 *
 */
public class ChooseGameClientState extends AbstractClientState{

	/**
	 * 
	 */
	private IPlayMode _playMode;
	
	/**
	 * 
	 * @param playMode
	 * @throws NullArgumentException
	 */
	public ChooseGameClientState(IPlayMode playMode)throws NullArgumentException
	{
		if( playMode==null )
		{
			throw new NullArgumentException("Wrong argument playMode=null");
		}
		
		_playMode=playMode;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.server.clientstate.IClientState#onReceive(org.promasi.server.core.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException {
		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument recData==null");
		}
		
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof UpdateGamesListRequest)
			{
				
			}
			else
			{
				client.sendMessage(new WrongProtocolResponse().toXML());
				client.disonnect();
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toXML());
			client.disonnect();
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
			client.disonnect();
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
			client.disonnect();
		}
		
		_playMode.updateStories(StoriesPool.getAllStories( ));
	}
	
	/**
	 * 
	 * @param client
	 * @return
	 */
	public boolean sendUpdateGamesListRequest(ProMaSiClient client)
	{
		if(client==null)
		{
			return false;
		}
		
		return client.sendMessage(new GetGamesListRequest().toXML());
	}

}
