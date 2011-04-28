/**
 * 
 */
package org.promasi.client.playmode.multiplayer.client.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;
import org.promasi.client.gui.IWaitingPlayersDialogListener;
import org.promasi.client.gui.WaitingPlayersDialog;
import org.promasi.client.playmode.multiplayer.AbstractClientState;
import org.promasi.client.playmode.multiplayer.MultiPlayerGame;
import org.promasi.client.playmode.multiplayer.ProMaSiClient;
import org.promasi.protocol.messages.CancelGameRequest;
import org.promasi.protocol.messages.CancelGameResponse;
import org.promasi.protocol.messages.GameStartedRequest;
import org.promasi.protocol.messages.GameStartedResponse;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.MessageRequest;
import org.promasi.protocol.messages.StartGameRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.UpdateGamePlayersListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class WaitingPlayersClientState extends AbstractClientState implements IWaitingPlayersDialogListener 
{
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	private WaitingPlayersDialog _waitingPlayersDialog;
	
	/**
	 * 
	 */
	private String _gameDescription;
	
	/**
	 * 
	 */
	private List<String> _players;
	
	/**
	 * 
	 */
	private boolean _isStartGameButtonPressed;
	
	/**
	 * 
	 * @param shell
	 * @param client
	 * @throws NullArgumentException
	 */
	public WaitingPlayersClientState(Shell shell,ProMaSiClient client, String gameId, String gameDescription,List<String> players)throws NullArgumentException{
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		if(client==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		if(gameId==null){
			throw new NullArgumentException("Wrong argument gameName==null");
		}
		
		if(gameDescription==null){
			throw new NullArgumentException("Wrong argument gameDescription==null");
		}
		
		if(players==null){
			throw new NullArgumentException("Wrong argument players==null");
		}
		
		_client=client;
		_shell=shell;
		_gameId=gameId;
		_gameDescription=gameDescription;
		_players=players;
		_isStartGameButtonPressed=false;
	}
	
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof GameStartedRequest){
				GameStartedRequest request=(GameStartedRequest)object;
				if(request.getGameModel()==null || request.getDateTime()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
					return;
				}
				
				PlayingGameClientState state=new PlayingGameClientState(_shell, new MultiPlayerGame(client),client, request.getGameModel(),new DateTime(request.getDateTime()));
				changeClientState(client, state);
				client.sendMessage(new GameStartedResponse().serialize());
			}else if(object instanceof MessageRequest){
				MessageRequest request=(MessageRequest)object;
				if(request.getClientId()==null || request.getMessage()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}else{
					_waitingPlayersDialog.messageReceived(request.getClientId(), request.getMessage());
				}
			}else if(object instanceof UpdateGamePlayersListRequest){
				UpdateGamePlayersListRequest request=(UpdateGamePlayersListRequest)object;
				if(request.getPlayers()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				_waitingPlayersDialog.updatePlayersList(request.getPlayers());
			}else if(object instanceof UpdateGameListRequest){
				
			}else if(object instanceof CancelGameResponse){
				ChooseGameClientState clientState=new ChooseGameClientState(client, _shell, new HashMap<String,String>());
				changeClientState(client, clientState);
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
	public void sendButtonPressed(String messageText) {
		_client.sendMessage(new MessageRequest(null, messageText).serialize());
	}

	@Override
	public void startGameButtonPressed() {
		_isStartGameButtonPressed=true;
		_waitingPlayersDialog.close();
		StartGameRequest request=new StartGameRequest(_gameId);
		_client.sendMessage(request.serialize());
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					try {
						_waitingPlayersDialog=new WaitingPlayersDialog(_shell, SWT.DIALOG_TRIM, _gameId, _gameDescription, WaitingPlayersClientState.this, _players);
						_waitingPlayersDialog.open();
					} catch (NullArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
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


	@Override
	public void dialogClosed() {
		if(!_isStartGameButtonPressed){
			_client.sendMessage(new CancelGameRequest(_gameId).serialize());
		}
	}
}
