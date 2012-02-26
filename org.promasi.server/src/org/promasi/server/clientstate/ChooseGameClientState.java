/**
 * 
 */
package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.promasi.game.GameModelMemento;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.MarketPlaceMemento;
import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.game.project.Project;
import org.promasi.game.project.ProjectMemento;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.CreateGameRequest;
import org.promasi.protocol.messages.CreateGameResponse;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.JoinGameFailedResponse;
import org.promasi.protocol.messages.JoinGameRequest;
import org.promasi.protocol.messages.JoinGameResponse;
import org.promasi.protocol.messages.UpdateAvailableGameListRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.ProMaSiServer;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class ChooseGameClientState  implements IClientListener 
{
	/**
	 * 
	 */
	private ProMaSiServer _server;
	
	/**
	 * 
	 */
	private String _clientId;
	
	/**
	 * 
	 * @param server
	 * @throws NullArgumentException
	 */
	public ChooseGameClientState(ProMaSiServer server, String clientId)throws NullArgumentException{
		if(server==null){
			throw new NullArgumentException("Wrong argument server==null");
		}
		
		if(clientId==null){
			throw new NullArgumentException("Wrong argument clientId==null");
		}
		
		_clientId=clientId;
		_server=server;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof JoinGameRequest){
				try{
					JoinGameRequest request=(JoinGameRequest)object;
					MultiPlayerGame game;
					game = _server.joinGame(_clientId, request.getGameId());

					JoinGameResponse response=new JoinGameResponse(game.getGameName(), game.getGameDescription(), game.getGamePlayers());
					client.removeListener(this);
					client.addListener(new WaitingGameClientState(_clientId,_server, client,request.getGameId(), game));
					client.sendMessage(response.serialize());
				}catch(IllegalArgumentException e){
					client.sendMessage(new JoinGameFailedResponse().serialize());
				}

			}else if(object instanceof CreateGameRequest){
				CreateGameRequest request=(CreateGameRequest)object;
				GameModelMemento gameModel=request.getGameModel();
				if(gameModel==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
					return;
				}
				
				MarketPlaceMemento marketPlace=gameModel.getMarketPlace();
				if(marketPlace==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				CompanyMemento company=gameModel.getCompany();
				if(company==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				Queue<ProjectMemento> sProjects=gameModel.getProjects();
				if(sProjects==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				Queue<Project> projects=new LinkedList<Project>(); 
				for(ProjectMemento currentProject : sProjects){
					projects.add(currentProject.getProject());
				}
				
				MultiPlayerGame game=new MultiPlayerGame(	_clientId, request.getGameId()
															, gameModel.getGameDescription()
															, marketPlace.getMarketPlace()
															, company.getCompany()
															, projects);
				
				if(_server.createGame(request.getGameId(), game))
				{
					CreateGameResponse response=new CreateGameResponse(request.getGameId(), gameModel.getGameDescription(), game.getGamePlayers());
					client.sendMessage(response.serialize());
					client.removeListener(this);
					client.addListener(new WaitingPlayersClientState(_clientId, request.getGameId(), client, game, _server));
				}
			}else if(object instanceof UpdateAvailableGameListRequest){
				UpdateGameListRequest request=new UpdateGameListRequest(_server.getAvailableGames());
				client.sendMessage(request.serialize());
			}else{
				client.sendMessage(new WrongProtocolResponse().serialize());
				client.disconnect();
			}
		}catch(Exception e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

}
