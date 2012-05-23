/**
 * 
 */
package org.promasi.desktop_swing;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;

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
	 * The number of widgets available on the current frame.
	 */
	private int _widgetsNumber;
	
	/**
	 * Constructor will initialize the object.
	 */
	public WidgetsFrame(){
		setBounds(10 , 10, 200, 450);
	    setLayout(new GridLayout(1, 0));
	    setOpaque(false);  
	    getContentPane().setBackground(Colors.White.alpha(0.f)); 
	    
	    javax.swing.plaf.InternalFrameUI ifu= getUI();
	    ((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);
	    
	    setBorder(BorderFactory.createEmptyBorder());
	    UIManager.getBorder("InternalFrame.paletteBorder");
	    UIManager.getBorder("InternalFrame.optionDialogBorder");
	    _widgetsNumber = 0;
	}
	
	/**
	 * Will add the widget to the widgets frame.
	 * @param widget instance of {@link Widget}
	 * @return true if succeed, false otherwise.
	 */
	public boolean addWidget( Widget widget ){
		boolean result = false;
		
		if( widget != null ){
			add(widget);
			_widgetsNumber++;
			setBounds(10 , 10, Widget.CONST_WIDGET_WIDTH*_widgetsNumber, Widget.CONST_WIDGET_HEIGHT + 20);
			result = true;
		}
		
		return result;
	}

}
