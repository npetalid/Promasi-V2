package org.promasi.protocol.messages;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * 
 * @author m1cRo
 *
 */
public class LoginRequest extends SerializableObject
{
	/**
	 * 
	 */
	private String _clientId;
	
	/**
	 * 
	 */
	private String _password;

	/**
	 * 
	 */
	public LoginRequest(){	
	}
	
	/**
	 * 
	 * @param clientId
	 */
	public LoginRequest(String clientId, String password){
		_clientId=clientId;
		_password=password;
	}
	
	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		_clientId = clientId;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return _clientId;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return _password;
	}
}
