package org.promasi.network.protocol.server.request;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.network.protocol.client.request.AbstractRequest;
import org.promasi.protocol.dtos.GameDto;
import java.util.List;

public class UpdateGamesListRequest extends AbstractRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private List<GameDto> _games;
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public UpdateGamesListRequest(final List<GameDto> games)throws NullArgumentException
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
	public List<GameDto> getGames()
	{
		List<GameDto> games=null;
		synchronized(this)
		{
			games=_games;
		}
		
		return games;
	}
	
	/**
	 * 
	 * @param games
	 * @throws NullArgumentException
	 */
	public synchronized void setGames(final List<GameDto> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		
		_games=games;
	}
	
}
