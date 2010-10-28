/**
 * 
 */
package org.promasi.network.protocol.client.response;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.NullArgumentException;


/**
 * @author m1cRo
 *
 */
public class RetreiveGameListResponse extends AbstractResponse {
	/**
	 * 
	 */
	private Map<String, String> _games;
	
	/**
	 * 
	 */
	public RetreiveGameListResponse()
	{
		super();
		_games=new TreeMap<String,String>();
	}
	
	/**
	 * @param gameList 
	 * 
	 */
	public RetreiveGameListResponse(Map<String,String> gameList)throws NullArgumentException
	{
		if(gameList==null)
		{
			throw new NullArgumentException("Wrong argument gameList==null");
		}
		
		_games=gameList;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String,String> getGames() {
		return _games;
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public void setGames(Map<String,String> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		
		_games=games;
	}
}
