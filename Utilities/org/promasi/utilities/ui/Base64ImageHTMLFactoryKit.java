package org.promasi.utilities.ui;

import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;
import javax.swing.text.html.ParagraphView;


public class Base64ImageHTMLFactoryKit extends HTMLEditorKit {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	Base64ImageHTMLFactory _htmlFactory = new Base64ImageHTMLFactory(); 
    
    private class Base64ImageHTMLFactory extends HTMLFactory {

        public View create(Element elem) {
            Object object = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
            
            if (object instanceof HTML.Tag) {
                HTML.Tag kind = (HTML.Tag) object;
                if (kind == HTML.Tag.IMG){
                	Base64ImageView imgView = new Base64ImageView(elem);
                    return imgView;
                }
            }
            
            try {
                return super.create(elem);
            } catch(Exception e) {
                return new ParagraphView(elem);
            }
        }
    }
    
    public ViewFactory getViewFactory() {
        return _htmlFactory;
    }
}
