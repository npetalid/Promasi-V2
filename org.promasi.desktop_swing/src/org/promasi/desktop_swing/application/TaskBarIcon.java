/**
 * 
 */
package org.promasi.desktop_swing.application;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;

import org.promasi.desktop_swing.IDesktop;
import org.promasi.utils_swing.GuiException;

/**
 * @author alekstheod
 * Represent the task bar icon on the
 * ProMaSi's desktop's task bar.
 */
public class TaskBarIcon extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance of {@link IDesktop} interface implementation.
	 * Needed in order to interact with the desktop space.
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
	public TaskBarIcon( ADesktopApplication applicaiton, IDesktop desktop)throws GuiException{
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
	 * @param message
	 * @return
	 */
	public boolean showPopupNotifier( String message ){
		boolean result = false;
		
		if( message != null ){
			try {
		        final JPopupMenu popup = new NotificationJPopupMenu( message, _application.getIcon());
		        popup.show( _desktop.getDesktopPane(), _desktop.getDesktopPane().getWidth() -  popup.getPreferredSize().width - 40 , 5 );
				result = true;
			} catch (GuiException e1) {
				e1.printStackTrace();
			}
		}
		
		return result;
	}
}
