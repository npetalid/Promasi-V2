/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.GameModelMemento;
import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class GameStartedRequest extends SerializableObject 
{
	/**
	 * 
	 */
	GameModelMemento _gameModel;

	/**
	 * 
	 */
	String _dateTime;
	
	/**
	 * 
	 */
	public GameStartedRequest(){}
	
	/**
	 * 
	 * @param gameModel
	 * @param dateTime
	 */
	public GameStartedRequest(GameModelMemento gameModel, String dateTime){
		_gameModel=gameModel;
		_dateTime=dateTime;
	}
	
	/**
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public String getDateTime() {
		return _dateTime;
	}

	/**
	 * 
	 * @param dateTime
	 */
	public void setDateTime(String dateTime) {
		_dateTime = dateTime;
	}

}
