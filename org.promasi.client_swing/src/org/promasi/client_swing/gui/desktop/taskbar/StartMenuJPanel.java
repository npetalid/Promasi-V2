/**
 * 
 */
package org.promasi.client_swing.gui.desktop.taskbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.promasi.client_swing.components.ApplicationCellRenderer;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.application.EmailClientDesktopApplication;
import org.promasi.client_swing.gui.desktop.application.SchedulerDesktopApplication;
import org.promasi.client_swing.gui.desktop.application.WebBrowser.WebBrowserDesktopApplication;
import org.promasi.game.IGame;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class StartMenuJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_PREFERED_WIDTH = 200;
	
	/**
	 * 
	 */
	public static final int CONST_PREFERED_HEIGHT = 350;
	
	/**
	 * 
	 */
	public static final int CONST_SHUTDOWN_PANEL_PREFERED_HEIGHT = 50;
	
	/**
	 * 
	 */
	public static final int CONST_USERNAME_LABEL_PREFERED_HEIGHT = 30;
	
	/**
	 * 
	 */
	private IDesktop _desktop;
	
	/**
	 * 
	 */
	private JList _appList;
	
	/**
	 * 
	 */
	private IGame _game;
	
	/**
	 * Constructor.
	 * @param desktop
	 */
	public StartMenuJPanel( IGame game, String username, IDesktop desktop )throws GuiException{
		super();
		
		if( desktop == null ){
			throw new GuiException( "Wrong argument desktop == null" );
		}
		
		if( username == null ){
			throw new GuiException( "Wrong argument username == null" );
		}
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		setPreferredSize(new Dimension(CONST_PREFERED_WIDTH, CONST_PREFERED_HEIGHT));
		_desktop = desktop;
		
		JLabel usernameLabel;
		try {
			usernameLabel = new JLabel(username, new ImageIcon( RootDirectory.getInstance().getImagesDirectory() + "user24.png" ), SwingConstants.LEFT);
			usernameLabel.setFont(new Font("Serif", Font.BOLD, 15));
			usernameLabel.setPreferredSize( new Dimension( CONST_PREFERED_WIDTH, CONST_USERNAME_LABEL_PREFERED_HEIGHT ) );
			add( usernameLabel, BorderLayout.NORTH );
		} catch (IOException ex) {
			throw new GuiException(ex.getMessage());
		}

		Vector<ADesktopApplication> apps = new Vector<ADesktopApplication>();
		try {
			apps.add(new SchedulerDesktopApplication( game ));
			apps.add(new EmailClientDesktopApplication(game));
			apps.add(new WebBrowserDesktopApplication(game));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		_appList = new JList(apps);
		_appList.setCellRenderer(new ApplicationCellRenderer());
		_appList.setPreferredSize(new Dimension(CONST_PREFERED_WIDTH, CONST_PREFERED_HEIGHT - CONST_SHUTDOWN_PANEL_PREFERED_HEIGHT - CONST_USERNAME_LABEL_PREFERED_HEIGHT - 20));
		add(_appList, BorderLayout.CENTER);
		_appList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ADesktopApplication app = (ADesktopApplication)_appList.getSelectedValue();
				_desktop.runApplication(app);
			}
		});
		
		JPanel shutDownPanel = new JPanel();
		shutDownPanel.setPreferredSize( new Dimension(CONST_PREFERED_WIDTH, CONST_SHUTDOWN_PANEL_PREFERED_HEIGHT ) );
		shutDownPanel.setLayout(new GridLayout(1, 2, 20, 0));
		
		JButton sleepButton = new JButton("Sleep");
		sleepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//_desktop.sleep();
			}
		});
		
		shutDownPanel.add( sleepButton );
		
		JButton shutDownButton = new JButton("ShutDown");
		shutDownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_game.removeListeners();
				_desktop.shutDown();
			}
		});

		shutDownPanel.add( shutDownButton );
		add(shutDownPanel, BorderLayout.NORTH);
		_game = game;
	}

}
