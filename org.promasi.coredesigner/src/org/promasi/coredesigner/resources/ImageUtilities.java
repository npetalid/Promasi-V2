package org.promasi.coredesigner.resources;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Control;
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
	
	public static void exportImageFromEditorContents( GraphicalViewer viewer, String saveFilePath, int format ) {
		
		ScalableRootEditPart rootEditPart = ( ScalableRootEditPart )viewer.getEditPartRegistry().get( LayerManager.ID );
		IFigure projectFigure = ( ( LayerManager )rootEditPart ).getLayer( LayerConstants.PRINTABLE_LAYERS );
		Rectangle projectFigureBounds = projectFigure.getBounds();		

		Control control = viewer.getControl();				
		GC controlGC = new GC( control );		
		
		Image image = new Image( null, projectFigureBounds.width, projectFigureBounds.height );
		GC imageGc = new GC( image );
		
		imageGc.setBackground( controlGC.getBackground() );
		imageGc.setForeground( controlGC.getForeground() );
		imageGc.setFont( controlGC.getFont());
		imageGc.setLineStyle( controlGC.getLineStyle() );
		imageGc.setLineWidth( controlGC.getLineWidth() );
		Graphics graphics = new SWTGraphics( imageGc );
		
		projectFigure.paint( graphics );
		
		ImageData[] imageData = new ImageData[1];
		imageData[0] = image.getImageData();
		
		ImageLoader imageLoader = new ImageLoader();
		imageLoader.data = imageData;
		imageLoader.save( saveFilePath, format );

		controlGC.dispose();
		imageGc.dispose();
		image.dispose();
	}

}
