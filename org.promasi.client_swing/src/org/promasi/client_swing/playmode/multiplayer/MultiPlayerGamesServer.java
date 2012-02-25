/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServer;
import org.promasi.game.IGamesServerListener;
import org.promasi.protocol.client.ProMaSiClient;

/**
 * @author alekstheod
 * Represent the Multiplayer game server in
 * promasi system.
 */
public class MultiPlayerGamesServer implements IGamesServer {

	/**
	 * 
	 */
	private ProMaSiClient _client;
	
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
	public boolean registerGamesServerListener(IGamesServerListener listener) {
		// TODO Auto-generated method stub
		return false;
	}

}
