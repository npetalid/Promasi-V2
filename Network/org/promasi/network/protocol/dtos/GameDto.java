/**
 * 
 */
package org.promasi.network.protocol.dtos;

import java.io.Serializable;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.model.Company;

/**
 * @author m1cRo
 *
 */
public class GameDto implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Company _company;
	
	/**
	 * 
	 */
	private String _name;
	
	/**
	 * 
	 */
	private String _gameDescription;
	
	/**
	 * 
	 */
	private int _numberOfPlayers;
	
	/**
	 * 
	 * @param company
	 * @param sdModel
	 * @param name
	 * @param numberOfPlayers
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public GameDto(Company company,String name,String gameDescription,int numberOfPlayers)throws NullArgumentException,IllegalArgumentException
	{
		if(company==null)
		{
			throw new NullArgumentException("Wrong argument company==null");
		}
		
		if(name==null)
		{
			throw new NullArgumentException("Wrong argument name==null");
		}
		
		if(gameDescription==null)
		{
			throw new NullArgumentException("Wrong argument gameDescription==null");
		}
		
		if(numberOfPlayers<0)
		{
			throw new NullArgumentException("Wrong argument numberOfPlayers<0");
		}
		
		_company=company;
		_name=name;
		_numberOfPlayers=numberOfPlayers;
	}
	
	/**
	 * 
	 * @param company
	 * @throws NullArgumentException
	 */
	public synchronized void setCompany(Company company)throws NullArgumentException
	{
		if(company==null)
		{
			throw new NullArgumentException("Wrong argument company==null");
		}
		
		_company=company;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized Company getCompany()
	{
		return _company;
	}
	
	/**
	 * 
	 * @param name
	 * @throws NullArgumentException
	 */
	public synchronized void setName(String name)throws NullArgumentException
	{
		if(name==null)
		{
			throw new NullArgumentException("Wrong argument name==null");
		}
		
		_name=name;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized String getName()
	{
		return _name;
	}
	
	/**
	 * 
	 * @param gameDescription
	 * @throws NullArgumentException
	 */
	public synchronized void setGameDescription(String gameDescription)throws NullArgumentException
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
	public synchronized String getGameDescription()
	{
		return _gameDescription;
	}
	
	/**
	 * 
	 * @param numberOfPlayers
	 * @throws IllegalArgumentException
	 */
	public synchronized void setName(int numberOfPlayers)throws IllegalArgumentException
	{
		if(numberOfPlayers<0)
		{
			throw new NullArgumentException("Wrong argument numberOfPlayers<0");
		}
		
		_numberOfPlayers=numberOfPlayers;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNumberOfPlayers()
	{
		return _numberOfPlayers;
	}
}
