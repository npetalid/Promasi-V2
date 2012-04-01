/**
 * 
 */
package org.promasi.desktop_swing.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.promasi.desktop_swing.IDesktop;
import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.RoundedJPanel;

/**
 * @author alekstheod
 *
 */
public class QuickStartButton extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private IDesktop _desktop;
	
	/**
	 * 
	 */
	private ADesktopApplication _application;

	/**
	 * Constructor will initialize the QuickStart button.
	 * @param applicaiton application related with this button.
	 * @param desktop instance of \see = IDesktop interface implementation.
	 * @throws GuiException will be thrown in case of invalid argument
	 */
	public QuickStartButton( ADesktopApplication applicaiton, IDesktop desktop)throws GuiException{
		if( applicaiton == null ){
			throw new GuiException("Wrong argument application == null");
		}
		
		if( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
		_application = applicaiton;
		_desktop = desktop;

		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				_desktop.runApplication(_application);
			}
		});
		
		//this.setRolloverEnabled(false);
		setBorder(BorderFactory.createEmptyBorder());
		setIcon(applicaiton.getFrameIcon());
		setPreferredSize(new Dimension(applicaiton.getFrameIcon().getIconWidth(), applicaiton.getFrameIcon().getIconHeight()));
	}
	
	/**
	 * 
	 * @param title
	 * @param message
	 * @return
	 */
	public boolean showPopupNotifier( String message ){
		boolean result = false;
		
		if( message != null ){
	        final JPopupMenu popup = new JPopupMenu( );
	        popup.setOpaque(false);
	        popup.setBorder(new EmptyBorder(0,0,0,0));
	        popup.setLayout( new BorderLayout());
	        RoundedJPanel bgPanel = new RoundedJPanel();
	        
	        JLabel label = new JLabel( message, _application.getIcon(), SwingConstants.CENTER );
	        label.setFont( new Font("Arial", Font.TRUETYPE_FONT, 12));
	        label.setOpaque(false);
	        label.addMouseListener( new MouseAdapter( ){
	            @Override
	            public void mouseClicked ( MouseEvent e ){
	                popup.setVisible( false );
	            }
	        });
	        
	        bgPanel.add( label, BorderLayout.NORTH );
	        popup.add(bgPanel);
	        label.setPreferredSize(new Dimension(label.getPreferredSize().width+20, label.getPreferredSize().height));
	        popup.show( _desktop.getDesktopPane(), _desktop.getDesktopPane().getWidth() -  label.getPreferredSize().width - 40 , 5 );
			result = true;
		}
		
		return result;
	}
}
