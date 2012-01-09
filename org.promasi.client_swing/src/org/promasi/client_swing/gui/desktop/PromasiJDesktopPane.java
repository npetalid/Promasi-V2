/**
 * 
 */
package org.promasi.client_swing.gui.desktop;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JPopupMenu;

import org.promasi.client_swing.gui.GuiException;
import org.promasi.client_swing.gui.desktop.application.ADesktopApplication;
import org.promasi.client_swing.gui.desktop.taskbar.StartMenuJPanel;
import org.promasi.game.IGame;
import org.promasi.utilities.file.RootDirectory;

/**
 * @author alekstheod
 *
 */
public class PromasiJDesktopPane extends JDesktopPane  implements IDesktop {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public static final String CONST_BG_IMAGE_NAME = "wallpaper.jpg";
	
	/**
	 * 
	 */
	private Image _bgImage;
	
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
		
		try {
			String imagePath = RootDirectory.getInstance().getImagesDirectory() + CONST_BG_IMAGE_NAME;
			_bgImage = new ImageIcon(imagePath).getImage();
		} catch (IOException e) {
			throw new GuiException(e.toString());
		}
		
		_startMenu = new JPopupMenu();
		_startMenu.setLayout(new BorderLayout());
		StartMenuJPanel startPanel = new StartMenuJPanel( game, username, this );
		_startMenu.add(startPanel, BorderLayout.CENTER);
		
		_desktop = desktop;
	}
	
	/**
	 * 
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(_bgImage, 0, 0, this.getBounds().width, this.getBounds().height, null);
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
	public void sleep() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runApplication( ADesktopApplication application ) {
		_startMenu.setVisible(false);
		_desktop.runApplication(application);
	}

}
