/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.promasi.game.AGamesServer;
import org.promasi.game.IGame;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.UpdateAvailableGameListRequest;
import org.promasi.protocol.messages.UpdateGameListRequest;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utils_swing.GuiException;

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
		
		_client = client;
		_client.addListener(this);
		_client.sendMessage(new UpdateAvailableGameListRequest());
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
				Map<String, String> availableGames = ((UpdateGameListRequest)object).getAvailableGames();
				if( availableGames != null ){
					List< IGame > games = new LinkedList<>();
					for( Map.Entry<String, String> gameEntry : availableGames.entrySet() ){
						MultiPlayerGame game = new MultiPlayerGame(this, client, gameEntry.getKey(), gameEntry.getValue() );
						games.add(game);
					}
					
					updateGamesList(games);
				}else{
					
				}
			}
		}catch( Exception e){
			_logger.error("Invalid message received : " + recData);
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
	public boolean createGame(String gameId, IGame game) {
		// TODO Auto-generated method stub
		return false;
	}
}
