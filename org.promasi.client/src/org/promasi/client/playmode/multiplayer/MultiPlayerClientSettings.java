package org.promasi.client.playmode.multiplayer;

import org.promasi.utilities.serialization.SerializableObject;

public class MultiPlayerClientSettings extends SerializableObject
{
	/**
	 * 
	 */
	private String _hostName;
	
	/**
	 * 
	 */
	private int _portNumber;
	
	/**
	 * 
	 */
	public MultiPlayerClientSettings(){
	}
	
	/**
	 * 
	 * @param _hostName
	 */
	public void setHostName(String _hostName) {
		this._hostName = _hostName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getHostName() {
		return _hostName;
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
