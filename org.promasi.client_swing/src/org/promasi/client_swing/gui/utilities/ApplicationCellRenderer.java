/**
 * 
 */
package org.promasi.client_swing.gui.utilities;

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
	public static final int CONST_PREFEREND_HEIGHT = 50;

	/**
	 * 
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
		label.setPreferredSize(new Dimension( 50, CONST_PREFEREND_HEIGHT ));
		if (value instanceof ADesktopApplication) {
			ADesktopApplication app = (ADesktopApplication) value;
			Icon icon = app.getAppIcon();
			label.setIcon(icon);
		} else {
			// Clear old icon; needed in 1st release of JDK 1.2
			label.setIcon(null);
		}

		return label;
	}

}
