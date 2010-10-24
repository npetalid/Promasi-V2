/**
 *
 */
package org.promasi.multiplayer.server.clientstate;

import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSi;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.Game;
import org.promasi.multiplayer.game.GameModel;
import org.promasi.network.protocol.client.request.CreateNewGameRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.CreateNewGameResponse;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;

/**
 * @author m1cRo
 *
 */
public class GameMasterClientState extends AbstractClientState {

	/**
	 *
	 */
	private ProMaSi _promasi;


	/**
	 *
	 * @param promasi
	 * @param game
	 * @throws NullArgumentException
	 */
	public GameMasterClientState(ProMaSi promasi)throws NullArgumentException
	{
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi==null");
		}
		
		_promasi=promasi;
	}

	/* (non-Javadoc)
	 * @see org.promasi.server.client.state.IClientState#onReceive(org.promasi.server.core.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException {
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
			if(object instanceof CreateNewGameRequest)
			{
				CreateNewGameRequest request=(CreateNewGameRequest)object;
				Game game=new Game(client,new GameModel(request.getGameStory()));
				try
				{
					_promasi.createNewGame(request.getGameStory().getName(),game);
					client.sendMessage(new CreateNewGameResponse(request.getGameStory()).toProtocolString());
					changeClientState(client,new WaitingGameClientState(_promasi,game));
				}
				catch(IllegalArgumentException e)
				{
					//client.sendMessage(new CreateNewGameResponse(false).toProtocolString());
				}
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
