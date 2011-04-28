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
public class GameStartedRequest extends SerializableObject 
{
	/**
	 * 
	 */
	SerializableGameModel _gameModel;

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
	public GameStartedRequest(SerializableGameModel gameModel, String dateTime){
		_gameModel=gameModel;
		_dateTime=dateTime;
	}
	
	/**
	 * 
	 * @return
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
