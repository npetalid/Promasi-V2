/**
 * 
 */
package org.promasi.client.playmode.multiplayer.client.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.TreeMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.promasi.client.gui.IMakeGameDialogListener;
import org.promasi.client.gui.MakeGameDialog;
import org.promasi.client.playmode.multiplayer.AbstractClientState;
import org.promasi.client.playmode.multiplayer.ProMaSiClient;
import org.promasi.game.GameModel;
import org.promasi.protocol.messages.CreateGameRequest;
import org.promasi.protocol.messages.CreateGameResponse;
import org.promasi.protocol.messages.InternalErrorResponse;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.utilities.exceptions.NullArgumentException;
import org.promasi.utilities.serialization.SerializationException;

/**
 * @author m1cRo
 *
 */
public class MakeGameClientState extends AbstractClientState implements IMakeGameDialogListener
{
	/**
	 * 
	 */
	private MakeGameDialog _makeGameDialog;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private boolean _doCreateGame;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 * @param client
	 * @param shell
	 * @throws NullArgumentException 
	 */
	public MakeGameClientState(ProMaSiClient client, Shell shell) throws NullArgumentException{
		if(client==null){
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		_doCreateGame=false;
		_client=client;
		_shell=shell;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.playmode.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof CreateGameResponse){
				CreateGameResponse response=(CreateGameResponse)object;
				_makeGameDialog.close();
				WaitingPlayersClientState waitingPlayersClientState=new WaitingPlayersClientState(_shell, client, response.getGameId(), response.getGameDescription(), response.getPlayers());
				changeClientState(client, waitingPlayersClientState);
			}else if(object instanceof UpdateGameListRequest){
				
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
	public synchronized void creteGameButtonPressed(String onlineGameId, GameModel game) {
		try {
			_doCreateGame=true;
			CreateGameRequest request=new CreateGameRequest(onlineGameId, game.getSerializableGameModel());
			String requestString=request.serialize();
			_client.sendMessage(requestString);
		} catch (SerializationException e) {
			_client.sendMessage(new InternalErrorResponse().serialize());
		}
	}

	@Override
	public void onSetState(ProMaSiClient client) {
		if(!_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
				
			_shell.getDisplay().asyncExec(new Runnable() {
						
				@Override
				public void run() {
					try {
						_makeGameDialog=new MakeGameDialog(_shell, SWT.DIALOG_TRIM);
						_makeGameDialog.addListener(MakeGameClientState.this);
						_makeGameDialog.open();
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

	@Override
	public void dialogClosed(MakeGameDialog dialog) {
		if(!_doCreateGame){
			try {
				changeClientState( _client, new ChooseGameClientState( _client, _shell, new TreeMap<String, String>() ) );
			}catch(NullArgumentException e){
				e.printStackTrace();
			}
		}
	}

}
