package org.promasi.protocol.messages;

/**
 * 
 * @author m1cRo
 *
 */
public class StartGameRequest extends Message
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
