/**
 * 
 */
package org.promasi.client.playmode.multiplayer.client.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.promasi.client.gui.IMultiPlayerGamesDialogListener;
import org.promasi.client.gui.MultiPlayerGamesDialog;
import org.promasi.client.playmode.multiplayer.AbstractClientState;
import org.promasi.client.playmode.multiplayer.ProMaSiClient;
import org.promasi.protocol.messages.JoinGameRequest;
import org.promasi.protocol.messages.JoinGameResponse;
import org.promasi.protocol.messages.UpdateAvailableGameListRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class ChooseGameClientState extends AbstractClientState implements IMultiPlayerGamesDialogListener
{
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private Map<String, String> _availableGames;
	
	/**
	 * 
	 */
	private MultiPlayerGamesDialog _gamesDialog;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 * @param availableGames
	 */
	public ChooseGameClientState(ProMaSiClient client, Shell shell, Map<String, String> availableGames)throws NullArgumentException, IllegalArgumentException{
		if(availableGames==null){
			throw new NullArgumentException("Wrong argument availableGames==null");
		}
		
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		if(client==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		_client=client;
		_shell=shell;
		_availableGames=new TreeMap<String,String>(availableGames);
	}

	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof JoinGameResponse){
				JoinGameResponse response=(JoinGameResponse)object;
				if(response.getGameName()==null || response.getGameDescription()==null || response.getPlayers()==null){
					client.sendMessage(new WrongProtocolResponse().serialize());
					client.disconnect();
				}
				
				WaitingGameClientState waitingGameClientState=new WaitingGameClientState(_shell, client, response.getGameName(), response.getGameDescription(), response.getPlayers());
				changeClientState(client, waitingGameClientState);
				if(_gamesDialog!=null){
					_gamesDialog.close();	
				}
					
			}else if(object instanceof UpdateGameListRequest){
				UpdateGameListRequest request=(UpdateGameListRequest)object;
				_gamesDialog.updateGameList(request.getAvailableGames());
				_availableGames=new TreeMap<String,String>(request.getAvailableGames());
			}else{
				_client.sendMessage(new WrongProtocolResponse().serialize());
				if(_gamesDialog!=null){
					_gamesDialog.close();
				}
			}
		}catch(NullArgumentException e){
			client.disconnect();
		}catch(IllegalArgumentException e){
			client.disconnect();
		}
	}

	@Override
	public void gameSelected(MultiPlayerGamesDialog dialog, String selectedGame) {
		if(_availableGames.containsKey(selectedGame)){
			JoinGameRequest request=new JoinGameRequest(selectedGame);
			_client.sendMessage(request.serialize());
		}
	}

	@Override
	public void createNewGameSelected() {
		Thread createGameThread=new Thread(new Runnable() {	
			@Override
			public void run() {
				try {
					MakeGameClientState makeGameClientState=new MakeGameClientState(_client, _shell);
					_gamesDialog.close();
					changeClientState(_client, makeGameClientState );
				} catch (NullArgumentException e) {
						//Logger
				}
			}
		});
		
		createGameThread.start();
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			
			_shell.getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					try {
						_gamesDialog=new MultiPlayerGamesDialog(_shell, SWT.DIALOG_TRIM);
						_gamesDialog.addListener(ChooseGameClientState.this);
						_client.sendMessage(new UpdateAvailableGameListRequest().serialize());
						_gamesDialog.updateGameList(_availableGames);
						_gamesDialog.open();
					} catch (IllegalArgumentException e) {
						//Logger
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

}
