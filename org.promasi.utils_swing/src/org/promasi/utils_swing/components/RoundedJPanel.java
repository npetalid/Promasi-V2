/**
 * 
 */
package org.promasi.utils_swing.components;

import java.awt.Color;
import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.RectanglePainter;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 * Represent a JPanel with a rounded corners.
 */
public class RoundedJPanel extends JXPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default offset.
	 */
	private static final int CONST_PANEL_OFFSET = 20;
	
	/**
	 * Empty constructor
	 * will initialize the object
	 * with default background and
	 * border colors.
	 */
	public RoundedJPanel(){
		super();
		int bpad = 80;
		setBorder(new EmptyBorder(bpad, bpad, bpad, bpad));
		Color color = Colors.White.alpha(0.5f);
		RectanglePainter roundRect = new RectanglePainter(	CONST_PANEL_OFFSET,
															CONST_PANEL_OFFSET,
															CONST_PANEL_OFFSET,
															CONST_PANEL_OFFSET, 
															30,30,true, color, 3, Colors.White.alpha(0.8f)); 
		setBackgroundPainter(roundRect);
		init();
	}
	
	/**
	 * Constructor with a background color
	 * and border color as arguments will 
	 * initialize the object.
	 * @param color the background color.
	 * @param borderColor the border color.
	 * @throws GuiException in case of invalid arguments.
	 */
	public RoundedJPanel(Color color, Color borderColor)throws GuiException{
		super();
		if( color == null ){
			throw new GuiException("Wrong argument color == null");
		}
		
		if( borderColor == null ){
			throw new GuiException("Wrong argument borderColor == null");
		}
		
		RectanglePainter roundRect = new RectanglePainter(20,20,20,20, 30,30,true, color, 3, borderColor); 
		setBackgroundPainter(roundRect);
		init();
	}

	private void init(){
		int bpad = CONST_PANEL_OFFSET*2;
		setBorder(new EmptyBorder(bpad, bpad, bpad, bpad));
		setOpaque(false);
	}
}
