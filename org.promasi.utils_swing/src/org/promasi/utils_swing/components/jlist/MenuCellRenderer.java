/**
 * 
 */
package org.promasi.utils_swing.components.jlist;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * @author alekstheod
 * 
 */
public class MenuCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JLabel _imageLabel;
	
	/**
	 * 
	 * @param menuEntry
	 * @throws GuiException
	 */
	public MenuCellRenderer(){
		_imageLabel = new JLabel();
		_imageLabel.setOpaque(true);
		_imageLabel.setBackground(Color.WHITE);
	}
	
	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
		if (value instanceof IMenuEntry) {
			IMenuEntry menuEntry = (IMenuEntry) value;
			Icon icon = menuEntry.getIcon();
			_imageLabel.setIcon(icon);
			_imageLabel.setText(label.getText());
		} else {
			_imageLabel.setIcon(null);
			_imageLabel.setText(label.getText());
		}
		
		if( isSelected ){
			_imageLabel.setBackground(Color.LIGHT_GRAY);
		}else{
			_imageLabel.setBackground(Color.WHITE);
		}

		return _imageLabel;
	}

}
