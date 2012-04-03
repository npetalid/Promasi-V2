/**
 * 
 */
package org.promasi.desktop_swing;

import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.JXPanel;

/**
 * @author alekstheod
 * Represent the widget on ProMaSi
 * system.
 */
public class Widget extends JXPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final int CONST_WIDGET_WIDTH = 200;
	
	/**
	 * Default widget height. Should be mentioned
	 * that user of this class shouldn't change the height
	 * of the widget.
	 */
	public static final int CONST_WIDGET_HEIGHT = 180;
	
	/**
	 * Constructor will initialize the
	 * object.
	 */
	public Widget(){
		super();
		setBounds(0, 0, CONST_WIDGET_WIDTH, CONST_WIDGET_HEIGHT);
		final int pad = 10;
		setBorder(new EmptyBorder(pad, pad, pad, pad));
	}

	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, CONST_WIDGET_WIDTH, CONST_WIDGET_HEIGHT);
	}
}
