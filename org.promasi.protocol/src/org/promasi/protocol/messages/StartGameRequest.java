package org.promasi.protocol.messages;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * 
 * @author m1cRo
 *
 */
public class StartGameRequest extends SerializableObject
{
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	public StartGameRequest(){}
	
	/**
	 * 
	 * @param gameId
	 */
	public StartGameRequest(String gameId){
		_gameId=gameId;
	}

	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(String gameId) {
		_gameId = gameId;
	}

	/**
	 * @return the gameId
	 */
	public String getGameId() {
		return _gameId;
	}
}
