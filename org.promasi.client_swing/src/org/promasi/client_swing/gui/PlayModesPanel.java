/**
 * 
 */
package org.promasi.client_swing.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.promasi.client_swing.components.ExtendedJEditorPane;
import org.promasi.client_swing.playmode.IPlayMode;
import org.promasi.client_swing.playmode.multiplayer.MultiPlayerPlayMode;
import org.promasi.client_swing.playmode.singleplayer.SinglePlayerPlayMode;

/**
 * @author alekstheod
 *
 */
public class PlayModesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_PLAYMODES_LIST_WIDTH = 150;
	
	/**
	 * 
	 */
	private JList<IPlayMode> _playModesList;
	
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
	public PlayModesPanel( IMainFrame listener )throws GuiException{
		super();
		if( listener == null ){
			throw new GuiException("Wrong argument listener == null");
		}
		
		_mainFrame = listener;
		DefaultListModel<IPlayMode> listModel = new DefaultListModel<IPlayMode>();
		listModel.addElement( new SinglePlayerPlayMode() );
		listModel.addElement( new MultiPlayerPlayMode() );
		
		_playModesList = new JList<IPlayMode>(listModel);
		setLayout( new BorderLayout() );
		_playModesList.setPreferredSize(new Dimension( CONST_PLAYMODES_LIST_WIDTH, 100 ));
		add(_playModesList, BorderLayout.EAST);
		_playModesList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				_playButton.setEnabled(true);
				_infoPane.setText( _playModesList.getSelectedValue().getDescription() );
			}
		});
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout() );
		
		_playButton = new JButton("Next");
		_playButton.setPreferredSize(new Dimension(CONST_PLAYMODES_LIST_WIDTH, 30));
		_playButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_playModesList.getSelectedValue().gotoNextPanel(_mainFrame);
			}
		});
		
		bottomPanel.add(_playButton, BorderLayout.EAST);
		add( bottomPanel, BorderLayout.SOUTH );
		
		EtchedBorder edge = new EtchedBorder(EtchedBorder.RAISED);
		_playButton.setBorder(edge);
		_playButton.setEnabled(false);
		_playModesList.setBorder(edge);
		
		_infoPane = new ExtendedJEditorPane();
		add( _infoPane, BorderLayout.CENTER );
		_infoPane.setEditable(false);
		_infoPane.setContentType("text/html" );
	}
}
