/**
 * 
 */
package org.promasi.server.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.promasi.game.SerializableGameModel;
import org.promasi.game.company.SerializableCompany;
import org.promasi.game.company.SerializableEmployee;
import org.promasi.game.company.SerializableEmployeeTask;
import org.promasi.game.company.SerializableMarketPlace;
import org.promasi.game.multiplayer.IMultiPlayerGameListener;
import org.promasi.game.multiplayer.IMultiPlayerGame;
import org.promasi.game.multiplayer.MultiPlayerGame;
import org.promasi.game.project.SerializableProject;
import org.promasi.protocol.messages.CancelGameRequest;
import org.promasi.protocol.messages.GameStartedRequest;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.MessageRequest;
import org.promasi.protocol.messages.StartGameRequest;
import org.promasi.protocol.messages.UpdateGamePlayersListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.server.AbstractClientState;
import org.promasi.server.ProMaSiClient;
import org.promasi.server.ProMaSiServer;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class WaitingPlayersClientState extends AbstractClientState implements IMultiPlayerGameListener
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
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private ProMaSiServer _server;
	
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 * @param game
	 * @throws NullArgumentException
	 */
	public WaitingPlayersClientState(String clientId, String gameId, ProMaSiClient client, MultiPlayerGame game, ProMaSiServer server)throws NullArgumentException{
		if(clientId==null){
			throw new NullArgumentException("Wrong argument clientId==null");
		}
		
		if(game==null){
			throw new NullArgumentException("Wrong argument game==null");
		}
		
		if(client==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		if(server==null){
			throw new NullArgumentException("Wrong argument server==null");
		}
		
		if(gameId==null){
			throw new NullArgumentException("Wrong argument gameId==null");
		}
		
		_game=game;
		_server=server;
		_game.addListener(this);
		_clientId=clientId;
		_client=client;
		_gameId=gameId;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof StartGameRequest){
				if(!_server.startGame(_clientId, _gameId)){
					client.sendMessage(new InternalErrorResponse().serialize());
					client.disconnect();
				}
			}else if(object instanceof MessageRequest){
				MessageRequest request=(MessageRequest)object;
				if( request.getMessage()==null ){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}else{
					_game.sendMessage(_clientId, request.getMessage());
				}
			}else if(object instanceof CancelGameRequest){
				_server.cancelGame(_gameId);
				changeClientState(client, new ChooseGameClientState(_server, _clientId));
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
		}
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameStarted(String clientId, IMultiPlayerGame game, SerializableGameModel gameModel, DateTime dateTime) {
		if(_clientId.equals(clientId)){
			try {
				_game.removeListener(this);
				changeClientState(_client, new PlayingGameClientState(_server, _client, _clientId, _game));
			} catch (NullArgumentException e) {
				//Logger
			}
			
			gameModel.setProjects(null);
			_client.sendMessage(new GameStartedRequest(gameModel, dateTime.toString()).serialize());
		}
	}

	@Override
	public void projectAssigned(String clientId, IMultiPlayerGame game,
			SerializableCompany company, SerializableProject project,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void projectFinished(String clientId, IMultiPlayerGame game,
			SerializableCompany company, SerializableProject project,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeHired(String clientId, IMultiPlayerGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeDischarged(String clientId, IMultiPlayerGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTasksAssigned(String clientId, IMultiPlayerGame game,
			SerializableCompany company, SerializableEmployee employee,
			List<SerializableEmployeeTask> employeeTasks, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void employeeTaskDetached(String clientId, IMultiPlayerGame game,
			SerializableMarketPlace marketPlace, SerializableCompany company,
			SerializableEmployee employee,
			SerializableEmployeeTask employeeTask, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void companyIsInsolvent(String clientId, IMultiPlayerGame game,
			SerializableCompany company, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onExecuteStep(String clientId, IMultiPlayerGame game,
			SerializableCompany company, SerializableProject assignedProject,
			DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick(String clientId, IMultiPlayerGame game, DateTime dateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageSent(String clientId, IMultiPlayerGame game, String message) {
		MessageRequest request=new MessageRequest(clientId, message);
		_client.sendMessage(request.serialize());
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		try {
			_game.leaveGame(_clientId);
		} catch (NullArgumentException e) {
			//Logger
		}
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		try {
			_game.leaveGame(_clientId);
		} catch (NullArgumentException e) {
			//Logger
		}
	}

	@Override
	public void playersListUpdated( IMultiPlayerGame game, List<String> gamePlayers) {
		_client.sendMessage(new UpdateGamePlayersListRequest(gamePlayers).serialize());
		
	}

	@Override
	public void gameFinished(Map<String, SerializableGameModel> gameModels) {
		// TODO Auto-generated method stub
		
	}

}
