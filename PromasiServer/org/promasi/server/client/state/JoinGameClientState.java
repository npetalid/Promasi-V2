/**
 *
 */
package org.promasi.server.client.state;

import java.net.ProtocolException;
import java.util.LinkedList;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.protocol.request.CreateNewGameRequest;
import org.promasi.protocol.request.JoinGameRequest;
import org.promasi.protocol.request.RequestBuilder;
import org.promasi.protocol.request.RetreiveGamesRequest;
import org.promasi.protocol.response.InternalErrorResponse;
import org.promasi.protocol.response.JoinGameResponse;
import org.promasi.protocol.response.RetreiveGamesResponse;
import org.promasi.protocol.response.WrongProtocolResponse;
import org.promasi.server.core.AbstractClientState;
import org.promasi.server.core.ProMaSi;
import org.promasi.server.core.ProMaSiClient;

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

		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof RetreiveGamesRequest)
			{
				LinkedList<String> gameList=_promasi.retreiveGames();
				RetreiveGamesResponse response=new RetreiveGamesResponse(gameList);
				client.sendMessage(response.toXML());
			}
			else if( object instanceof JoinGameRequest)
			{
				JoinGameRequest joinRequest=(JoinGameRequest)object;
				//changeClientState(client,new PlayingGameClientState(_promasi,joinRequest.getGameId()));
				client.sendMessage(new JoinGameResponse().toXML());
			}
			else if(object instanceof CreateNewGameRequest)
			{

			}
			else
			{
				client.sendMessage(new JoinGameResponse("Wrong protocol").toXML());
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toXML());
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toXML());
		}
	}
}
