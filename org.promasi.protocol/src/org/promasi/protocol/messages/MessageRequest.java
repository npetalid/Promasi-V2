package org.promasi.protocol.messages;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * 
 * @author m1cRo
 *
 */
public class MessageRequest extends SerializableObject 
{
	/**
	 * 
	 */
	private String _clientId;
	
	/**
	 * 
	 */
	private String _message;
	
	/**
	 * 
	 */
	public MessageRequest(){
	}
	
	/**
	 * 
	 * @param clientId
	 * @param message
	 */
	public MessageRequest(String clientId, String message){
		_clientId=clientId;
		_message=message;
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
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		_message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return _message;
	}
}
