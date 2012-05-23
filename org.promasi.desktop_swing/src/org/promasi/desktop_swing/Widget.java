/**
 * 
 */
package org.promasi.desktop_swing;

import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.RoundedJPanel;

/**
 * @author alekstheod
 * Represent the widget on ProMaSi
 * system.
 */
public class Widget extends RoundedJPanel {

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
	 * @throws GuiException in case of initialization error.
	 */
	public Widget() throws GuiException{
		super(Colors.Blue.alpha(0.5f), Colors.LightBlue.alpha(1f));
		setBounds(0, 0, CONST_WIDGET_WIDTH, CONST_WIDGET_HEIGHT);
	}

	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, CONST_WIDGET_WIDTH, CONST_WIDGET_HEIGHT);
	}
}
