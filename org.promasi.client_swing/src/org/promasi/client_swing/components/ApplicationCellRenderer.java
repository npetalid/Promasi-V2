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

import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;

/**
 * @author alekstheod
 * 
 */
public class ApplicationCellRenderer extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
		if (value instanceof ADesktopApplication) {
			ADesktopApplication app = (ADesktopApplication) value;
			Icon icon = app.getAppIcon();
			label.setPreferredSize(new Dimension( 50, icon.getIconHeight() ));
			label.setIcon(icon);
		} else {
			label.setIcon(null);
		}

		return label;
	}

}
