/**
 *
 */
package org.promasi.multiplayer.server.clientstate;

import java.net.ProtocolException;
import java.util.LinkedList;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.Game;
import org.promasi.multiplayer.server.ProMaSi;
import org.promasi.network.protocol.client.request.CreateNewGameRequest;
import org.promasi.network.protocol.client.request.JoinGameRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.request.RetreiveGamesRequest;
import org.promasi.network.protocol.client.response.CreateNewGameResponse;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.JoinGameResponse;
import org.promasi.network.protocol.client.response.RetreiveGamesResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;

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
				if(!_promasi.joinGame(client, joinRequest.getGameId()))
				{
					//ToDo send failed response
					return;
				}
				
				if(!client.sendMessage(new JoinGameResponse().toXML()))
				{
					client.disconnect();
				}
				
				changeClientState(client,new WaitingGameClientState(_promasi,_promasi.getGame(joinRequest.getGameId())));
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
