package org.promasi.utils_swing.components.jeditorpane;

import javax.swing.JEditorPane;

public class ExtendedJEditorPane extends JEditorPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088155298009386795L;

	public ExtendedJEditorPane(){
		setEditorKit(new ExtendedHTMLEditorKit());
	}
}
