/**
 * 
 */
package org.promasi.client_swing.components;

import java.awt.Component;
import java.awt.Dimension;

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
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
		if (value instanceof IMenuEntry) {
			IMenuEntry menuEntry = (IMenuEntry) value;
			Icon icon = menuEntry.getIcon();
			label.setPreferredSize(new Dimension( 50, icon.getIconHeight() ));
			label.setIcon(icon);
		} else {
			label.setIcon(null);
		}

		return label;
	}

}
