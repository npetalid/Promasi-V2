package org.promasi.network.protocol.server.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.protocol.client.request.AbstractRequest;
import org.promasi.multiplayer.game.Game;
import java.util.List;

public class UpdateGamesListRequest extends AbstractRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private List<Game> _games;
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public UpdateGamesListRequest(final List<Game> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		
		_games=games;
	}

	/**
	 * 
	 * @return
	 */
	public synchronized List<Game> getGames()
	{
		return _games;
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public synchronized void setGames(final List<Game> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		
		_games=games;
	}
	
}
