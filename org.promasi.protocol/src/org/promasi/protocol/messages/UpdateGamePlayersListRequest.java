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
public class UpdateGamePlayersListRequest extends SerializableObject 
{
	/**
	 * 
	 */
	private List<String> _players;

	/**
	 * 
	 */
	public UpdateGamePlayersListRequest(){
	}
	
	/**
	 * 
	 * @param players
	 */
	public UpdateGamePlayersListRequest(List<String> players){
		_players=players;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<String> getPlayers() {
		return _players;
	}

	/**
	 * 
	 * @param players
	 */
	public void setPlayers(List<String> players) {
		_players = players;
	}
	
	
}
