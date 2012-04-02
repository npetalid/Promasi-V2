/**
 * 
 */
package org.promasi.desktop_swing.application.scheduler;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import org.promasi.desktop_swing.Widget;
import org.promasi.utils_swing.Colors;

/**
 * @author alekstheod
 *
 */
public class ProductivityWidget extends Widget{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductivityWidget(){
		setLayout(new BorderLayout());
		add(new JLabel("TEST"));
		setOpaque(false);
		setBackground(Colors.White.alpha(0f));
	}
}
