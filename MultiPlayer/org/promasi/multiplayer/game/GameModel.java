/**
 *
 */
package org.promasi.multiplayer.game;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
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
    public GameModel()
    {
    	
    }
    
	/**
	 * 
	 */
	public synchronized boolean executeStep()
	{
		if(_sdSystem==null)
		{
			return false;
		}
		
		_sdSystem.executeStep();
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Company getCompany()
	{
		return _company;
	}
	
	/**
	 * 
	 * @param company
	 * @throws NullArgumentException
	 */
	public void setCompany(Company company)throws NullArgumentException
	{
		if(company==null){
			throw new NullArgumentException("");
		}
		
		_company=company;
	}
	
	/**
	 * 
	 * @return
	 */
	public SdSystem getSdSystem()
	{
		return _sdSystem;
	}
	
	/**
	 * 
	 * @param sdSystem
	 * @throws NullArgumentException
	 */
	public void setSdSystem(SdSystem sdSystem)throws NullArgumentException
	{
		if(sdSystem==null){
			throw new NullArgumentException("");
		}
		
		_sdSystem=sdSystem;
	}
	
	/**
	 * 
	 * @param gameDescription
	 * @throws NullArgumentException
	 */
	public void setGameDescription(String gameDescription)throws NullArgumentException
	{
		if(gameDescription==null)
		{
			throw new NullArgumentException("Wrong argument gameDescription==null");
		}
		
		_gameDescription=gameDescription;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getGameDescription()
	{
		return _gameDescription;
	}
	
	/**
	 * 
	 * @return
	 */
	public String toXmlString()
	{
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		XMLEncoder xmlEncoder=new XMLEncoder(outputStream);
		xmlEncoder.writeObject(this);
		xmlEncoder.close();
		return outputStream.toString();
	}
}
