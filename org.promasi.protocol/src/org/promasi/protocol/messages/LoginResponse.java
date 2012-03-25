/**
 * 
 */
package org.promasi.protocol.messages;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author m1cRo
 * Represent the valid login response in
 * ProMaSi system. This response should be send as
 * response to the LoginRequest message, in case if
 * login succeed.
 */
public class LoginResponse extends Message 
{
	/**
	 * List of available games.
	 */
	private Map<String, String> _availableGames;
	
	/**
	 * The username of connected user.
	 */
	private String _userName;

	/**
	 * Empty constructor will initialize the
	 * object.
	 */
	public LoginResponse(){
		_availableGames = new TreeMap<String, String>();
	}
	
	/**
	 * 
	 * @param availableGames
	 */
	public LoginResponse(String userName, Map<String, String> availableGames){
		_availableGames=availableGames;
		setUserName(userName);
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

	/**
	 * @return the _userName
	 */
	public String getUserName() {
		return _userName;
	}

	/**
	 * @param _userName the _userName to set
	 */
	public void setUserName(String _userName) {
		this._userName = _userName;
	}
}
