/**
 *
 */
package org.promasi.multiplayer.game;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.core.SdSystem;
import org.promasi.model.Company;

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
     * The current {@link Company} of this game model.
     */
	private Company _company;

    /**
     * The running sd system.
     */
    private SdSystem _sdSystem;
    
    /**
     * 
     */
    private String _gameDescription;
	
    /**
     * 
     */
    public GameModel(Company company,SdSystem sdSystem,String gameDescription)throws NullArgumentException{
    	if(company==null){
    		throw new NullArgumentException("Wrong argument company==null");
    	}
    	
    	if(sdSystem==null){
    		throw new NullArgumentException("Wrong argument sdSystem==null");
    	}
    	
    	if(gameDescription==null){
    		throw new NullArgumentException("Wrong argument gameDescription==null");
    	}
    	
    	_company=company;
    	_sdSystem=sdSystem;
    	_gameDescription=gameDescription;
    }
    
    /**
     * 
     * @return
     */
    public String getGameDescription(){
    	return _gameDescription;
    }
    
    /**
     * 
     * @return
     */
    public Company getCompany(){
    	return _company;
    }
    
	/**
	 * 
	 */
	public synchronized boolean executeStep(){
		if(_sdSystem==null){
			return false;
		}
		
		_sdSystem.executeStep();
		return true;
	}
}
