/**
 * 
 */
package org.promasi.desktop_swing.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.promasi.utils_swing.Colors;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.RoundedJPanel;

/**
 * @author alekstheod
 * Represents the notification message popup on promasi
 * system.
 */
public class NotificationJPopupMenu extends JPopupMenu{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor will initialize the object.
	 * @param message
	 * @param icon
	 * @throws GuiException
	 */
	public NotificationJPopupMenu(String message, Icon icon) throws GuiException{
		if( message == null ){
			throw new GuiException("Wrong argument message == null");
		}
		
		if( icon == null ){
			throw new GuiException("Wrong argument icon == null");
		}
		
        setOpaque(false);
        setBackground(Colors.White.alpha(0f));
        setBorder(new EmptyBorder(0,0,0,0));
        setLayout( new BorderLayout());
        RoundedJPanel bgPanel = new RoundedJPanel(Colors.Orange.alpha(0.5f), Colors.Gray.alpha(0.5f));     
        JLabel label = new JLabel( message, icon, SwingConstants.CENTER );
        //label.setForeground(Colors.Gray.alpha(1f));
        label.setFont( new Font("Arial", Font.BOLD, 12));
        label.setOpaque(false);
        label.addMouseListener( new MouseAdapter( ){
            @Override
            public void mouseClicked ( MouseEvent e ){
            	NotificationJPopupMenu.this.setVisible( false );
            }
        });
        
        bgPanel.add( label, BorderLayout.NORTH );
        add(bgPanel);
        label.setPreferredSize(new Dimension(label.getPreferredSize().width+20, label.getPreferredSize().height));
        setPreferredSize(new Dimension(label.getPreferredSize().width + 40, label.getPreferredSize().height + 20));
	}

}
