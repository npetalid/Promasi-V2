/**
 * 
 */
package org.promasi.client_swing.gui;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.promasi.client_swing.playmode.multiplayer.MultiPlayerGamesServer;
import org.promasi.protocol.client.IClientListener;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.LoginFailedResponse;
import org.promasi.protocol.messages.LoginRequest;
import org.promasi.protocol.messages.LoginResponse;
import org.promasi.utilities.logger.ILogger;
import org.promasi.utilities.logger.LoggerFactory;
import org.promasi.utils_swing.GuiException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * @author alekstheod
 * Represent the Login panel on
 * ProMaSi system.
 */
public class LoginJPanel extends JPanel implements IClientListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of {@link = ILogger} needed for logging.
	 */
	private static final ILogger _logger = LoggerFactory.getInstance(LoginJPanel.class);
	
	/**
	 * Instance of {@link = ProMaSiClient}
	 * Needed in order to communicate with a
	 * ProMaSi server.
	 */
	private ProMaSiClient _client;
	
	/**
	 * Instance of {@link = IMainFrame } interface implementation.
	 * Which represent the main windows on ProMaSi system.
	 */
	private IMainFrame _mainFrame;
	
	/**
	 * The user name input text field.
	 */
	private JTextField _usernameField;
	
	/**
	 * Password input text field.
	 */
	private JPasswordField _passwordField;
	
	/**
	 * Constructor will initialize the object.
	 * @param _mainFrame Implementation of {@link = IMainFrame} interface
	 * which represent the main window of ProMaSi system.
	 * @param client Instance of {@link = ProMaSiClient} needed to interact with
	 * the ProMaSi server.
	 * @throws GuiException In case of invalid arguments.
	 */
	public LoginJPanel( IMainFrame mainFrame, ProMaSiClient client )throws GuiException{
		if( client == null ){
			throw new GuiException("Wrong argument client == null");
		}
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null");
		}
		
		_client = client;
		_mainFrame = mainFrame;
		
		JLabel userNameLabel =  new JLabel("Username");
		userNameLabel.setFont(new Font("Arial", Font.BOLD, 25));
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 25));
		
		_usernameField = new JTextField();
		_usernameField.setFont(new Font("Arial", Font.BOLD, 25));

		_passwordField = new JPasswordField();
		_passwordField.setFont(new Font("Arial", Font.BOLD, 25));
		
		JButton loginButton = new JButton("Next");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_client.sendMessage(new LoginRequest(_usernameField.getText(), new String(_passwordField.getPassword())));
			}
		});
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PlayModesJPanel panel = new PlayModesJPanel(_mainFrame);
					_mainFrame.changePanel(panel);
				} catch (GuiException e1) {
					_logger.error("PlayModesJPanel initialization failed");
				}

			}
		});
		
		_client.addListener(this);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(backButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 262, Short.MAX_VALUE)
							.addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(userNameLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(_usernameField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(_passwordField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
					.addGap(73))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(101)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(userNameLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(_usernameField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(_passwordField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(57)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(backButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(50))
		);
		setLayout(groupLayout);
	}

	@Override
	public void onReceive(ProMaSiClient client, String recData) {
		
		try
		{
			Object object=new XMLDecoder(new ByteArrayInputStream(recData.getBytes())).readObject();
			if(object instanceof LoginResponse){
				final LoginResponse resp = (LoginResponse)object;
				final MultiPlayerGamesServer gamesServer = new MultiPlayerGamesServer(_client);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							final MultiPlayerGamesJPanel gamesPanel = new MultiPlayerGamesJPanel(resp.getUserName(), _mainFrame, _client, gamesServer );
							_mainFrame.changePanel(gamesPanel);
						} catch (GuiException e) {
							_logger.error("Unable to create MultiPlayerGamesJPanel because an exception was thrown : " + e.getMessage());
						}
					}
				});
				
			}else if( object instanceof LoginFailedResponse){
				_logger.warn("Login failed");
				JOptionPane.showMessageDialog(this, "Login failed\nPlease check your username and password\nand try again.");
			}
		}catch( Exception e){
			_logger.error("Invalid message received : " + recData);
		}
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		_logger.warn("Connection lost");
	}

	@Override
	public void onConnect(ProMaSiClient client) {}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		_logger.warn("Connection error");
	}
}
