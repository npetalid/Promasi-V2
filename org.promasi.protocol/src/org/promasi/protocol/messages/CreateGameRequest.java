/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.SerializableGameModel;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class CreateGameRequest extends SerializableObject
{
	/**
	 * 
	 */
	private SerializableGameModel _gameModel;

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
	public CreateGameRequest(String gameId, SerializableGameModel gameModel){
		_gameModel=gameModel;
		_gameId=gameId;
	}

	/**
	 * @return the gameModel
	 */
	public SerializableGameModel getGameModel() {
		return _gameModel;
	}

	/**
	 * 
	 * @param gameModel
	 */
	public void setGameModel(SerializableGameModel gameModel) {
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
