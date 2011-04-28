package org.promasi.server;

import org.promasi.utilities.serialization.SerializableObject;

/**
 * 
 * @author m1cRo
 *
 */
public class ServerSettings extends SerializableObject {
	/**
	 * Server listening port number.
	 */
	private int _portNumber;

	/**
	 * 
	 */
	public ServerSettings(){
	}
	
	/**
	 * 
	 * @param _portNumber
	 */
	public void setPortNumber(int _portNumber) {
		this._portNumber = _portNumber;
	}

	/**
	 * 
	 * @return
	 */
	public int getPortNumber() {
		return _portNumber;
	}
	
	
}
