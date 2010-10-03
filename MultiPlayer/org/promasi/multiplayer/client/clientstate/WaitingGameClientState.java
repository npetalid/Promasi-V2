/**
 * 
 */
package org.promasi.multiplayer.client.clientstate;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.GameStory;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.shell.playmodes.multiplayermode.MultiPlayerScorePlayMode;

/**
 * @author m1cRo
 *
 */
public class WaitingGameClientState extends AbstractClientState {
	/**
	 * 
	 */
	GameStory _gameStory;
	
	/**
	 * 
	 */
	MultiPlayerScorePlayMode _playMode;
	
	public WaitingGameClientState(MultiPlayerScorePlayMode playMode, GameStory gameStory)throws NullArgumentException
	{
		if( playMode==null )
		{
			throw new NullArgumentException("Wrong argument playMode==null");
		}
		
		if(gameStory==null)
		{
			throw new NullArgumentException("Wrong argument gameStory==null");
		}
		
		_playMode=playMode;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.multiplayer.server.clientstate.IClientState#onReceive(org.promasi.multiplayer.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException {
		// TODO Auto-generated method stub
	}

}
