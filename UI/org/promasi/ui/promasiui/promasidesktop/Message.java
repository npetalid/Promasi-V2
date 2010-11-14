/**
 * 
 */
package org.promasi.ui.promasiui.promasidesktop;

import org.apache.commons.lang.NullArgumentException;
import org.joda.time.DateTime;

/**
 * @author m1cRo
 *
 */
public class Message 
{

	/**
	 * 
	 */
	private DateTime _dateTime;
	
	/**
	 * 
	 */
	private String _messageString;
	
	/**
	 * 
	 */
	private String _sender;
	
	/**
	 * 
	 */
	private String _title;
	
	/**
	 * 
	 * @param dateTime
	 * @param sender
	 * @param title
	 * @param messageString
	 * @throws NullArgumentException
	 */
	public Message(DateTime dateTime,String sender, String title, String messageString)throws NullArgumentException{
		if(dateTime==null){
			throw new NullArgumentException("Wrong argument dateTime==null");
		}
		
		if(messageString==null){
			throw new NullArgumentException("Wrong argument messageString==null");
		}
		
		if(sender==null){
			throw new NullArgumentException("Wrong argument sender==null");
		}
		
		if(title==null){
			throw new NullArgumentException("Wrong argument title==null");
		}
		
		_sender=sender;
		_title=title;
		_dateTime=dateTime;
		_messageString=messageString;
	}
	
	/**
	 * 
	 * @return
	 */
	public DateTime getDate(){
		return _dateTime;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getBody(){
		return _messageString;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getTitle(){
		return _title;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSender(){
		return _sender;
	}
}
