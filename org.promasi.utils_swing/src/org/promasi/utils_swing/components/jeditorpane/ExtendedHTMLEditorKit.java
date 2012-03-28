/**
 * 
 */
package org.promasi.utils_swing.components.jeditorpane;

import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;

/**
 * @author alekstheod
 *
 */
public class ExtendedHTMLEditorKit extends HTMLEditorKit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5627554003893994801L;

	/**
	 * 
	 */
	public ViewFactory getViewFactory(){
		return new ExtendedViewFactory();
	}
}
