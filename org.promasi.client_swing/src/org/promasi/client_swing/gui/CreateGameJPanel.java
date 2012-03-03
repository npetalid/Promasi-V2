/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import org.promasi.client_swing.components.JEditorPane.ExtendedJEditorPane;
import org.promasi.client_swing.components.JList.MenuCellRenderer;
import org.promasi.game.AGamesServer;
import org.promasi.game.IGame;
import org.promasi.game.IGamesServerListener;

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
		
		_mainFrame = mainFrame;
		_gamesServer = gamesServer;
		_userName = userName;
	
		setLayout( new BorderLayout() );
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		DefaultListModel<IGame> listModel = new DefaultListModel<IGame>();
		
		_gamesList = new JList<IGame>(listModel);
		_gamesList.setCellRenderer(new MenuCellRenderer());
		splitPane.setLeftComponent(_gamesList);
		
		
		_gamesList.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				Point p = new Point(arg0.getX(),arg0.getY());
				_gamesList.setSelectedIndex(_gamesList.locationToIndex(p));
				IGame game = (IGame)_gamesList.getSelectedValue();
				if( game != null ){
					_infoPane.setText(game.getGameDescription());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		_gamesList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IGame game = (IGame)_gamesList.getSelectedValue();
				if( game != null ){
					_gamesServer.createGame(game);
				}
			}
		});
		
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
	public void onJoinGame(IGame game) {
		_gamesServer.createGame(game);
	}
}
