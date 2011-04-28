/**
 * 
 */
package org.promasi.client.playmode.singleplayer;

import org.promasi.client.playmode.IPlayMode;
import org.promasi.client.playmode.singleplayer.userstate.IUserState;
import org.promasi.utilities.exceptions.NullArgumentException;


/**
 * 
 * @author m1cRo
 *
 */
public abstract class AbstractUserState implements IUserState{
	/**
	 * 
	 */
	protected IPlayMode _playMode;
	
	/**
	 * 
	 * @param playMode
	 * @throws NullArgumentException
	 */
	public AbstractUserState(IPlayMode playMode)throws NullArgumentException{
		if(playMode==null){
			throw new NullArgumentException("Wrong argument playMode==null");
		}
		
		_playMode=playMode;
	}
	
	
	/**
	 * 
	 * @param newState
	 */
	public boolean changeUserState(IUserState newState ){
		if(newState==null){
			return false;
		}
		
		_playMode.changeState(newState);
		newState.run();
		return true;
	}
}
