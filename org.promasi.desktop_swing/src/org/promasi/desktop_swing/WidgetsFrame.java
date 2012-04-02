/**
 * 
 */
package org.promasi.desktop_swing;

import java.awt.GridLayout;

import javax.swing.JInternalFrame;

import org.promasi.utils_swing.Colors;

/**
 * @author alekstheod
 * Represent the widgets panel on ProMaSi
 * desktop. User can register his own widgets
 * and their will be shown on this panel.
 */
public class WidgetsFrame extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public WidgetsFrame(){
		setBounds(10 , 10,300,500);
	    setLayout(new GridLayout(0, 1));
	    setOpaque(false);  
	    getContentPane().setBackground(Colors.White.alpha(0.5f));  
	}
	
	/**
	 * 
	 * @param widget
	 * @return
	 */
	public boolean addWidget( Widget widget ){
		boolean result = false;
		
		if( widget != null ){
			add(widget);
			result = true;
		}
		
		return result;
	}

}
