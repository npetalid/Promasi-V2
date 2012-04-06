/**
 * 
 */
package org.promasi.utils_swing.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.EmptyBorder;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.Painter;
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
	public static final int CONST_PANEL_OFFSET = 10;

	/**
	 * Empty constructor
	 * will initialize the object
	 * with default background and
	 * border colors.
	 */
	public RoundedJPanel(){
		super();
		int bpad = CONST_PANEL_OFFSET;
		setBorder(new EmptyBorder(bpad, bpad, bpad, bpad));
		Color color = Colors.White.alpha(0.5f);
		Color borderColor = Colors.White.alpha(1f);
		init(color, borderColor);
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

		init(color, borderColor);
	}

	/**
	 * 
	 * @param bgColor
	 * @param borderColor
	 */
	private void init(Color bgColor, Color borderColor){
		int bpad = CONST_PANEL_OFFSET;
		setBorder(new EmptyBorder(bpad, bpad, bpad, bpad));
		setOpaque(false);
		setBackgroundPainter(getPainter(bgColor, borderColor) );
		setLayout(new BorderLayout());
	}
	
	/**
	 * 
	 * @param bgColor
	 * @param borderColor
	 * @return
	 */
	private Painter<Component> getPainter(Color bgColor, Color borderColor){
		RectanglePainter roundRect = new RectanglePainter(	CONST_PANEL_OFFSET,
				CONST_PANEL_OFFSET,
				CONST_PANEL_OFFSET,
				CONST_PANEL_OFFSET, 
				30,30,true, bgColor, 3, borderColor); 

	    return (new CompoundPainter<Component>(roundRect));	
	}
}
