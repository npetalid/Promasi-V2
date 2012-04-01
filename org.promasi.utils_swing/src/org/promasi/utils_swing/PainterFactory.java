/**
 * 
 */
package org.promasi.utils_swing;

import java.awt.Component;

import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;

/**
 * @author alekstheod
 * The painter factory needed in
 * order to generate the painter instances
 * for the different parts of ProMaSi gui.
 */
public class PainterFactory {
	
	public enum ENUM_PAINTER{
		Background,
		InactiveBackground,
		DesktopBackground
	};

	/**
	 * Will return a new generate instance of {@link Painter}
	 * class by using the {@link ENUM_PAINTER} value.
	 * @param painter type of the painter.
	 * @return instance of the {@link Painter} class.
	 */
	public static Painter<Component> getInstance(ENUM_PAINTER painter)throws GuiException{
		switch(painter){
		case Background:
			return getBackgroundPainter();
		case DesktopBackground:
		case InactiveBackground:
			return getInactiveBackgroundPainter();
		default:
			throw new GuiException("Unknown painter type requested");
		}
	}
	
	private static Painter<Component> getBackgroundPainter(){
	    MattePainter mp = new MattePainter(Colors.Black.alpha(0.8f));
	    PinstripePainter pp = new PinstripePainter(Colors.Gray.alpha(0.2f),45d);
	   return (new CompoundPainter<Component>(mp, pp));	
	}
	
	private static Painter<Component> getInactiveBackgroundPainter(){
	    MattePainter mp = new MattePainter(Colors.Black.alpha(0.8f));
	    GlossPainter gp = new GlossPainter(Colors.White.alpha(0.1f), GlossPainter.GlossPosition.TOP);
	    PinstripePainter pp = new PinstripePainter(Colors.Gray.alpha(0.2f), 45d);
	    return (new CompoundPainter<Component>(mp, pp, gp));	
	}
}
