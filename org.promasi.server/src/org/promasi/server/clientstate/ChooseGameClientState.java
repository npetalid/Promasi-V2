/**
 * 
 */
package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.game.project.Project;
import org.promasi.game.project.SerializableProject;
import org.promasi.protocol.messages.CreateGameFailedResponse;
import org.promasi.protocol.messages.CreateGameRequest;
import org.promasi.protocol.messages.CreateGameResponse;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.JoinGameFailedResponse;
import org.promasi.protocol.messages.JoinGameRequest;
import org.promasi.protocol.messages.JoinGameResponse;
import org.promasi.protocol.messages.UpdateAvailableGameListRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.AbstractClientState;
import org.promasi.server.ProMaSiClient;
import org.promasi.server.ProMaSiServer;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class ChooseGameClientState extends AbstractClientState
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
					MultiPlayerGame game=_server.joinGame(_clientId, request.getGameId());
					JoinGameResponse response=new JoinGameResponse(game.getGameName(), game.getGameDescription(), game.getGamePlayers());
					changeClientState(client, new WaitingGameClientState(_clientId,_server, client,request.getGameId(), game));
					client.sendMessage(response.serialize());
				}catch(IllegalArgumentException e){
					client.sendMessage(new JoinGameFailedResponse().serialize());
				}

			}else if(object instanceof CreateGameRequest){
				CreateGameRequest request=(CreateGameRequest)object;
				SerializableGameModel gameModel=request.getGameModel();
				if(gameModel==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
					return;
				}
				
				SerializableMarketPlace marketPlace=gameModel.getMarketPlace();
				if(marketPlace==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				SerializableCompany company=gameModel.getCompany();
				if(company==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				Queue<SerializableProject> sProjects=gameModel.getProjects();
				if(sProjects==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				Queue<Project> projects=new LinkedList<Project>(); 
				for(SerializableProject currentProject : sProjects){
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
					changeClientState( client, new WaitingPlayersClientState(_clientId, request.getGameId(), client, game, _server) );
				}else{
					client.sendMessage(new CreateGameFailedResponse().serialize());
				}
			}else if(object instanceof UpdateAvailableGameListRequest){
				UpdateGameListRequest request=new UpdateGameListRequest(_server.getAvailableGames());
				client.sendMessage(request.serialize());
			}else{
				client.sendMessage(new WrongProtocolResponse().serialize());
				client.disconnect();
			}
		}catch(NullArgumentException e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}catch(IllegalArgumentException e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}catch(SerializationException e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
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
