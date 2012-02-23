/**
 * 
 */
package org.promasi.client_swing.playmode.multiplayer;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServer;
import org.promasi.game.IGamesServerListener;

/**
 * @author alekstheod
 * Represent the Multiplayer game server in
 * promasi system.
 */
public class MultiPlayerGamesServer implements IGamesServer {

	/**
	 * Constructor will initialize the object.
	 * @param hostName Hostname where the promasi server is running.
	 * @param portNumber The port number in which the promasi server are listening.
	 * @throws GuiException In case of invalid arguments or connection problem.
	 */
	public MultiPlayerGamesServer(String hostName, int portNumber) throws GuiException{
		if( hostName == null ){
			throw new GuiException("Wrong argument hostName == null");
		}
		
		if( portNumber < 0 ){
			throw new GuiException("Wrong argument portNumber < 0");
		}
		
		
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
