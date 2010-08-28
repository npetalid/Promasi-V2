/**
 *
 */
package org.promasi.network.protocol.client.response;

import java.util.LinkedList;

import org.apache.commons.lang.NullArgumentException;

/**
 * @author m1cRo
 *
 */
public class RetreiveGamesResponse extends AbstractResponse
{
	/**
	 *
	 */
	private LinkedList<String> _games;

	/**
	 *
	 * @param games
	 * @throws NullArgumentException
	 */
	public RetreiveGamesResponse(LinkedList<String> games)throws NullArgumentException
	{
		if(games==null)
		{
			throw new NullArgumentException("Wrong argument games==null");
		}
		_games=games;
	}

	/**
	 *
	 * @param games
	 * @throws NullArgumentException
	 */
	public void setGames(LinkedList<String> games)throws NullArgumentException
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
	LinkedList<String> getGames()
	{
		return _games;
	}
}
