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
public class JoinGameResponse extends SerializableObject 
{
	/**
	 * 
	 */
	private String _gameName;

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
	public JoinGameResponse(){
		
	}
	
	/**
	 * 
	 * @param gameName
	 */
    public JoinGameResponse(String gameName, String gameDescription, List<String> players){
    	_gameName=gameName;
    	setGameDescription(gameDescription);
    	setPlayers(players);
    }
	
	/**
	 * @param gameName the gameName to set
	 */
	public void setGameName(String gameName) {
		_gameName = gameName;
	}

	/**
	 * @return the gameName
	 */
	public String getGameName() {
		return _gameName;
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
