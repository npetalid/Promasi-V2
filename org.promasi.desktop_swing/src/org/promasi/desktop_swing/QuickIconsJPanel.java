/**
 * 
 */
package org.promasi.desktop_swing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author alekstheod
 *
 */
public class QuickIconsJPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_QUICK_ICONS_PANEL_WIDTH = 200;
	
	/**
	 * 
	 */
	public QuickIconsJPanel(){
		super();
		setBackground(Color.gray);
		setPreferredSize(new Dimension(CONST_QUICK_ICONS_PANEL_WIDTH,100));
	}

}
