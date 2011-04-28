/**
 * 
 */
package org.promasi.client.playmode.multiplayer.client.clientstate;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.promasi.client.gui.ILoginDialogListener;
import org.promasi.client.gui.LoginDialog;
import org.promasi.client.playmode.multiplayer.AbstractClientState;
import org.promasi.client.playmode.multiplayer.ProMaSiClient;
import org.promasi.protocol.messages.LoginFailedResponse;
import org.promasi.protocol.messages.LoginRequest;
import org.promasi.protocol.messages.LoginResponse;
import org.promasi.protocol.messages.WrongProtocolResponse;
import org.promasi.utilities.exceptions.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class LoginClientState extends AbstractClientState implements ILoginDialogListener
{
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private LoginDialog _loginDialog;
	
	/**
	 * 
	 * @param shell
	 * @throws NullArgumentException
	 */
	public LoginClientState(Shell shell)throws NullArgumentException{
		if(shell==null){
			throw new NullArgumentException("Wrong argument shell==null");
		}

		_shell=shell;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.game.multiplayer.IClientState#onReceive(org.promasi.playmode.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof LoginResponse){
				LoginResponse response=(LoginResponse)object;
				ChooseGameClientState chooseGameClientSate=new ChooseGameClientState(client, _shell, response.getAvailableGames());	
				_loginDialog.close();
				changeClientState(client, chooseGameClientSate);
			}else if(object instanceof LoginFailedResponse){
				_loginDialog.showLoginFailedMessage();
			}else{
				client.disconnect();
				_client.sendMessage(new WrongProtocolResponse().serialize());
				_loginDialog.close();
			}
		}catch(NullArgumentException e){
			client.disconnect();
		}catch(IllegalArgumentException e){
			client.disconnect();
		}
	}
	
	@Override
	public void loginButtonPressed(String userName, String password) {
		if(_client==null){
			//Logger 
			return;
		}
			
		LoginRequest request=new LoginRequest(userName, password);
		_client.sendMessage(request.serialize());
	}
	
	@Override
	public void onSetState(ProMaSiClient client) {
		if(client!=null && !_shell.isDisposed() && !_shell.getDisplay().isDisposed()){
			_client=client;	
			_shell.getDisplay().asyncExec(new Runnable() {
			
				@Override
				public void run() {
					try {
						
						_loginDialog=new LoginDialog(_shell, SWT.DIALOG_TRIM);
						_loginDialog.addListener(LoginClientState.this);
						_loginDialog.open();
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

}
