/**
 * 
 */
package org.promasi.desktop_swing.application.email;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.HtmlPanel;

/**
 * @author alekstheod
 * Represent the email information panel
 * contains a sender, theme and body information fields.
 * Used in order to present the sender and receiver of
 * email.
 */
public class EmailJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Field which will present the
	 * sender information.
	 */
	private JLabel _senderField;
	
	/**
	 * Field which will present the
	 * email's theme information.
	 */
	private JLabel _themeField;
	
	/**
	 * Field which will present a
	 * body of the received email.
	 */
	private HtmlPanel _bodyPane;
	
	/**
	 * Constructor will initialize the object.
	 * @throws GuiException in case of error in HtmlPanel initialization.
	 */
	public EmailJPanel() throws GuiException{
		setLayout(new BorderLayout());
		setOpaque(false);
		setBackground(Colors.White.alpha(0f));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		
		JPanel senderPanel = new JPanel();
		senderPanel.setLayout(new BorderLayout());
		JLabel fromLabel = new JLabel("From :");
		fromLabel.setOpaque(false);
		fromLabel.setPreferredSize(new Dimension(100,20));
		senderPanel.add(fromLabel, BorderLayout.WEST);
		_senderField = new JLabel();
		_senderField.setBorder(new EmptyBorder(1,1,1,1));
		_senderField.setBackground(Colors.White.alpha(1f));
		senderPanel.add(_senderField, BorderLayout.CENTER);
		topPanel.add(senderPanel, BorderLayout.NORTH);
		
		JPanel themePanel = new JPanel();
		themePanel.setLayout(new BorderLayout());
		JLabel themeLabel = new JLabel("Theme :");
		themeLabel.setOpaque(false);
		themeLabel.setPreferredSize(new Dimension(100,20));
		themePanel.add(themeLabel, BorderLayout.WEST);
		_themeField = new JLabel();
		_themeField.setBackground(Colors.White.alpha(1f));
		_themeField.setBorder(new EmptyBorder(1,1,1,1));
		themePanel.add(_themeField, BorderLayout.CENTER);
		topPanel.add(themePanel, BorderLayout.SOUTH);
		
		_bodyPane = new HtmlPanel(true);
		add(topPanel, BorderLayout.NORTH);
		add(_bodyPane, BorderLayout.CENTER);
	}
	
	/**
	 * Will present the message, so the
	 * sender, receiver and info fields
	 * will be filled with information provided
	 * by the given instance of {@link Message}.
	 * @param message instance of {@link Message}
	 * which provides the required information
	 * in order to present it.
	 * @return true if succeed, false otherwise.
	 */
	public boolean showMessage( Message message ){
		boolean result = false;
		
		if( message != null ){
			_senderField.setText(message.getSender());
			_themeField.setText(message.getTheme());
			_bodyPane.setText(message.open());
			result = true;
		}
		
		return result;
	}

}
