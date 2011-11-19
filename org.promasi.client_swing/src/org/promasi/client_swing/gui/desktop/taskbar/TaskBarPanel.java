/**
 * 
 */
package org.promasi.client_swing.gui.desktop.taskbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.joda.time.DateTime;
import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;

/**
 * @author alekstheod
 *
 */
public class TaskBarPanel extends JPanel {

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
	public ClockPanel _clockPanel;
	
	/**
	 * 
	 */
	private JPopupMenu _startMenu;
	
	/**
	 * 
	 */
	private IDesktop _desktop;

	/**
	 * 
	 */
	public TaskBarPanel( String username, IDesktop desktop )throws GuiException{
		super();
		
		if( username == null || username.isEmpty() ){
			throw new GuiException("Wrong argument username");
		}
		
		setPreferredSize( new Dimension( 100, CONST_TASKBAR_HEIGHT ) );
		
		setLayout(new BorderLayout());
		_startMenuButton = new JButton("Start");
		_startMenuButton.setPreferredSize(new Dimension(100,100));
		_startMenuButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_startMenu.show(_startMenuButton , _startMenuButton.getX(), (int)(_startMenuButton.getY() - _startMenu.getPreferredSize().getHeight()) );
			}
		});
		
		add(_startMenuButton, BorderLayout.WEST);
		
		JPanel iconsPanel = new JPanel();
		iconsPanel.setLayout(new BorderLayout());
		iconsPanel.setPreferredSize(new Dimension( ClockPanel.CONST_CLOCK_PANEL_WIDTH + QuickIconsPanel.CONST_QUICK_ICONS_PANEL_WIDTH, 100) );
		_clockPanel = new ClockPanel();
		iconsPanel.add(_clockPanel, BorderLayout.EAST);
		iconsPanel.add(new QuickIconsPanel(), BorderLayout.CENTER);
		add(iconsPanel, BorderLayout.EAST);
		

		_startMenu = new JPopupMenu();
		_startMenu.setLayout(new BorderLayout());
		StartPanel startPanel = new StartPanel();
		_startMenu.add(startPanel, BorderLayout.CENTER);
		
		JLabel usernameLabel = new JLabel(username);
		usernameLabel.setPreferredSize( new Dimension( startPanel.getPreferredSize().width, 20  ) );
		_startMenu.add( usernameLabel, BorderLayout.NORTH );
		_desktop = desktop;
		
	}
	
	public void updateTime( DateTime dateTime ){
		_clockPanel.updateTime(dateTime);
	}
}
