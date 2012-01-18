/**
 * 
 */
package org.promasi.client_swing.components.JList;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import org.promasi.client_swing.components.JEditorPane.ExtendedJEditorPane;

/**
 * @author alekstheod
 *
 */
public class HtmlCellRenderer extends DefaultListCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private ExtendedJEditorPane _htmlPane;
	
	/**
	 * 
	 */
	public HtmlCellRenderer(){
		_htmlPane = new ExtendedJEditorPane();
		_htmlPane.setEditable(false);
		_htmlPane.setContentType("text/html" );
		
		_htmlPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		_htmlPane.setVisible(true);
		_htmlPane.setFocusable(true);
	}
	
	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
		if( isSelected ){
			_htmlPane.setText("<body bgcolor=\"Silver\">" + value.toString() +"</body>" );
		}else{
			_htmlPane.setText( value.toString() );
		}	

		return _htmlPane;
	}
}
