/**
 * 
 */
package org.promasi.client_swing.components.JList;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;

import org.promasi.client_swing.components.JEditorPane.ExtendedJEditorPane;

/**
 * @author alekstheod
 *
 */
public class CheckBoxCellRenderer extends DefaultListCellRenderer {

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
		_panel.add(_checkBox, BorderLayout.EAST);
	}

	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		if( isSelected && (value instanceof CheckBoxListEntry)){
			CheckBoxListEntry entry = (CheckBoxListEntry)value;
			_checkBox.setSelected(entry.isSelected());
			_htmlPane.setText(entry.toString());
		}else{
			_htmlPane.setText( value.toString() );
		}
		
		return _panel;
	}

}
