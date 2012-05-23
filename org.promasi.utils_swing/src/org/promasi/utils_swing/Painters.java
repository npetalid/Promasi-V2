/**
 * 
 */
package org.promasi.utils_swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

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
public enum Painters implements Painter<Component>{

    /**
     * Available painters.
     */
    Background(getBackgroundPainter()),
    InactiveBackground(getInactiveBackgroundPainter()),
    DesktopBackground(getBackgroundPainter());

    /**
     * The background painter.
     */
    private Painter<Component> _painter;

    /**
     * Constructor will initialize the object.
     */
    Painters(Painter<Component> painter){
        _painter = painter;
    }

    /**
     * Will return a current painter.
     * @return instance of Painter<Component>
     */
    public Painter<Component> painter(){
        return _painter;
    }

    private static Painter<Component> getBackgroundPainter(){
        MattePainter mp = new MattePainter(Colors.PastelPetrol.alpha(1f));
       return (new CompoundPainter<Component>(mp)); 
    }

    private static Painter<Component> getInactiveBackgroundPainter(){
        MattePainter mp = new MattePainter(Colors.PastelPetrol.alpha(1f));
        GlossPainter gp = new GlossPainter(Colors.Gray.alpha(0.1f), GlossPainter.GlossPosition.BOTTOM);
        return (new CompoundPainter<Component>(mp, gp));    
    }

	@Override
	public void paint(Graphics2D arg0, Component arg1, int arg2, int arg3) {
		_painter.paint(arg0, arg1, arg2, arg3);
	}
}
