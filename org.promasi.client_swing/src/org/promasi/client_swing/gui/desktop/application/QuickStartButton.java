/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.IDesktop;

/**
 * @author alekstheod
 *
 */
public class QuickStartButton extends JButton {

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
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_desktop.runApplication(_application);
			}
		});
		
		this.setRolloverEnabled(false);
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
	public boolean showPopupNotifier( String title, String message ){
		boolean result = false;
		
		if( title != null && message != null ){
	        final JPopupMenu popup = new JPopupMenu( );
	        popup.setLayout( new BorderLayout());
	        JLabel label = new JLabel( message, getIcon( ), SwingConstants.CENTER );
	        label.setFont( new Font("Arial", Font.ITALIC, 12));
	        label.addMouseListener( new MouseAdapter( ){
	            @Override
	            public void mouseClicked ( MouseEvent e ){
	                fireActionPerformed( new ActionEvent( this, 0, "" ) );
	                popup.setVisible( false );
	            }
	        });
	        popup.add( label, BorderLayout.NORTH );
	        popup.show( this, 0, getHeight( ) );
			result = true;
		}
		
		return result;
	}
}
