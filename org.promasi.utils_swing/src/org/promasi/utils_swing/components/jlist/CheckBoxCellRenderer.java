/**
 * 
 */
package org.promasi.utils_swing.components.jlist;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;

import org.promasi.utils_swing.components.jeditorpane.ExtendedJEditorPane;

/**
 * @author alekstheod
 *
 */
public class CheckBoxCellRenderer<T> extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JPanel _panel;
	
	/**
	 * 
	 */
	private ExtendedJEditorPane _htmlPane;
	
	/**
	 * 
	 */
	private JCheckBox _checkBox;
	
	/**
	 * 
	 */
	public CheckBoxCellRenderer(){
		_panel = new JPanel();
		_panel.setLayout(new BorderLayout());
		_htmlPane = new ExtendedJEditorPane();
		_htmlPane.setEditable(false);
		_htmlPane.setContentType("text/html" );
		
		_htmlPane.setVisible(true);
		_htmlPane.setFocusable(true);
		_panel.add(_htmlPane, BorderLayout.CENTER);
		
		_checkBox = new JCheckBox();
		_checkBox.setSelected(false);
		_panel.add(_checkBox, BorderLayout.EAST);
		_panel.setBorder( BorderFactory.createEtchedBorder( ) );
	}

	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
		if(value instanceof CheckBoxListEntry ){
			@SuppressWarnings("unchecked")
			CheckBoxListEntry<T> entry = (CheckBoxListEntry<T>)value;
			_htmlPane.setText(entry.toString());
			_checkBox.setSelected(entry.isSelected());
		}else{
			_htmlPane.setText( value.toString() );
		}
		
		return _panel;
	}

}
