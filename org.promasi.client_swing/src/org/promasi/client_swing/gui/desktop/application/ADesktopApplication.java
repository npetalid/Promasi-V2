/**
 * 
 */
package org.promasi.client_swing.gui.desktop.application;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

import org.promasi.client_swing.gui.GuiException;

/**
 * @author alekstheod
 *
 */
public abstract class ADesktopApplication extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String _appName;
	
	/**
	 * 
	 */
	private Icon _appIcon;
	
	/**
	 * 
	 * @param appName
	 */
	public ADesktopApplication(String appName, String iconPath) throws GuiException {
		super(appName, true, true, true, true);
		if( appName == null){
			throw new GuiException("Wrong argument appName == null");
		}
		
		if( iconPath == null ){
			throw new GuiException("Wrong argument iconPath == null");
		}
		
		_appIcon = new ImageIcon( iconPath );
		_appName = appName;
	}
	
	@Override
	public String toString(){
		return _appName;
	}
	
	/**
	 * 
	 * @return
	 */
	public Icon getAppIcon()
	{
		return _appIcon;
	}
}
