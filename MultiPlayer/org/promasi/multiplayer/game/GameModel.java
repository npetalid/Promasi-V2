/**
 *
 */
package org.promasi.multiplayer.game;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;
import org.promasi.model.MarketPlace;
import org.promasi.shell.Story.Story;

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
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     */
    public Company getCompany() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException
    {
    	Company company=(Company)BeanUtils.cloneBean(_gameStory.getCompany());
    	return company;
    }
    
    /**
     * 
     * @return
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws InstantiationException 
     * @throws IllegalAccessException 
     */
    public MarketPlace getMarketPlace() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException
    {
    	MarketPlace marketPlace=(MarketPlace)BeanUtils.cloneBean(_gameStory.getMarketPlace());
    	return marketPlace;
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
     * @return
     */
    public String getName()
    {
    	return new String( _gameStory.getName() );
    }
    
    /**
     * 
     * @return
     */
    public String getDescription()
    {
    	return new String( _gameStory.getInfoString() );
    }
    
	/**
	 * 
	 */
	public synchronized boolean executeStep(){

		return true;
	}
	
	
	public synchronized boolean setGameValues(Map<String,Double> gameValues)
	{
		
		return true;
	}
}
