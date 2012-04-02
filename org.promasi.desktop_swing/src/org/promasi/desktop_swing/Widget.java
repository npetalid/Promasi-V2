/**
 * 
 */
package org.promasi.desktop_swing;

import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

/**
 * @author alekstheod
 *
 */
public class Widget extends JXPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Widget(){
		super();
		setBounds(0, 0, 100, 100);
		final int pad = 10;
		setBorder(new EmptyBorder(pad, pad, pad, pad));
	}

}
