/**
 * 
 */
package org.promasi.desktop_swing;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author alekstheod
 *
 */
public class ClockWidget extends Widget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClockWidget(){
		JPanel internalPanel = new JPanel();
		internalPanel.setLayout(new BorderLayout());
		
		setLayout(new BorderLayout());
		setVisible(true);
		setPreferredSize(new Dimension(250,200));
		setOpaque(false);
		
		add(internalPanel);
	}
}
