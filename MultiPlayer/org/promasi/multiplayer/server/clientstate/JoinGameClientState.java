/**
 *
 */
package org.promasi.multiplayer.server.clientstate;

import java.net.ProtocolException;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSi;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.multiplayer.game.Game;
import org.promasi.network.protocol.client.request.CreateNewGameRequest;
import org.promasi.network.protocol.client.request.JoinGameRequest;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.request.RetreiveGameListRequest;
import org.promasi.network.protocol.client.response.CreateNewGameResponse;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.JoinGameResponse;
import org.promasi.network.protocol.client.response.RetreiveGameListResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.network.protocol.dtos.GameDto;
import org.promasi.shell.ui.playmode.StoriesPool;
import org.promasi.shell.ui.playmode.Story;
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
				List<GameDto> gameList=new Vector<GameDto>();
				List<Story> stories=StoriesPool.getAllStories();
				if(stories.size()>0)
				{
					Story currentStory=stories.get(0);
					Company company=currentStory.getCompany();
					company.setBoss(currentStory.getBoss());
					company.setAccountant(currentStory.getAccountant());
					company.setAdministrator(currentStory.getAdministrator());
					
					//------------------------------- TEST -------------------------------------
					gameList.add(new GameDto(company,"NewGame","Description",1,currentStory.getMarketPlace() ) );
					//------------------------------- TEST -------------------------------------
					
					RetreiveGameListResponse response=new RetreiveGameListResponse(gameList/*_promasi.retreiveGames()*/);
					client.sendMessage(response.toProtocolString());
				}

			}
			else if( object instanceof JoinGameRequest)
			{
				JoinGameRequest joinRequest=(JoinGameRequest)object;
				if(!_promasi.joinGame(client, joinRequest.getGameId()))
				{
					//ToDo send failed response
					return;
				}
				
				if(!client.sendMessage(new JoinGameResponse().toProtocolString()))
				{
					client.disconnect();
				}
				
				changeClientState(client,new WaitingGameClientState(_promasi,_promasi.getGame(joinRequest.getGameId())));
			}
			else if(object instanceof CreateNewGameRequest)
			{
				CreateNewGameRequest request=(CreateNewGameRequest)object;
				Game game=new Game(request.getGameId(),client,request.getGameModel());
				try
				{
					_promasi.createNewGame(game);
					client.sendMessage(new CreateNewGameResponse(true).toProtocolString());
					changeClientState(client,new GameMasterClientState(_promasi,game));
				}
				catch(IllegalArgumentException e)
				{
					client.sendMessage(new CreateNewGameResponse(false).toProtocolString());
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
