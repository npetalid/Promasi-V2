/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.AGamesServer;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServerListener;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.UpdateAvailableGameListRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;

/**
 * @author alekstheod
 * Represent the Multiplayer game server in
 * promasi system.
 */
public class MultiPlayerGamesServer extends AGamesServer implements IClientListener {

	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * The games server listener.
	 */
	private List< IGamesServerListener > _listeners;
	
	/**
	 * 
	 */
	private ILogger _logger = LoggerFactory.getInstance(MultiPlayerGamesServer.class);
	
	/**
	 * 
	 * @param client
	 * @throws GuiException
	 */
	public MultiPlayerGamesServer(ProMaSiClient client ) throws GuiException{
		if( client == null){
			throw new GuiException("Wrong argument client");
		}
		
		_listeners = new LinkedList<>();
		_client.addListener(this);
		_client = client;
		_client.sendMessage(new UpdateAvailableGameListRequest().serialize());
	}
	
	@Override
	public boolean requestGamesList() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean joinGame(IGame game) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		try
		{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof UpdateGameListRequest){
				final  UpdateGameListRequest request = (UpdateGameListRequest)object;	
				//request.get
			}
		}catch( Exception e){
			_logger.error("Invalid message received : " + recData);
		}
	}

	@Override
	public void onSetState(ProMaSiClient client, IClientListener state) {
		// TODO Auto-generated method stub
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
