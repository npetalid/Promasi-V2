/**
 * 
 */
package org.promasi.client.playmode.multiplayer.client.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;
import org.promasi.client.gui.GameFinishedDialog;
import org.promasi.client.gui.IDialogListener;
import org.promasi.client.gui.PlayingGameDialog;
import org.promasi.client.playmode.multiplayer.AbstractClientState;
import org.promasi.client.playmode.multiplayer.MultiPlayerGame;
import org.promasi.client.playmode.multiplayer.ProMaSiClient;
import org.promasi.game.IGame;
import org.promasi.game.SerializableGameModel;
import org.promasi.protocol.messages.EmployeeDischargedRequest;
import org.promasi.protocol.messages.EmployeeHiredRequest;
import org.promasi.protocol.messages.GameFinishedRequest;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.LeaveGameRequest;
import org.promasi.protocol.messages.LeaveGameResponse;
import org.promasi.protocol.messages.OnExecuteStepRequest;
import org.promasi.protocol.messages.OnTickRequest;
import org.promasi.protocol.messages.ProjectAssignedRequest;
import org.promasi.protocol.messages.ProjectFinishedRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class PlayingGameClientState extends AbstractClientState implements IDialogListener
{
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private PlayingGameDialog _playingGameDialog;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * 
	 */
	private SerializableGameModel _gameModel;
	
	/**
	 * 
	 */
	private DateTime _dateTime;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private GameFinishedRequest _gameFinishedRequest;
	
	/**
	 * 
	 * @param shell
	 * @param onlineGame
	 * @throws NullArgumentException
	 */
	public PlayingGameClientState(Shell shell, MultiPlayerGame onlineGame, ProMaSiClient client, SerializableGameModel gameModel, DateTime dateTime)throws NullArgumentException{
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		if(onlineGame==null){
			throw new NullArgumentException("Wrong argument onlineGame==null");
		}
		
		if(gameModel==null){
			throw new NullArgumentException("Wrong argument gameModel==null");
		}
		
		if(dateTime==null){
			throw new NullArgumentException("Wrong argument dateTime==null");
		}
		
		if(client==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		_client=client;
		_shell=shell;
		_game=onlineGame;
		_dateTime=dateTime;
		_gameModel=gameModel;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof ProjectAssignedRequest){
				ProjectAssignedRequest request=(ProjectAssignedRequest)object;
				if(request.getCompany()==null || request.getProject()==null){
					_client.sendMessage(new WrongProtocolResponse().serialize());
					_client.disconnect();
				}else{
					_playingGameDialog.projectAssigned(null, request.getCompany(), request.getProject(), new DateTime(request.getDateTime()));
				}
			}else if(object instanceof OnExecuteStepRequest){
				OnExecuteStepRequest request=(OnExecuteStepRequest)object;
				if(request.getProject()==null || request.getDateTime()==null || request.getCompany()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}else{
					_playingGameDialog.onExecuteStep(_game, request.getCompany(), request.getProject(), new DateTime(request.getDateTime()));
				}
			}else if(object instanceof OnTickRequest){
				OnTickRequest request=(OnTickRequest)object;
				if(request.getDateTime()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}else{
					_playingGameDialog.onTick(_game, new DateTime(request.getDateTime()));
				}
			}else if(object instanceof EmployeeHiredRequest){
				EmployeeHiredRequest request=(EmployeeHiredRequest)object;
				if(request.getMarketPlace()==null || request.getCompany()==null || request.getEmployee()==null || request.getDateTime()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				_playingGameDialog.employeeHired(_game, request.getMarketPlace(), request.getCompany(), request.getEmployee(), new DateTime(request.getDateTime()));
			}else if(object instanceof EmployeeDischargedRequest){
				EmployeeDischargedRequest request=(EmployeeDischargedRequest)object;
				if(request.getMarketPlace()==null || request.getCompany()==null || request.getEmployee()==null || request.getDateTime()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				_playingGameDialog.employeeDischarged(_game, request.getMarketPlace(), request.getCompany(), request.getEmployee(), new DateTime(request.getDateTime()));
			}else if(object instanceof ProjectFinishedRequest){
				ProjectFinishedRequest request=(ProjectFinishedRequest)object;
				if(request.getProject()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				_playingGameDialog.projectFinished(_game, null, request.getProject(), null);
			}else if (object instanceof GameFinishedRequest){
				_gameFinishedRequest=(GameFinishedRequest)object;
				if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
					_shell.getDisplay().syncExec(new Runnable() {
						
						@Override
						public void run() {
							try {
								_playingGameDialog.removeGamesDialogListener(PlayingGameClientState.this);
								_playingGameDialog.closeDialog();
								GameFinishedDialog gameFinishedDialog=new GameFinishedDialog(_shell, SWT.DIALOG_TRIM, _gameFinishedRequest.getClientId(), _gameFinishedRequest.getGameModel(), _gameFinishedRequest.getOtherPlayersModels());
								gameFinishedDialog.registerDialogListener(PlayingGameClientState.this);
								gameFinishedDialog.open();
							} catch (NullArgumentException e) {
									//Logger
							}
						}
					});
				}
			}else if(object instanceof UpdateGameListRequest){
			}else if(object instanceof LeaveGameResponse){
				try {
					changeClientState(_client, new ChooseGameClientState(_client, _shell, new TreeMap<String, String>()));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				client.sendMessage(new WrongProtocolResponse().serialize());
				client.disconnect();
			}
		}catch(IllegalArgumentException e){
			client.sendMessage(new InternalErrorResponse().serialize());
			client.disconnect();
		}
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_shell.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					try {
						_playingGameDialog=new PlayingGameDialog(_shell, SWT.APPLICATION_MODAL, _game);
						_playingGameDialog.registerGamesDialogListener(PlayingGameClientState.this);
						_playingGameDialog.open();
						_playingGameDialog.gameStarted(_game, _gameModel, _dateTime);
					} catch (NullArgumentException e) {
						//Logger
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
	public void dialogClosed(Dialog dialog) {
		_client.sendMessage(new LeaveGameRequest().serialize());
	}

}
