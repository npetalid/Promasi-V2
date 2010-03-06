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
import org.promasi.server.core.game.Game;

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
	public PlayingGameClientState(ProMaSi promasi,String gameId)throws IllegalArgumentException,NullArgumentException
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi==null");
		}

		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument promasi==null");
		}
		_promasi=promasi;
		_game=_promasi.getGame(gameId);
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
