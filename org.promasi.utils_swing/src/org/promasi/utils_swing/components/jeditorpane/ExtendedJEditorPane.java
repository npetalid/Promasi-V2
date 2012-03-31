package org.promasi.utils_swing.components.jeditorpane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;
import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;

public class ExtendedJEditorPane extends JEditorPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088155298009386795L;
	
	/**
	 * 
	 */
	private Color _bgColor;
	
	/**
	 * 
	 */
	private Color _borderColor;
	
	/**
	 * 
	 */
	public ExtendedJEditorPane(){
		_bgColor = Colors.White.alpha(0.3f);
		_borderColor = Colors.White.alpha(1f);
		init();
	}
	
	/**
	 * @throws GuiException 
	 * 
	 */
	public ExtendedJEditorPane( Color bgColor, Color borderColor ) throws GuiException{
		if( bgColor == null ){
			throw new GuiException("Wrong argument bgColor == null");
		}
		
		if( borderColor == null ){
			throw new GuiException("Wrong argument borderColor == null");
		}
		
		_bgColor = bgColor;
		_borderColor = borderColor;
		init();
	}
	
	private void init(){
		setEditorKit(new ExtendedHTMLEditorKit());
		int bpad = 20;
		setBorder(new EmptyBorder(bpad, bpad, bpad, bpad));
		setOpaque(false);	
	}
}
