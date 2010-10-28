package org.promasi.network.protocol.client.response;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.NullArgumentException;

public class ChooseGameMasterStateResponse extends AbstractResponse {
	
	/**
	 * 
	 */
	Map<String,String> _availableGames;
	
	/**
	 * 
	 */
	public ChooseGameMasterStateResponse()
	{
		_availableGames=new TreeMap<String,String>();
	}
	
	/**
	 * 
	 * @param availableGames
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public ChooseGameMasterStateResponse(Map<String,String> availableGames)throws NullArgumentException, IllegalArgumentException
	{
		if(availableGames==null)
		{
			throw new NullArgumentException("Wrong argument availableGames==null");
		}
		
		for(Map.Entry<String, String> entry : availableGames.entrySet())
		{
			if(entry.getKey()==null || entry.getValue()==null)
			{
				throw new IllegalArgumentException("Wrong argument availableGames contains null");
			}
		}
		
		_availableGames=availableGames;
	}
	
	/**
	 * 
	 * @param availableGames
	 * @throws NullArgumentException
	 * @throws IllegalArgumentException
	 */
	public void setAvailableGames(Map<String,String> availableGames)throws NullArgumentException, IllegalArgumentException
	{
		if(availableGames==null)
		{
			throw new NullArgumentException("Wrong argument availableGames==null");
		}
		
		for(Map.Entry<String, String> entry : _availableGames.entrySet())
		{
			if(entry.getKey()==null || entry.getValue()==null)
			{
				throw new IllegalArgumentException("Wrong argument availableGames contains null");
			}
		}
		
		_availableGames=availableGames;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String,String> getAvailableGames()
	{
		return _availableGames;
	}
}
