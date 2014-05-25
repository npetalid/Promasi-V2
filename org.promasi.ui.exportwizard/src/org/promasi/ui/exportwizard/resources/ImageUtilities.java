package org.promasi.ui.exportwizard.resources;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
/**
 * 
 * @author antoxron
 *
 */
public class ImageUtilities {
	
	public static Color getColor( int systemColorID ) {
		Display display = Display.getCurrent();
		return display.getSystemColor( systemColorID );
	}

}
