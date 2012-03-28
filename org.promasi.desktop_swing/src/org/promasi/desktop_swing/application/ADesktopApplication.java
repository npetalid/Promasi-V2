/**
 * 
 */
package org.promasi.desktop_swing.application;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;

import org.promasi.utils_swing.GuiException;
import org.promasi.utils_swing.components.jlist.IMenuEntry;

/**
 * @author alekstheod
 *
 */
public abstract class ADesktopApplication extends JInternalFrame implements IMenuEntry {
	
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
	 */
	private Icon _smallIcon;
	
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
		if(_appIcon.getIconHeight() <=0 || _appIcon.getIconWidth() <= 0){
			throw new GuiException("Wrong argument iconPath");
		}
		
		AffineTransform transform = new AffineTransform();
		transform.scale(0.5, 0.5);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		
		BufferedImage bufimg = new BufferedImage( _appIcon.getIconWidth(), _appIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB );
		Graphics2D graphic2d = bufimg.createGraphics();
		_appIcon.paintIcon( new Canvas(), graphic2d, 0, 0 );
		graphic2d.dispose();
		
		BufferedImage bufferedImage = op.filter(bufimg, null);
		_smallIcon = new ImageIcon(bufferedImage);
		_appName = appName;
		setFrameIcon(_smallIcon);
	}
	
	@Override
	public String toString(){
		return _appName;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public Icon getIcon()
	{
		return _appIcon;
	}
}
