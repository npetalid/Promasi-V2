package org.promasi.protocol.messages;

public class JoinGameRequest extends Message 
{
	/**
	 * 
	 */
	private String _gameId;

	/**
	 * 
	 */
	public JoinGameRequest(){
		
	}
	
	/**
	 * 
	 * @param gameId
	 */
	public JoinGameRequest(String gameId){
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
