/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultListModel;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;


import org.promasi.client_swing.components.JEditorPane.ExtendedJEditorPane;
import org.promasi.client_swing.components.JList.MenuCellRenderer;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.client_swing.playmode.multiplayer.MultiPlayerPlayMode;
import org.promasi.client_swing.playmode.singleplayer.SinglePlayerPlayMode;

/**
 * @author alekstheod
 *
 */
public class PlayModesJPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_PLAYMODES_LIST_WIDTH = 200;
	
	/**
	 * 
	 */
	private JList<IPlayMode> _playModesList;
	
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
	public PlayModesJPanel( IMainFrame listener )throws GuiException{
		super();
		if( listener == null ){
			throw new GuiException("Wrong argument listener == null");
		}
		
		_mainFrame = listener;
		DefaultListModel<IPlayMode> listModel = new DefaultListModel<IPlayMode>();
		listModel.addElement( new SinglePlayerPlayMode() );
		listModel.addElement( new MultiPlayerPlayMode() );
		
		JSplitPane splitPane = new JSplitPane();
		
		_playModesList = new JList<IPlayMode>(listModel);
		setLayout( new BorderLayout() );
		add(splitPane, BorderLayout.CENTER);
		
		splitPane.setLeftComponent(_playModesList);
		_playModesList.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
				Point p = new Point(arg0.getX(),arg0.getY());
				_playModesList.setSelectedIndex(_playModesList.locationToIndex(p));
				IPlayMode playMode = _playModesList.getSelectedValue();
				if( playMode != null ){
					_infoPane.setText(playMode.getDescription());
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		_playModesList.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {	
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IPlayMode playMode = _playModesList.getSelectedValue();
				if( playMode != null ){
					playMode.gotoNextPanel(_mainFrame);
				}
			}
		});
		
		_playModesList.setCellRenderer(new MenuCellRenderer());
		
		JPanel bottomPanel = new JPanel();
		
		add( bottomPanel, BorderLayout.SOUTH );
		
		EtchedBorder edge = new EtchedBorder(EtchedBorder.RAISED);

		_playModesList.setBorder(edge);
		
		_infoPane = new ExtendedJEditorPane();
		_infoPane.setEditable(false);
		_infoPane.setContentType( "text/html" );
		splitPane.setRightComponent(_infoPane);
		splitPane.setDividerLocation( 200 );
	}
}
