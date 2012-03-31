/**
 * 
 */
package org.promasi.desktop_swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JDesktopPane;
import javax.swing.JPopupMenu;

import org.promasi.desktop_swing.application.ADesktopApplication;
import org.promasi.desktop_swing.application.QuickStartButton;
import org.promasi.game.IGame;
import org.promasi.utils_swing.GuiException;


/**
 * @author alekstheod
 * Represent the workspace place on ProMaSi desktop.
 * the workspace place can accept some internal frames
 * as a Desktop applications.
 */
public class PromasiJDesktopPane extends JDesktopPane  implements IDesktop {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JPopupMenu _startMenu;
	
	/**
	 * 
	 */
	private IDesktop _desktop;
	
	/**
	 * @throws GuiException 
	 * 
	 */
	public PromasiJDesktopPane( IGame game, String username , IDesktop desktop) throws GuiException{
		super();
		
		if ( desktop == null ){
			throw new GuiException("Wrong argument desktop == null");
		}
		
		if( game == null ){
			throw new GuiException("Wrong argument game == null");
		}
		
		_startMenu = new JPopupMenu();
		_startMenu.setBackground(new Color(255, 255, 255, 100));
		_startMenu.setLayout(new BorderLayout());
		
		_desktop = desktop;
		StartMenuJPanel startPanel = new StartMenuJPanel( game, username, this );
		_startMenu.add(startPanel, BorderLayout.CENTER);
	}

	public void showStartMenu() {
		_startMenu.show(this , 5, 0 );
	}

	@Override
	public void shutDown() {
		_startMenu.setVisible(false);
		_desktop.shutDown();
	}

	@Override
	public void runApplication( ADesktopApplication application ) {
		_startMenu.setVisible(false);
		_desktop.runApplication(application);
	}

	@Override
	public boolean addQuickStartButton(QuickStartButton button) {
		return _desktop.addQuickStartButton(button);
	}

	@Override
	public boolean showMessageBox(Object message, String title, int messageType, Icon icon) {
		return _desktop.showMessageBox(message, title, messageType, icon);
	}

	@Override
	public JDesktopPane getDesktopPane() {
		return this;
	}
}
