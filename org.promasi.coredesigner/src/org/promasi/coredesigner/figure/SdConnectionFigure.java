package org.promasi.coredesigner.figure;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;

import org.eclipse.swt.SWT;
/**
 * Represents the visual representation of the connection between SdObjects
 * 
 * @author antoxron
 *
 */
public class SdConnectionFigure extends PolylineConnection  {
	
	public SdConnectionFigure( )  {
		BendpointConnectionRouter bendpointConnectionRoute = new BendpointConnectionRouter();
		setConnectionRouter( bendpointConnectionRoute );
	    setLineWidth( 1 );
		PolygonDecoration decoration = new PolygonDecoration();
		decoration.setTemplate( PolygonDecoration.TRIANGLE_TIP );
		setTargetDecoration( decoration );
		setLineStyle( SWT.LINE_DOT );
	}
}