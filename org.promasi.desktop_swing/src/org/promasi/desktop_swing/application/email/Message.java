package org.promasi.desktop_swing.application.email;

import org.joda.time.DateTime;
import org.promasi.utils_swing.GuiException;

/**
 * 
 * @author alekstheod
 * Represent the email message in promasi.
 * Contains all needed information such as 
 * sender, theme, and body of the email message.
 */
public class Message {
	/**
	 * boolean variable which inform about the user about a message state,
	 * there are 2 message states available in the Email client
	 * already red and not opened.
	 */
	private boolean _wasOpened;
	
	/**
	 * Message sender.
	 */
	private String _sender;
	
	/**
	 * Message theme.
	 */
	private String _theme;
	
	/**
	 * Message body.
	 */
	private String _body;
	
	/**
	 * 
	 */
	private DateTime _dateSent;
	
	/**
	 * Constructor will initialize the message
	 * _wasOpened variable will be set to false
	 * @param sender Message sender.
	 * @param theme Message theme.
	 * @param date message send date.
	 * @param messageBody Message body.
	 */
	public Message(String sender, String theme, DateTime date, String body)throws GuiException{
		if( sender == null ){
			throw new GuiException("Wrong argument sender == null");
		}
		
		if( theme == null ){
			throw new GuiException("Wrong argument theme == null");
		}
		
		if( body == null ){
			throw new GuiException("Wrong argument body == null");
		}
		
		if( date == null ){
			throw new GuiException("Wrong argument date == null ");
		}
		
		_wasOpened = false;
		_dateSent = date;
		_sender = sender;
		_theme = theme;
		_body = body;
	}
	
	/**
	 * This method will return the message's body
	 * _wasOpened variable will be set to true, so 
	 * user can open this message once.
	 * @return
	 */
	public String open(){
		_wasOpened = true;
		return _body;
	}
	
	/**
	 * Override toString method
	 * Message will be presented as an 
	 * string with sender and theme.
	 */
	@Override
	public String toString(){
		return _sender + " : " + _theme;
	}
	
	/**
	 * Sender getter.
	 * @return message sender.
	 */
	public String getSender(){
		return _sender;
	}
	
	/**
	 * Theme getter;
	 * @return theme of the message
	 */
	public String getTheme(){
		return _theme;
	}
	
	/**
	 * 
	 * @return true in case if 
	 * open method was called before,
	 * false otherwise.
	 */
	public boolean itWasOpened(){
		return _wasOpened;
	}
	
	/**
	 * Getter for the send date.
	 * @return _dateSent
	 */
	public DateTime getDate(){
		return _dateSent;
	}
}
