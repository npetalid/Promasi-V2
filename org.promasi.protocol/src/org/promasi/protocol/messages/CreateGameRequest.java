/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.GameModelMemento;

/**
 * @author m1cRo
 *
 */
public class CreateGameRequest extends Message
{
	/**
	 * 
	 */
	private GameModelMemento _gameModel;

	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	public CreateGameRequest(){
	}
	
	/**
	 * 
	 * @param gameModel
	 */
	public CreateGameRequest(String gameId, GameModelMemento gameModel){
		_gameModel=gameModel;
		_gameId=gameId;
	}

	/**
	 * @return the gameModel
	 */
	public GameModelMemento getGameModel() {
		return _gameModel;
	}

	/**
	 * 
	 * @param gameModel
	 */
	public void setGameModel(GameModelMemento gameModel) {
		_gameModel = gameModel;
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
