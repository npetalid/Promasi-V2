package org.promasi.protocol.messages;

import java.util.Map;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * 
 * @author m1cRo
 *
 */
public class UpdateGameListRequest extends SerializableObject 
{
	/**
	 * 
	 */
	private Map<String, String> _availableGames;

	/**
	 * 
	 */
	public UpdateGameListRequest(){
	}
	
	/**
	 * 
	 * @param availableGames
	 */
	public UpdateGameListRequest(Map<String, String> availableGames){
		_availableGames=availableGames;
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, String> getAvailableGames() {
		return _availableGames;
	}

	/**
	 * 
	 * @param availableGames
	 */
	public void setAvailableGames(Map<String, String> availableGames) {
		_availableGames = availableGames;
	}
}
