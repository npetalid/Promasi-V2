/**
 * 
 */
package org.promasi.network.protocol.client.response;

import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.protocol.dtos.GameDto;

/**
 * @author m1cRo
 *
 */
public class RetreiveGameListResponse extends AbstractResponse {
	/**
	 * 
	 */
	private List<GameDto> _gameList;
	
	/**
	 * 
	 */
	public RetreiveGameListResponse()
	{
		super();
	}
	
	/**
	 * @param gameList 
	 * 
	 */
	public RetreiveGameListResponse(List<GameDto> gameList)throws NullArgumentException
	{
		if(gameList==null)
		{
			throw new NullArgumentException("Wrong argument gameList==null");
		}
		
		_gameList=gameList;
	}

	/**
	 * 
	 * @return
	 */
	public List<GameDto> getGames() {
		return _gameList;
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public void setGames(List<GameDto> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		
		_gameList=games;
	}
}
