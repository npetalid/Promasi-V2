/**
 * 
 */
package org.promasi.utils_swing.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

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
	public HtmlPanel(boolean useScrollPane) throws GuiException{
		super(Colors.White.alpha(0.2f), Colors.White.alpha(1f));
		init(useScrollPane);
	}
	
	/**
	 * Constructor will initialize the object.
	 * @param color The background color.
	 * @param borderColor The border color.
	 * @param useScrollPane true if the instance of {@link JScrollPane} should
	 * be placed, so scrollbars will be used.
	 * @throws GuiException in case of invalid arguments.
	 */
	public HtmlPanel(Color color, Color borderColor, boolean useScrollPane) throws GuiException{
		super(color, borderColor);
		init(useScrollPane);
	}
	
	/**
	 * Will initialize the gui objects.
	 * @param useScrollPane true if the scrollpane will be
	 * used, false otherwise.
	 */
	private void init(boolean useScrollPane){
		setLayout(new BorderLayout());
		_htmlPane = new ExtendedJEditorPane();
		_htmlPane.setEditable(false);
		_htmlPane.setContentType("text/html");
		
		if( useScrollPane ){
			JScrollPane scrollPane = new JScrollPane(_htmlPane);
			scrollPane.setBorder(new EmptyBorder(	RoundedJPanel.CONST_PANEL_OFFSET, 
													RoundedJPanel.CONST_PANEL_OFFSET, 
													RoundedJPanel.CONST_PANEL_OFFSET, 
													RoundedJPanel.CONST_PANEL_OFFSET));
			
			scrollPane.setOpaque(false);
			scrollPane.setBackground(Colors.White.alpha(0f));
			scrollPane.getViewport().setOpaque(false);
			scrollPane.getViewport().setBackground(Colors.White.alpha(0f));
			add(scrollPane, BorderLayout.CENTER);
		}
		else
		{
			add(_htmlPane, BorderLayout.CENTER);
		}
	}
	
	/**
	 * Will set the content to the html panel
	 * @param text a string which represents a content.
	 */
	public void setText( String text ){
		_htmlPane.setText(text);
	}
}
