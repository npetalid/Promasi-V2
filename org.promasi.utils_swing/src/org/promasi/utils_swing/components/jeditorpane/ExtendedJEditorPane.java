package org.promasi.utils_swing.components.jeditorpane;

import javax.swing.JEditorPane;
import javax.swing.border.EmptyBorder;

public class ExtendedJEditorPane extends JEditorPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088155298009386795L;
	
	/**
	 * 
	 */
	public ExtendedJEditorPane(){
		init();
	}
	
	private void init(){
		setEditorKit(new ExtendedHTMLEditorKit());
		int bpad = 20;
		setBorder(new EmptyBorder(bpad, bpad, bpad, bpad));
		setOpaque(false);	
	}
}
