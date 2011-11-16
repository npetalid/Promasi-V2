/**
 * 
 */
package org.promasi.client_swing.gui.taskbar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

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
	public TaskBarPanel(){
		super();
		setPreferredSize( new Dimension( 100, CONST_TASKBAR_HEIGHT ) );
		
		setLayout(new BorderLayout());
		_startMenuButton = new JButton("Start");
		add(_startMenuButton, BorderLayout.WEST);
		
		JPanel iconsPanel = new JPanel();
		iconsPanel.setLayout(new BorderLayout());
		iconsPanel.setPreferredSize(new Dimension(200, 100) );
		iconsPanel.add(new ClockPanel(), BorderLayout.EAST);
		iconsPanel.add(new QuickIconsPanel(), BorderLayout.CENTER);
		add(iconsPanel, BorderLayout.EAST);
		
	}
}
