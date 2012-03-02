package org.promasi.server;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * 
 * @author m1cRo
 * Describes the server application
 * settings such as port number and other...
 */
public class ServerSettings extends SerializableObject {
	/**
	 * Server listening port number.
	 */
	private int _portNumber;
	
	/**
	 * Port number setter.
	 * @param _portNumber the port number.
	 */
	public void setPortNumber(int _portNumber) {
		this._portNumber = _portNumber;
	}

	/**
	 * Port number getter.
	 * @return the port number.
	 */
	public int getPortNumber() {
		return _portNumber;
	}
	
	
}
