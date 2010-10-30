package org.promasi.multiplayer.client.clientstate;

import java.net.ProtocolException;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.CreateNewGameResponse;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.shell.Shell;
import org.promasi.shell.playmodes.multiplayerscoremode.MultiPlayerScorePlayMode;
import org.promasi.ui.promasiui.multiplayer.ChooseStoryFrame;

public class GameMasterClientState extends AbstractClientState {

	/**
	 * 
	 */
	private ChooseStoryFrame _createGamefrom;
	
	/**
	 * 
	 */
	private Shell _shell;
	
	/**
	 * 
	 */
	private MultiPlayerScorePlayMode _playMode;
	
	/**
	 * 
	 * @param playMode
	 * @throws NullArgumentException
	 */
	public GameMasterClientState(Shell shell, MultiPlayerScorePlayMode playMode, ProMaSiClient client, Map<String, String> availableGames)throws NullArgumentException
	{
		if( playMode==null )
		{
			throw new NullArgumentException("Wrong argument playMode=null");
		}
		
		if( client==null )
		{
			throw new NullArgumentException("Wrong argument client=null");
		}
		
		if(availableGames==null)
		{
			throw new NullArgumentException("Wrong argument availableGames==null");
		}
		
		if(shell==null)
		{
			throw new NullArgumentException("Wrong argument shell==null");
		}
		
		_shell=shell;
		_createGamefrom=new ChooseStoryFrame(client,availableGames);
		_createGamefrom.setVisible(true);
		_playMode=playMode;
	}

	@Override
	public void onReceive(ProMaSiClient client, String recData) throws NullArgumentException {
		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof CreateNewGameResponse)
			{
				changeClientState(client, new WaitingForPlayersClientState(_shell, _playMode,client));				
			}
			else
			{
				_createGamefrom.setVisible(true);
				client.sendMessage(new WrongProtocolResponse().toProtocolString());
				client.disconnect();
			}
		}
		catch(ProtocolException e)
		{
			client.sendMessage(new WrongProtocolResponse().toProtocolString());
			client.disconnect();
		}
		catch(NullArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
		catch(IllegalArgumentException e)
		{
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
	}
	
}
