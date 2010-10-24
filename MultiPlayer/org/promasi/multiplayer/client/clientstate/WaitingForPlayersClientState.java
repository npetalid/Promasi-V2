package org.promasi.multiplayer.client.clientstate;

import java.net.ProtocolException;

import javax.naming.ConfigurationException;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.communication.Communicator;
import org.promasi.communication.ICommunicator;
import org.promasi.model.Company;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.network.protocol.client.request.RequestBuilder;
import org.promasi.network.protocol.client.response.InternalErrorResponse;
import org.promasi.network.protocol.client.response.StartGameResponse;
import org.promasi.network.protocol.client.response.WrongProtocolResponse;
import org.promasi.shell.playmodes.multiplayerscoremode.MultiPlayerScorePlayMode;
import org.promasi.shell.ui.Story.Story;
import org.promasi.ui.promasiui.multiplayer.WaitingForPlayersForm;

/**
 * 
 * @author m1cRo
 *
 */
public class WaitingForPlayersClientState extends AbstractClientState {

	/**
	 * 
	 */
	private MultiPlayerScorePlayMode _playMode;
	
	/**
	 * 
	 */
	private Story _gameStory;
	
	/**
	 * 
	 */
	WaitingForPlayersForm _waitingForm;
	
	/**
	 * 
	 * @param playMode
	 * @throws NullArgumentException
	 */
	public WaitingForPlayersClientState(MultiPlayerScorePlayMode playMode, Story gameStory, ProMaSiClient client)throws NullArgumentException
	{
		if(playMode==null)
		{
			throw new NullArgumentException("Wrong argument playMode==null");
		}
		
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		_waitingForm=new WaitingForPlayersForm(client);
		_waitingForm.setVisible(true);
		_gameStory=gameStory;
		_playMode=playMode;
	}
	
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException {
		if(recData==null)
		{
			throw new NullArgumentException("Wrong argument recData==null");
		}
		
		if(client==null)
		{
			throw new NullArgumentException("Wrong argument client==null");
		}
		
		try
		{
			Object object=RequestBuilder.buildRequest(recData);
			if(object instanceof StartGameResponse)
			{	
				Company company = _gameStory.getCompany();
		        company.setProjectManager( _playMode.getProjectManager());
		        _playMode.getShell().setCompany( company );
		        _playMode.setMarketPlace(_gameStory.getMarketPlace());
  
		        ICommunicator communicator=new Communicator();
		        communicator.setMainReceiver( _playMode.getShell().getModelMessageReceiver());
		            	
		        _playMode.getShell().setCurrentPlayMode(_playMode);
		        _waitingForm.setVisible(false);
		        _waitingForm.dispose();
				changeClientState( client, new PlayingGameClientState(_playMode.getShell()) );
			}
			else
			{
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
		} catch (ConfigurationException e) {
			client.sendMessage(new InternalErrorResponse().toProtocolString());
			client.disconnect();
		}
	}

}
