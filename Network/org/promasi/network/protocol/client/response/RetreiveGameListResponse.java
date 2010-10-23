/**
 * 
 */
package org.promasi.network.protocol.client.response;

import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.shell.ui.Story.Story;

/**
 * @author m1cRo
 *
 */
public class RetreiveGameListResponse extends AbstractResponse {
	/**
	 * 
	 */
	private List<Story> _games;
	
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
	public RetreiveGameListResponse(List<Story> gameList)throws NullArgumentException
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
	public List<Story> getGames() {
		return _games;
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public void setGames(List<Story> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		
		_games=games;
	}
}
