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
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;

import org.promasi.client_swing.components.JEditorPane.ExtendedJEditorPane;
import org.promasi.client_swing.components.JList.MenuCellRenderer;
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
					_gamesServer.joinGame(game);
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
