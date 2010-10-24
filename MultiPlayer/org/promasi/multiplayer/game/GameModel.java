/**
 *
 */
package org.promasi.multiplayer.game;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.core.SdModel;
import org.promasi.core.SdSystem;
import org.promasi.model.Company;
import org.promasi.shell.ui.Story.Story;

/**
 * @author m1cRo
 *
 */
public class GameModel implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /**
     * The running sd system.
     */
    private Story _gameStory;
    
    /**
     * 
     */
    public GameModel(Story gameStory)throws NullArgumentException{
    	if(gameStory==null){
    		throw new NullArgumentException("Wrong argument gameStory==null");
    	}
    	
    	_gameStory=gameStory;
    }
    
    /**
     * 
     * @return
     */
    public Story getGameStory()
    {
    	return _gameStory;
    }
    
	/**
	 * 
	 */
	public synchronized boolean executeStep(){

		return true;
	}
}
