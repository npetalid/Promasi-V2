package org.promasi.multiplayer.server.clientstate;

import java.lang.reflect.InvocationTargetException;
import java.net.ProtocolException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSi;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.Game;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.request.StartGameRequest;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.StartGameResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;

public class WaitingForPlayersClientState extends AbstractClientState 
{
	/**
	 * 
	 */
	Game _game;
	
	/**
	 * 
	 * @param game
	 * @throws NullArgumentException
	 */
	public WaitingForPlayersClientState(ProMaSi promasi, Game game)throws NullArgumentException
	{
		if(game==null)
		{
			throw new NullArgumentException("Wrong argument game==null");
		}
		
		if(promasi==null)
		{
			throw new NullArgumentException("Wrong argument promasi==null");
		}
		
		_game=game;
	}
	
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
			if(object instanceof StartGameRequest)
			{
				_game.startGame();
				changeClientState( client,new PlayingGameClientState( _game ) );
				client.sendMessage( new StartGameResponse(_game.getCompany(), _game.getMarketPlace()).toProtocolString() );
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
		catch (IllegalAccessException e) {
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		} catch (InstantiationException e) {
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		} catch (InvocationTargetException e) {
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		} catch (NoSuchMethodException e) {
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
	}
}
