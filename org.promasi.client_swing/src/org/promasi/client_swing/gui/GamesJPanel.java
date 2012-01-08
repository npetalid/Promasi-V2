/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.promasi.client_swing.components.JEditorPane.ExtendedJEditorPane;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServer;
import org.promasi.game.IGamesServerListener;

/**
 * @author alekstheod
 *
 */
public class GamesJPanel extends JPanel implements IGamesServerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_GAMES_LIST_WIDTH = 200;
	
	/**
	 * 
	 */
	private JList<IGame> _gamesList;
	
	/**
	 * 
	 */
	private JButton _playButton;
	
	/**
	 * 
	 */
	private IMainFrame _mainFrame;
	
	/**
	 * 
	 */
	private ExtendedJEditorPane _infoPane;
	
	/**
	 * 
	 */
	private IGamesServer _gamesServer;
	
	/**
	 * 
	 */
	private Timer _timer;
	
	/**
	 * 
	 */
	private String _username;

	/**
	 * @throws GuiException 
	 * 
	 */
	public GamesJPanel( IMainFrame mainFrame , IGamesServer gamesServer, String username ) throws GuiException{
		super();
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null ");
		}
		
		if( gamesServer == null ){
			throw new GuiException("Wrong argument gamesServer == null ");
		}
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		_mainFrame = mainFrame;
		_gamesServer = gamesServer;
		
		DefaultListModel<IGame> listModel = new DefaultListModel<IGame>();
		
		_gamesList = new JList<IGame>(listModel);
		setLayout( new BorderLayout() );
		_gamesList.setPreferredSize(new Dimension( CONST_GAMES_LIST_WIDTH, 100 ));
		add(_gamesList, BorderLayout.EAST);
		_gamesList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				_playButton.setEnabled(true);
				IGame game = _gamesList.getSelectedValue();
				_infoPane.setText( game.getGameDescription() );
			}
		});
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout() );
		
		_playButton = new JButton("Next");
		_playButton.setPreferredSize(new Dimension(CONST_GAMES_LIST_WIDTH, 30));
		_playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_gamesServer.joinGame((IGame)_gamesList.getSelectedValue());
			}
			
		});
		
		bottomPanel.add(_playButton, BorderLayout.EAST);
		add( bottomPanel, BorderLayout.SOUTH );
		
		EtchedBorder edge = new EtchedBorder(EtchedBorder.RAISED);
		_playButton.setBorder(edge);
		_playButton.setEnabled(false);
		_gamesList.setBorder(edge);
		
		_infoPane = new ExtendedJEditorPane();
		add( _infoPane, BorderLayout.CENTER );
		_infoPane.setEditable(false);
		_infoPane.setContentType("text/html" );
		
		_username = username;
		_gamesServer.registerGamesServerListener(this);
		_timer = new Timer();
		_timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				_gamesServer.requestGamesList();
			}
			
		}, 500);
	}

	@Override
	public void updateGamesList(List<IGame> games) {
		_gamesList.setListData( new Vector< IGame >( games) );
	}

	@Override
	public void onJoinGame(IGame game) {
		try {
			_mainFrame.changePanel(new LoadingJPanel(_mainFrame,game, _username));
		} catch (GuiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
