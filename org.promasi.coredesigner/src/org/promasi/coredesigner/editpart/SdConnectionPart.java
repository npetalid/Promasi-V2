package org.promasi.coredesigner.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import org.promasi.coredesigner.editpolicies.BendpointConnectionEditPolicy;
import org.promasi.coredesigner.editpolicies.SdConnectionDeleteEditPolicy;
import org.promasi.coredesigner.editpolicies.SdConnectionPolicy;
import org.promasi.coredesigner.figure.SdConnectionFigure;
import org.promasi.coredesigner.model.SdConnection;


/**
 * This class is used to link the Connection Model and its visual representation
 * 
 * @author antoxron
 *
 */
public class SdConnectionPart extends AbstractConnectionEditPart 
	implements PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		return new SdConnectionFigure();
	}

	@Override
	protected void createEditPolicies( ) {
		installEditPolicy( EditPolicy.CONNECTION_ROLE, new SdConnectionPolicy() );
		installEditPolicy( EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy() );
		installEditPolicy( EditPolicy.CONNECTION_BENDPOINTS_ROLE, new BendpointConnectionEditPolicy() );
		installEditPolicy( EditPolicy.CONNECTION_ROLE, new SdConnectionDeleteEditPolicy() );
	}
	@Override
	public void activate( ) {
		super.activate();
		( ( SdConnection ) getModel() ).addPropertyChangeListener( this );
	}

	@Override
	public void deactivate( ) {
		( ( SdConnection ) getModel() ).removePropertyChangeListener( this );
		super.deactivate();
	}
	@Override
	public void propertyChange( PropertyChangeEvent event ) {
		if ( event.getPropertyName().equals( SdConnection.BEND_POINT ) ) {
			refreshBendpoints();
		}

	}
	protected void refreshBendpoints( ) {
		List<Point> bendpoints = ( ( SdConnection ) getModel() ).getBendpoints();
		List<Point> constraint = new ArrayList<Point>();

		for ( int i = 0; i < bendpoints.size(); i++ ) {
			constraint.add( new AbsoluteBendpoint( ( Point ) bendpoints.get( i ) ) );
		}
		getConnectionFigure().setRoutingConstraint( constraint );
	}
	
	@Override
	protected void refreshVisuals( ) {
		refreshBendpoints();
	}

}
