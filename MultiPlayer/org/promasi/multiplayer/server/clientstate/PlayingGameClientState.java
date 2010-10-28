/**
 *
 */
package org.promasi.multiplayer.server.clientstate;

import java.net.ProtocolException;
import java.util.HashMap;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSi;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.Game;
import org.promasi.network.protocol.client.request.GetGameStatsRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.request.SetGameValuesRequest;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.SetGameValuesResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.shell.Story.Story;

/**
 * @author m1cRo
 *
 */
public class PlayingGameClientState extends AbstractClientState {

	/**
	 *
	 */
	private ProMaSi _promasi;

	/**
	 *
	 */
	private Game _game;

	/**
	 *
	 */
	public PlayingGameClientState(ProMaSi promasi,Story gameStory)throws IllegalArgumentException,NullArgumentException
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi==null");
		}

		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_promasi=promasi;
		_game=_promasi.getGame(gameStory.getName());
	}

	/* (non-Javadoc)
	 * @see org.promasi.protocol.state.IProtocolState#OnReceive(org.promasi.server.ProMaSiClient, java.lang.String)
	 */
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
			if(object instanceof SetGameValuesRequest)
			{
				SetGameValuesRequest request=(SetGameValuesRequest)object;
				HashMap<String,Double> invalidValues=_game.setGameValues(request.getValues());
				if(invalidValues==null)
				{
					client.sendMessage(new SetGameValuesResponse().toProtocolString());
				}
				else
				{
					client.sendMessage(new SetGameValuesResponse(invalidValues).toProtocolString());
				}
			}
			else if(object instanceof GetGameStatsRequest)
			{
				
			}
			else
			{

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
