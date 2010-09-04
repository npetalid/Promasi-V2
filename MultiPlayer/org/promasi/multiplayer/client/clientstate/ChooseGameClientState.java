/**
 * 
 */
package org.promasi.multiplayer.client.clientstate;



import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.network.protocol.server.request.UpdateGamesListRequest;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.playmodes.multiplayermode.MultiPlayerScorePlayMode;

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
				UpdateGamesListRequest request=(UpdateGamesListRequest)object;
				//_playMode.updateGameList(request.getGames());
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
