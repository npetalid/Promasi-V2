/**
 * 
 */
package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.promasi.game.GameModelMemento;
import org.promasi.game.company.CompanyMemento;
import org.promasi.game.company.EmployeeMemento;
import org.promasi.game.company.EmployeeTaskMemento;
import org.promasi.game.company.MarketPlaceMemento;
import org.promasi.game.multiplayer.IMultiPlayerGame;
import org.promasi.game.multiplayer.IServerGameListener;
import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.game.project.ProjectMemento;
import org.promasi.network.tcp.NetworkException;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.GameCanceledResponse;
import org.promasi.protocol.messages.GameStartedRequest;
import org.promasi.protocol.messages.GameStartedResponse;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.LeaveGameRequest;
import org.promasi.protocol.messages.MessageRequest;
import org.promasi.protocol.messages.UpdateGamePlayersListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.ProMaSiServer;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class WaitingGameClientState implements IServerGameListener, IClientListener
{
	/**
	 * 
	 */
	private MultiPlayerGame _game;
	
	/**
	 * 
	 */
	private String _clientId;
	
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private ProMaSiServer _server;
	
	/**
	 * 
	 * @param clientId
	 * @param game
	 * @throws NullArgumentException
	 */
	public WaitingGameClientState(String clientId, ProMaSiServer server, ProMaSiClient client, String gameId, MultiPlayerGame game)throws NullArgumentException{
		if(game==null){
			throw new NullArgumentException("Wrong argument game==null");
		}
		
		if(clientId==null){
			throw new NullArgumentException("Wrong argument clientId==null");
		}
		
		if(client==null){
			throw new NullArgumentException("Wrong argument cleint==null");
		}
		
		if(server==null){
			throw new NullArgumentException("Wrong argument server==null");
		}
		
		if(gameId==null){
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		_gameId=gameId;
		_server=server;
		_client=client;
		_game=game;
		_game.addListener(this);
		_clientId=clientId;
		_client.addListener(this);
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof GameStartedResponse){

			}else if(object instanceof MessageRequest){
				MessageRequest request=(MessageRequest)object;
				if(request.getMessage()==null){
					client.sendMessage(new WrongProtocolResponse());
					client.disconnect();
				}else{
					_game.sendMessage(_clientId, request.getMessage());
				}
			}else if(object instanceof GameCanceledResponse){
				client.removeListener(this);
				client.addListener(new ChooseGameClientState(_server, client, _clientId));
			}else if(object instanceof LeaveGameRequest){
				_server.leaveGame(_gameId, _clientId);
				client.removeListener(this);
				client.addListener(new ChooseGameClientState(_server, client, _clientId));
			}else{
				client.sendMessage(new WrongProtocolResponse());
			}
		}catch(NetworkException e){
			client.sendMessage(new InternalErrorResponse());
			client.disconnect();
		}catch(IllegalArgumentException e){
			client.sendMessage(new InternalErrorResponse());
			client.disconnect();
		}

	}

	@Override
	public void gameStarted(String playerId, IMultiPlayerGame game,
			GameModelMemento gameModel, DateTime dateTime) {
		if(playerId.equals(_clientId)){
			try {
				_game.removeListener(this);
				_client.removeListener(this);
				_client.addListener(new PlayingGameClientState(_server, _client, _clientId, _game));
			} catch (NetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			gameModel.setProjects(null);
			_client.sendMessage(new GameStartedRequest(gameModel, dateTime.toString()));
		}
	}

	@Override
	public void projectAssigned(String playerId, IMultiPlayerGame game,
			CompanyMemento company, ProjectMemento project,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(String playerId, IMultiPlayerGame game,
			CompanyMemento company, ProjectMemento project,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(String playerId, IMultiPlayerGame game,
			MarketPlaceMemento marketPlace, CompanyMemento company,
			EmployeeMemento employee, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeDischarged(String playerId, IMultiPlayerGame game,
			MarketPlaceMemento marketPlace, CompanyMemento company,
			EmployeeMemento employee, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTasksAssigned(String playerId, IMultiPlayerGame game,
			CompanyMemento company, EmployeeMemento employee,
			List<EmployeeTaskMemento> employeeTasks, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskDetached(String playerId, IMultiPlayerGame game,
			MarketPlaceMemento marketPlace, CompanyMemento company,
			EmployeeMemento employee,
			EmployeeTaskMemento employeeTask, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String playerId, IMultiPlayerGame game,
			CompanyMemento company, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteWorkingStep(String playerId, IMultiPlayerGame game,
			CompanyMemento company, ProjectMemento assignedProject,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(String playerId, IMultiPlayerGame game, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageSent(String playerId, IMultiPlayerGame game, String message) {
		MessageRequest request=new MessageRequest(playerId, message);
		_client.sendMessage(request);
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		_game.leaveGame(_clientId);
		_client.removeListener(this);
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		_game.leaveGame(_clientId);
		_client.removeListener(this);
	}

	@Override
	public void playersListUpdated( IMultiPlayerGame game, List<String> gamePlayers) {
		_client.sendMessage(new UpdateGamePlayersListRequest(gamePlayers));
	}

	@Override
	public void gameFinished(Map<String, GameModelMemento> gameModels) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(String playerId, IMultiPlayerGame game,
			CompanyMemento company, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}
}
