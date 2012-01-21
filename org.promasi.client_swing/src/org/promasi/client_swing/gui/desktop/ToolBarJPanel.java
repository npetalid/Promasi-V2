/**
 * 
 */
package org.promasi.client_swing.gui.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.QuickStartButton;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class ToolBarJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_TASKBAR_HEIGHT = 30;
	
	/**
	 * 
	 */
	public static final String CONST_START_ICON = "startbutton.png";
	
	/**
	 * 
	 */
	private JButton _startButton;
	
	/**
	 * 
	 */
	public ClockJPanel _clockPanel;
	
	/**
	 * 
	 */
	private IDesktop _desktop;
	
	/**
	 * 
	 */
	private JPanel _quickMenuPanel;
	
	/**
	 * 
	 */
	private JPanel _quickButtonsJPanel;
	
	/**
	 * 
	 */
	public ToolBarJPanel( String username, IDesktop desktop )throws GuiException{
		super();
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}

		setPreferredSize( new Dimension( 100, CONST_TASKBAR_HEIGHT ) );
		setLayout(new BorderLayout());
		
		try {
			Icon startIcon = new ImageIcon(RootDirectory.getInstance().getImagesDirectory() + CONST_START_ICON);
			_startButton = new JButton("Start");
			_startButton.setIcon(startIcon);
			_startButton.setPreferredSize(new Dimension( startIcon.getIconHeight(), startIcon.getIconHeight() ));
		} catch (IOException e1) {
			_startButton = new JButton("Start");
		}
		
		_startButton.setHorizontalAlignment(SwingConstants.LEFT);
		_startButton.setPreferredSize(new Dimension(125,100));
		_startButton.setFocusPainted(false);
		_startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_desktop.showStartMenu();
			}
		});
		
		add(_startButton, BorderLayout.WEST);
		
		_quickMenuPanel = new JPanel();
		_quickMenuPanel.setLayout(new BorderLayout());
		_quickMenuPanel.setPreferredSize(new Dimension( ClockJPanel.CONST_CLOCK_PANEL_WIDTH + QuickIconsJPanel.CONST_QUICK_ICONS_PANEL_WIDTH, 100) );
		_clockPanel = new ClockJPanel();
		
		JButton exitButton = null;
		try {
			Icon exitIcon = new ImageIcon(RootDirectory.getInstance().getImagesDirectory() + "exit.png");
			exitButton = new JButton(exitIcon);
			exitButton.setPreferredSize(new Dimension( exitIcon.getIconHeight(), exitIcon.getIconHeight() ));
		} catch (IOException e1) {
			exitButton = new JButton("Exit");
		}
		
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_desktop.shutDown();
			}
		});
		
		_quickButtonsJPanel = new JPanel();
		_quickButtonsJPanel.setLayout(new FlowLayout());
		
		_quickMenuPanel.add(exitButton, BorderLayout.EAST);
		_quickMenuPanel.add(_clockPanel, BorderLayout.CENTER);
		_quickMenuPanel.add(_quickButtonsJPanel, BorderLayout.WEST);
		
		add(_quickMenuPanel, BorderLayout.EAST);
		
		_desktop = desktop;
	}
	
	/**
	 * 
	 * @param dateTime
	 */
	public void updateTime( DateTime dateTime ){
		_clockPanel.updateTime(dateTime);
	}
	
	/**
	 * 
	 * @param button
	 * @return
	 */
	public boolean addQuickStartButton( QuickStartButton button ){
		boolean result = false;
		if( button != null ){
			_quickButtonsJPanel.add(button);
			result = true;
		}
		
		return result;
	}
}
