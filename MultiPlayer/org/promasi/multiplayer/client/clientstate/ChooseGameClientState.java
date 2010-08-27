/**
 * 
 */
package org.promasi.multiplayer.client.clientstate;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.multiplayer.AbstractClientState;
import org.promasi.multiplayer.ProMaSiClient;
import org.promasi.shell.IPlayMode;
import org.promasi.shell.playmodes.singleplayerscoremode.StoriesPool;

/**
 * @author m1cRo
 *
 */
public class ChooseGameClientState extends AbstractClientState{

	/**
	 * 
	 */
	private IPlayMode _playMode;
	
	/**
	 * 
	 * @param playMode
	 * @throws NullArgumentException
	 */
	public ChooseGameClientState(IPlayMode playMode)throws NullArgumentException
	{
		if( playMode==null )
		{
			throw new NullArgumentException("Wrong argument playMode=null");
		}
		
		_playMode=playMode;
	}
	
	/* (non-Javadoc)
	 * @see org.promasi.server.clientstate.IClientState#onReceive(org.promasi.server.core.ProMaSiClient, java.lang.String)
	 */
	@Override
	public void onReceive(ProMaSiClient client, String recData)throws NullArgumentException {
		// TODO protocol check and update Stories list.
		_playMode.updateStories(StoriesPool.getAllStories( ));
	}

}
