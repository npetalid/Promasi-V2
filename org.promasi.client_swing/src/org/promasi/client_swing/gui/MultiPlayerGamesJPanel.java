/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.promasi.client_swing.playmode.multiplayer.MultiPlayerGamesServer;
import org.promasi.protocol.client.ProMaSiClient;

/**
 * @author alekstheod
 * Represent the Multiplayer game choose panel
 * in ProMaSi system. This class is quite similar with
 * the {@link = GamesJPanel} with except that uses will be able
 * to make a new game by using it.
 */
public class MultiPlayerGamesJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private GamesJPanel _gamesPanel;
	
	/**
	 * Constructor will initialize the object
	 * @param mainFrame Instance of {@link = IMainFrame} interface
	 * implementation. Needed in order to change the internal frames.
	 * @param client Instance of {@link = ProMaSiClient} needed in order
	 * to communicate with a ProMaSi server.
	 * @param gamesServer The games server. 
	 * @throws GuiException In case of invalid arguments.
	 */
	public MultiPlayerGamesJPanel( String username, IMainFrame mainFrame, ProMaSiClient client, MultiPlayerGamesServer gamesServer )throws GuiException
	{
		_gamesPanel = new GamesJPanel(mainFrame, gamesServer, username);
		if( client == null ){
			throw new GuiException("Wrong argument client == null");
		}
		
		_client = client;
		setLayout(new BorderLayout());
		add(_gamesPanel, BorderLayout.CENTER);
		
		
		JPanel bottomPanel = new JPanel();
		add( bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout());
		
		JButton newGameButton = new JButton("Create New");
		bottomPanel.add(newGameButton, BorderLayout.EAST);
	}

}
