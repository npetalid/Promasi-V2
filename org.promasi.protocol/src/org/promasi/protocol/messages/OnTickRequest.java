/**
 * 
 */
package org.promasi.protocol.messages;

/**
 * @author m1cRo
 *
 */
public class OnTickRequest extends Message 
{
	/**
	 * 
	 */
	private String _dateTime;
	
	/**
	 * 
	 */
	public OnTickRequest(){}
	
	/**
	 * 
	 * @param dateTime
	 */
	public OnTickRequest(String dateTime){
		setDateTime(dateTime);
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		_dateTime = dateTime;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return _dateTime;
	}
}
