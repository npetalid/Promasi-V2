/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.promasi.client_swing.playmode.multiplayer.MultiPlayerGamesServer;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServerListener;
import org.promasi.protocol.client.ProMaSiClient;

/**
 * @author alekstheod
 * Represent the Multiplayer game choose panel
 * in ProMaSi system. This class is quite similar with
 * the {@link = GamesJPanel} with except that uses will be able
 * to make a new game by using it.
 */
public class MultiPlayerGamesJPanel extends JPanel implements IGamesServerListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of {@link = ProMaSiClient}
	 * needed in order to communicate with the ProMaSi server.
	 */
	private ProMaSiClient _client;
	
	/**
	 * 
	 */
	private GamesJPanel _gamesPanel;
	
	/**
	 * 
	 */
	private IMainFrame _mainFrame;
	
	/**
	 * 
	 */
	private String _username;
	
	/**
	 * 
	 */
	private MultiPlayerGamesServer _gamesServer;
	
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
		
		if( gamesServer == null ){
			throw new GuiException("Wrong argument gamesServer == null");
		}
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null");
		}

		if( username == null ){
			throw new GuiException("Wrong argument username == null");
		}
		
		_client = client;
		_gamesServer = gamesServer;
		_mainFrame = mainFrame;
		_username = username;
		
		setLayout(new BorderLayout());
		add(_gamesPanel, BorderLayout.CENTER);
		
		
		JPanel bottomPanel = new JPanel();
		add( bottomPanel, BorderLayout.SOUTH);
		bottomPanel.setLayout(new BorderLayout());
		
		JButton newGameButton = new JButton("Create New");
		bottomPanel.add(newGameButton, BorderLayout.EAST);
	}

	@Override
	public void updateGamesList(List<IGame> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJoinGame(IGame game) {
		// TODO Auto-generated method stub
		
	}

}
