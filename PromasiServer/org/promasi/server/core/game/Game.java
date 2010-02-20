/**
 *
 */
package org.promasi.server.core.game;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;
import org.promasi.server.core.GameModel;
import org.promasi.server.core.UserManager;
import org.promasi.server.core.ProMaSiClient;

/**
 * @author m1cRo
 *
 */
public class Game
{
	private String _gameId;

	private ProMaSiClient _gameMaster;

	private Map<String,GameModel> _gameModels;

	private String _promasiModel;

	public Game(String gameId,ProMaSiClient gameMaster,String promasiModel)throws NullArgumentException
	{
		if(gameId==null)
		{
			throw new NullArgumentException("Wrong argument gameId==null");
		}

		if(gameMaster==null)
		{
			throw new NullArgumentException("Wrong argument gameMaster==null");
		}

		if(promasiModel==null)
		{
			throw new NullArgumentException("Wrong argument promasiModel==null");
		}
		_gameId=gameId;
		_gameMaster=gameMaster;
		_gameModels=new HashMap<String,GameModel>();
		_promasiModel=promasiModel;
	}

	/**
	 *
	 * @return
	 */
	public String getGameId()
	{
		return _gameId;
	}
}
