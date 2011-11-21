/**
 * 
 */
package org.promasi.client_swing.gui.desktop.taskbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;

/**
 * @author alekstheod
 *
 */
public class TaskBarJPanel extends JPanel {

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
	private JButton _startMenuButton;
	
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
	public TaskBarJPanel( String username, IDesktop desktop )throws GuiException{
		super();
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
		setPreferredSize( new Dimension( 100, CONST_TASKBAR_HEIGHT ) );
		
		setLayout(new BorderLayout());
		_startMenuButton = new JButton("Start");
		_startMenuButton.setPreferredSize(new Dimension(100,100));
		_startMenuButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_desktop.showStartMenu();
			}
		});
		
		add(_startMenuButton, BorderLayout.WEST);
		
		JPanel iconsPanel = new JPanel();
		iconsPanel.setLayout(new BorderLayout());
		iconsPanel.setPreferredSize(new Dimension( ClockJPanel.CONST_CLOCK_PANEL_WIDTH + QuickIconsJPanel.CONST_QUICK_ICONS_PANEL_WIDTH, 100) );
		_clockPanel = new ClockJPanel();
		iconsPanel.add(_clockPanel, BorderLayout.EAST);
		iconsPanel.add(new QuickIconsJPanel(), BorderLayout.CENTER);
		add(iconsPanel, BorderLayout.EAST);
		
		_desktop = desktop;
	}
	
	public void updateTime( DateTime dateTime ){
		_clockPanel.updateTime(dateTime);
	}
}
