/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.List;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * @author m1cRo
 *
 */
public class CreateGameResponse extends SerializableObject 
{
	/**
	 * 
	 */
	private String _gameId;
	
	/**
	 * 
	 */
	private String _gameDescription;
	
	/**
	 * 
	 */
	private List<String> _players;
	
	/**
	 * 
	 */
	public CreateGameResponse(){}
	
	/**
	 * 
	 * @param gameId
	 * @param players
	 */
	public CreateGameResponse(String gameId, String gameDescription, List<String> players){
		_gameId=gameId;
		_players=players;
		_gameDescription=gameDescription;
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

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<String> players) {
		_players = players;
	}

	/**
	 * @return the players
	 */
	public List<String> getPlayers() {
		return _players;
	}

	/**
	 * @param gameDescription the gameDescription to set
	 */
	public void setGameDescription(String gameDescription) {
		_gameDescription = gameDescription;
	}

	/**
	 * @return the gameDescription
	 */
	public String getGameDescription() {
		return _gameDescription;
	}
}
