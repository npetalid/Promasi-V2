/**
 *
 */
package org.promasi.multiplayer.server.clientstate;

import java.net.ProtocolException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSi;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.ChooseGameMasterStateRequest;
import org.promasi.network.protocol.client.request.JoinGameRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.request.RetreiveGameListRequest;
import org.promasi.network.protocol.client.response.ChooseGameMasterStateResponse;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.JoinGameFailedResponse;
import org.promasi.network.protocol.client.response.JoinGameResponse;
import org.promasi.network.protocol.client.response.RetreiveGameListResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.shell.Story.StoriesPool;
import org.promasi.shell.Story.Story;


/**
 * @author m1cRo
 *
 */
public class JoinGameClientState extends AbstractClientState {

	/**
	 *
	 */
	private ProMaSi _promasi;

	/**
	 *
	 * @param promasi
	 */
	public JoinGameClientState(ProMaSi promasi)
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi");
		}
		
		_promasi=promasi;
	}

	/* (non-Javadoc)
	 * @see org.promasi.protocol.state.IProtocolState#OnReceive(org.promasi.server.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException
	{
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}

		//Check for current request type.
		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof RetreiveGameListRequest)
			{
				RetreiveGameListResponse response=new RetreiveGameListResponse(_promasi.retreiveGames());
				client.sendMessage(response.toProtocolString());
			}
			else if( object instanceof JoinGameRequest)
			{
				JoinGameRequest joinRequest=(JoinGameRequest)object;
				if(!_promasi.joinGame(client, joinRequest.getGameId()))
				{
					client.sendMessage(new JoinGameFailedResponse().toProtocolString());
				}else{
					client.sendMessage(new JoinGameResponse().toProtocolString());
					changeClientState(client,new WaitingGameClientState(_promasi.getGame(joinRequest.getGameId())));
				}
				
			}
			else if( object instanceof ChooseGameMasterStateRequest)
			{
				List<Story> stories=StoriesPool.getAllStories();
				Map<String,String> availableGames=new TreeMap<String,String>();
				for(Story story : stories)
				{
					availableGames.put(story.getName(), story.getInfoString());
				}
				
				client.sendMessage(new ChooseGameMasterStateResponse(availableGames).toProtocolString());
				changeClientState(client, new GameMasterClientState(_promasi));
			}
			else
			{
				client.sendMessage(new WrongProtocolResponse().toProtocolString());
				client.disconnect();
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toProtocolString());
			client.disconnect();
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
	}
}
