/**
 *
 */
package org.promasi.server.clientstate;

import java.net.ProtocolException;
import java.util.LinkedList;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.protocol.request.CreateNewGameRequest;
import org.promasi.network.protocol.request.JoinGameRequest;
import org.promasi.network.protocol.request.RequestBuilder;
import org.promasi.network.protocol.request.RetreiveGamesRequest;
import org.promasi.protocol.response.CreateNewGameResponse;
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
			if(object instanceof RetreiveGamesRequest)
			{
				LinkedList<String> gameList=_promasi.retreiveGames();
				RetreiveGamesResponse response=new RetreiveGamesResponse(gameList);
				client.sendMessage(response.toXML());
			}
			else if( object instanceof JoinGameRequest)
			{
				JoinGameRequest joinRequest=(JoinGameRequest)object;
				Game game=_promasi.getGame(joinRequest.getGameId());
				game.addPlayer(client);
				if(!client.sendMessage(new JoinGameResponse().toXML()))
				{
					client.disonnect();
				}
				changeClientState(client,new WaitingGameClientState(_promasi,game));
			}
			else if(object instanceof CreateNewGameRequest)
			{
				CreateNewGameRequest request=(CreateNewGameRequest)object;
				Game game=new Game(request.getGameId(),client,request.getGameModel().toXML());
				try
				{
					_promasi.createNewGame(game);
					client.sendMessage(new CreateNewGameResponse(true).toXML());
					changeClientState(client,new GameMasterClientState(_promasi,game));
				}
				catch(IllegalArgumentException e)
				{
					client.sendMessage(new CreateNewGameResponse(false).toXML());
				}
			}
			else
			{
				client.sendMessage(new JoinGameResponse("Wrong protocol").toXML());
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
	}
}
