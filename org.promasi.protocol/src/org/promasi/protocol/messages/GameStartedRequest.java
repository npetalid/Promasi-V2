/**
 * 
 */
package org.promasi.protocol.messages;

import org.promasi.game.model.generated.GameModelModel;

/**
 * @author m1cRo
 *
 */
public class GameStartedRequest extends Message 
{
	/**
	 * 
	 */
	GameModelModel _gameModel;

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
	public GameStartedRequest(GameModelModel gameModel, String dateTime){
		_gameModel=gameModel;
		_dateTime=dateTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public GameModelModel getGameModel() {
		return _gameModel;
	}

	/**
	 * 
	 * @param gameModel
	 */
	public void setGameModel(GameModelModel gameModel) {
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
