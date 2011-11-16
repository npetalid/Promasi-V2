/**
 * 
 */
package org.promasi.client_swing.gui.taskbar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author alekstheod
 *
 */
public class ClockPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClockPanel(){
		super();
		setBackground( Color.black );
		setPreferredSize( new Dimension(100, 100) );
	}
}
