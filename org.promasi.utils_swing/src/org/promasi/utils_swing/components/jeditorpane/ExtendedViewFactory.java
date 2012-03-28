/**
 * 
 */
package org.promasi.utils_swing.components.jeditorpane;

import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit.HTMLFactory;
import javax.swing.text.html.ImageView;

import org.promasi.utils_swing.components.ComponentException;


/**
 * @author alekstheod
 *
 */
public class ExtendedViewFactory implements ViewFactory {

	/**
	 * 
	 */
	private HTMLFactory _defaultFactory;
	
	/**
	 * 
	 */
	public ExtendedViewFactory(){
		_defaultFactory = new HTMLFactory();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.text.ViewFactory#create(javax.swing.text.Element)
	 */
	@Override
	public View create(Element elem) {
		View result = new ImageView(elem);
		if ( elem.getName() != EmbededJpegImageView.CONST_IMG_TAG_NAME ){
			result = _defaultFactory.create(elem);
		}else{
			try {
				result = new EmbededJpegImageView(elem);
			} catch (ComponentException e) {
				//Log invalid format.
			}
		}

		return result;
	}

}
