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
	
	private int _widgetsNumber;
	
	/**
	 * 
	 */
	public WidgetsFrame(){
		setBounds(10 , 10, 200, 450);
	    setLayout(new GridLayout(0, 1));
	    setOpaque(false);  
	    getContentPane().setBackground(Colors.White.alpha(0.3f)); 
	    setBorder(null);
	    putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
	    _widgetsNumber = 0;
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
			_widgetsNumber++;
			setBounds(10 , 10, 200, Widget.CONST_WIDGET_HEIGHT*_widgetsNumber);
			result = true;
		}
		
		return result;
	}

}
