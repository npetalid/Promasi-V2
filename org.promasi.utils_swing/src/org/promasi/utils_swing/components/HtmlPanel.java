/**
 * 
 */
package org.promasi.utils_swing.components;

import java.awt.BorderLayout;
import java.awt.Color;

import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.jeditorpane.ExtendedJEditorPane;

/**
 * @author alekstheod
 * Represent the Html panel.
 * This panel is based on the RoundedJPanel class.
 */
public class HtmlPanel extends RoundedJPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of {@link ExtendedJEditorPane} which represent
	 * the html pane needed in order to draw the html content.
	 */
	private ExtendedJEditorPane _htmlPane;
	
	/**
	 * Emtry contructor will initialize the object
	 * with default values.
	 * @throws GuiException 
	 */
	public HtmlPanel() throws GuiException{
		super(Colors.White.alpha(0.2f), Colors.White.alpha(1f));
		init();
	}
	
	/**
	 * 
	 * @param color
	 * @param borderColor
	 * @throws GuiException
	 */
	public HtmlPanel(Color color, Color borderColor) throws GuiException{
		super(color, borderColor);
		init();
	}
	
	/**
	 * 
	 */
	private void init(){
		setLayout(new BorderLayout());
		_htmlPane = new ExtendedJEditorPane();
		_htmlPane.setEditable(false);
		_htmlPane.setContentType("text/html");
		add(_htmlPane, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 * @param text
	 */
	public void setText( String text ){
		_htmlPane.setText(text);
	}
}
