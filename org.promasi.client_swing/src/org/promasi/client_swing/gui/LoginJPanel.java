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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.promasi.client_swing.playmode.multiplayer.MultiPlayerGamesServer;
import org.promasi.protocol.client.IClientState;
import org.promasi.protocol.client.ProMaSiClient;
import org.promasi.protocol.messages.LoginFailedResponse;
import org.promasi.protocol.messages.LoginRequest;
import org.promasi.protocol.messages.LoginResponse;

/**
 * @author alekstheod
 * Represent the Login panel on
 * ProMaSi system.
 */
public class LoginJPanel extends JPanel implements IClientState{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		_usernameField = new JTextField();
		_passwordField = new JPasswordField();
		setLayout(null);
		
		JLabel userNameLabel =  new JLabel("Username");
		userNameLabel.setBounds(300, 200, 150, 30);
		add( userNameLabel );
		userNameLabel.setFont(new Font("Arial", Font.BOLD, 25));
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(300, 280, 150, 30);
		passwordLabel.setFont(new Font("Arial", Font.BOLD, 25));
		
		add( _usernameField );
		_usernameField.setBounds(500, 200, 300, 30);
		_usernameField.setFont(new Font("Arial", Font.BOLD, 25));
		
		add( _passwordField );
		_passwordField.setBounds(500, 280, 300, 30);
		_passwordField.setFont(new Font("Arial", Font.BOLD, 25));
		
		JButton loginButton = new JButton("Next");
		add( loginButton );
		loginButton.setBounds(800, 400, 100, 30);
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_client.sendMessage(new LoginRequest(_usernameField.getText(), new String(_passwordField.getPassword())).serialize());
			}
		});
		
		add( passwordLabel );
		
		_client.changeState(this);
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
							//TODO log.
						}
					}
				});
				
			}else if( object instanceof LoginFailedResponse){
				
			}else{
				
			}
		}catch( Exception e){
			
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				_mainFrame.setResizable(true);
			}
		});
	}

	@Override
	public void onSetState(ProMaSiClient client, IClientState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnect(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionError(ProMaSiClient client) {
		// TODO Auto-generated method stub
		
	}
}
