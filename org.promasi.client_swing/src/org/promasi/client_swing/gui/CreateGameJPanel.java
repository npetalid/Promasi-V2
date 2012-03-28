/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.promasi.game.AGamesServer;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServerListener;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.jeditorpane.ExtendedJEditorPane;
import org.promasi.utils_swing.components.jlist.MenuCellRenderer;

/**
 * @author alekstheod
 * Represent the panel which gives to user
 * the opportunity to create new online game.
 */
public class CreateGameJPanel extends JPanel implements IGamesServerListener {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of {@link AGamesServer} needed in order to
	 * interact with the game server.
	 */
	private AGamesServer _gamesServer;
	
	/**
	 * A list of available games.
	 */
	private JList<IGame> _gamesList;
	
	/**
	 * Represent the game name field in the form.
	 */
	private JTextField _gameNameField;
	
	/**
	 * Instance of {@link IMainFrame} needed in 
	 * order to change internal frames.
	 */
	private IMainFrame _mainFrame;
	
	/**
	 * The game information panel,
	 * will render the game description information.
	 */
	private ExtendedJEditorPane _infoPane;
	
	/**
	 * Represent the client id.
	 */
	private String _userName;
	
	/**
	 * Constructor will initialize the object.
	 * @param gamesServer instance of {@link AGamesServer} implementation.
	 * @throws GuiException in case of invalid arguments, null arguments and so on.
	 */
	public CreateGameJPanel( IMainFrame mainFrame, String userName, AGamesServer gamesServer)throws GuiException{
		if( gamesServer == null ){
			throw new GuiException("Wrong argument gamesServer == null");
		}
		
		if( mainFrame == null ){
			throw new GuiException("Wrong argument mainFrame == null");
		}
		
		if( userName == null ){
			throw new GuiException("Wrong argument userName == null");
		}
		
		setLayout(new BorderLayout());
		_mainFrame = mainFrame;
		_gamesServer = gamesServer;
		_userName = userName;
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		_gameNameField = new JTextField();
		bottomPanel.add(_gameNameField, BorderLayout.CENTER);
		add( bottomPanel, BorderLayout.SOUTH );
		
		JButton createButton = new JButton("Create");
		bottomPanel.add(createButton, BorderLayout.EAST);
		createButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IGame game = (IGame)_gamesList.getSelectedValue();
				if( game != null ){
					_gamesServer.createGame(_gameNameField.getText(), game);
				}
			}
		});
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		DefaultListModel<IGame> listModel = new DefaultListModel<IGame>();
		
		_gamesList = new JList<IGame>(listModel);
		_gamesList.setCellRenderer(new MenuCellRenderer());
		_gamesList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if( _gamesList.getSelectedValue() != null ){
					_infoPane.setText(_gamesList.getSelectedValue().getGameDescription());
				}
			}
		});
		
		splitPane.setLeftComponent(_gamesList);
		
		EtchedBorder edge = new EtchedBorder(EtchedBorder.RAISED);
		_gamesList.setBorder(edge);
		
		_infoPane = new ExtendedJEditorPane();
		splitPane.setRightComponent(_infoPane);
		splitPane.setDividerLocation(200);
		_infoPane.setEditable(false);
		_infoPane.setContentType("text/html" );
		
		_gamesServer.addListener(this);
		_gamesServer.requestGamesList();
	}

	@Override
	public void updateGamesList(final List<IGame> games) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_gamesList.setListData( new Vector< IGame >( games) );
			}
		});
	}

	@Override
	public void onJoinGame(final IGame game) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
	}
}
