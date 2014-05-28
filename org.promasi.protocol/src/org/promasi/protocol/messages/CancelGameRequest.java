/**
 * 
 */
package org.promasi.protocol.messages;

/**
 * @author m1cRo
 *
 */
public class CancelGameRequest extends Message 
{
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	public CancelGameRequest(){}
	
	/**
	 * 
	 * @param gameId
	 */
	public CancelGameRequest(String gameId){
		setGameId(gameId);
	}
	/**
	 * @param _gameId the _gameId to set
	 */
	public void setGameId(String _gameId) {
		this._gameId = _gameId;
	}
	/**
	 * @return the _gameId
	 */
	public String getGameId() {
		return _gameId;
	}
}
