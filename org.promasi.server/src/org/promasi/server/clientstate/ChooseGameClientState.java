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
import org.promasi.network.tcp.NetworkException;
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
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author m1cRo
 * Represent the client state in which a user will chose
 * the game from the list of available games exposed by the
 * server.
 */
public class ChooseGameClientState  implements IClientListener 
{
	/**
	 * Instance of {@link = ProMaSiServer} needed to
	 * handle the users commands.
	 */
	private ProMaSiServer _server;
	
	/**
	 * The client id, user insert it in the login
	 * procedure.
	 */
	private String _clientId;
	
	/**
	 * Instance of {@link ILogger} interface implementation,
	 * need for logging.
	 */
	private ILogger _logger = LoggerFactory.getInstance(ChooseGameClientState.class);
	
	/**
	 * Instance of {@link ProMaSiClient}
	 */
	private ProMaSiClient _client;
	
	/**
	 * Constructor will initialize the object.
	 * @param server the server which is responsible to handle the given client.
	 * @param client Instance of {@link ProMaSiClient}
	 * @param clientId The client id.
	 * @throws NetworkException in case of invalid arguments or initialization errors.
	 */
	public ChooseGameClientState( ProMaSiServer server, ProMaSiClient client, String clientId)throws NetworkException{
		if(server==null){
			_logger.error("Initialization failed because a wrong argument server == null");
			throw new NetworkException("Wrong argument server==null");
		}
		
		if(clientId==null){
			_logger.error("Initialization failed because a wrong argument clientId == null");
			throw new NetworkException("Wrong argument clientId==null");
		}
		
		if( client == null ){
			_logger.error("Initialization failed because a wrong argument client == null");
			throw new NetworkException("Wrong argument client==null");
		}
		
		_clientId=clientId;
		_server=server;
		_client=client;
		_client.addListener(this);
		_logger.debug("Initialization complete");
	}
	
	/**
	 * Accepted requests are:
	 * {@link JoinGameRequest}, 
	 * {@link CreateGameRequest},
	 * {@link UpdateAvailableGameListRequest},
	 * In case of any other type of request client
	 * will be disconnected.
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof JoinGameRequest){
				try{
					_logger.info("Received message :'" + JoinGameRequest.class.toString() + "'");
					JoinGameRequest request=(JoinGameRequest)object;
					MultiPlayerGame game;
					game = _server.joinGame(_clientId, request.getGameId());

					JoinGameResponse response=new JoinGameResponse(game.getGameName(), game.getGameDescription(), game.getGamePlayers());
					client.removeListener(this);
					client.addListener(new WaitingGameClientState(_clientId,_server, client,request.getGameId(), game));
					client.sendMessage(response);
				}catch(IllegalArgumentException e){
					client.sendMessage(new JoinGameFailedResponse());
				}

			}else if(object instanceof CreateGameRequest){
				_logger.info("Received message :'" + CreateGameRequest.class.toString() + "'");
				CreateGameRequest request=(CreateGameRequest)object;
				GameModelMemento gameModel=request.getGameModel();
				if(gameModel==null){
					_logger.warn("Invalid message detected gameModel == null, client will be disconnected");
					client.sendMessage(new WrongProtocolResponse());
					client.disconnect();
					return;
				}
				
				MarketPlaceMemento marketPlace=gameModel.getMarketPlace();
				if(marketPlace==null){
					_logger.warn("Invalid message detected marketPlace == null, client will be disconnected");
					client.sendMessage(new WrongProtocolResponse());
					client.disconnect();
				}
				
				CompanyMemento company=gameModel.getCompany();
				if(company==null){
					_logger.warn("Invalid message detected company == null, client will be disconnected");
					client.sendMessage(new WrongProtocolResponse());
					client.disconnect();
				}
				
				Queue<ProjectMemento> sProjects=gameModel.getProjects();
				if(sProjects==null){
					_logger.warn("Invalid message detected projects == null, client will be disconnected");
					client.sendMessage(new WrongProtocolResponse());
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
					client.sendMessage(response);
					client.removeListener(this);
					client.addListener(new WaitingPlayersClientState(_clientId, request.getGameId(), client, game, _server));
				}else{
					_logger.warn("Create game failed");
				}
			}else if(object instanceof UpdateAvailableGameListRequest){
				_logger.info("Received message :'" + UpdateAvailableGameListRequest.class.toString() + "'");
				UpdateGameListRequest request=new UpdateGameListRequest(_server.getAvailableGames());
				client.sendMessage(request);
			}else{
				_logger.warn("Received an invalid message type '" +object.toString()+"'");
				client.sendMessage(new WrongProtocolResponse());
				client.disconnect();
			}
		}catch(Exception e){
			_logger.warn("Internal error client will be disconnected");
			client.sendMessage(new InternalErrorResponse());
			client.disconnect();
		}
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		_client.removeListener(this);
		_logger.debug("Client disconnected");
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		_client.removeListener(this);
		_logger.debug("Client connection error");
	}

}
