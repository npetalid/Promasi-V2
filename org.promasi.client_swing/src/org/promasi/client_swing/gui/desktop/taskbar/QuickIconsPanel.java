/**
 * 
 */
package org.promasi.client_swing.gui.desktop.taskbar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author alekstheod
 *
 */
public class QuickIconsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int CONST_QUICK_ICONS_PANEL_WIDTH = 200;
	
	public QuickIconsPanel(){
		super();
		setBackground(Color.gray);
		setPreferredSize(new Dimension(CONST_QUICK_ICONS_PANEL_WIDTH,100));
	}

}