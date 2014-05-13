package org.promasi.coredesigner.editpart;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;

import org.promasi.coredesigner.editpolicies.SdObjectDeletePolicy;
import org.promasi.coredesigner.editpolicies.EditorEditLayoutPolicy;
import org.promasi.coredesigner.editpolicies.SdConnectionPolicy;
import org.promasi.coredesigner.editpolicies.SdDirectEditPolicy;
import org.promasi.coredesigner.figure.SdFlowFigure;
import org.promasi.coredesigner.model.SdConnection;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdObject;
/**
 * Represents the controller between the SdFlow model and the SdFlowFigure
 * 
 * @author antoxron
 *
 */
public class SdFlowEditPart extends AbstractEditPart implements NodeEditPart {


	public SdFlowEditPart() {
		
	}
	@Override
	protected IFigure createFigure( ) {
		IFigure figure = new SdFlowFigure(); 
		return figure;
	}
	@Override
	protected void createEditPolicies( ) {
		installEditPolicy( EditPolicy.LAYOUT_ROLE, new EditorEditLayoutPolicy() );
		installEditPolicy( EditPolicy.DIRECT_EDIT_ROLE, new SdDirectEditPolicy() );
		installEditPolicy( EditPolicy.COMPONENT_ROLE,new SdObjectDeletePolicy() );
		installEditPolicy( EditPolicy.GRAPHICAL_NODE_ROLE,new SdConnectionPolicy() );
	}
	public List<SdConnection> getModelSourceConnections( ) {
		return ( ( SdObject ) getModel() ).getSourceConnectionArray();
	}
	public List<SdConnection> getModelTargetConnections()  {
		return ( ( SdObject ) getModel() ).getTargetConnectionArray();
	}
	@Override 
	protected void refreshVisuals(){ 
		SdFlowFigure figure = ( SdFlowFigure )getFigure(); 
		SdFlow model = ( SdFlow )getModel(); 
		figure.setName( model.getName() ); 
		figure.setLayout( model.getLayout() ); 
	}
	@Override 
	public void propertyChange(PropertyChangeEvent event) { 
		if ( event.getPropertyName().equals( SdObject.PROPERTY_RENAME ) ) {
			refreshVisuals();
		}
		if ( event.getPropertyName().equals( SdObject.PROPERTY_LAYOUT ) ) {
			refreshVisuals();
		}		
		if ( event.getPropertyName().equals( SdObject.SOURCE_CONNECTION ) ) {
			refreshSourceConnections();
		}
		if ( event.getPropertyName().equals( SdObject.TARGET_CONNECTION ) )  {
			refreshTargetConnections();
		}
		if ( event.getPropertyName().equals( SdObject.PROPERTY_ADD ) ) {
			refreshChildren(); 
		}
		if ( event.getPropertyName().equals( SdObject.PROPERTY_REMOVE ) ) {
			refreshChildren();
		}
	}
	@Override
	public ConnectionAnchor getSourceConnectionAnchor( ConnectionEditPart connection ) {	
		return new ChopboxAnchor( getFigure() );
	}
	@Override
	public ConnectionAnchor getSourceConnectionAnchor( Request request )  {
		return new ChopboxAnchor( getFigure() );
	}
	@Override
	public ConnectionAnchor getTargetConnectionAnchor( ConnectionEditPart connection ) {
		return new ChopboxAnchor( getFigure() );
	}
	@Override
	public ConnectionAnchor getTargetConnectionAnchor( Request request ) {
		return new ChopboxAnchor( getFigure() );
	}
	@Override
	public List<SdObject> getModelChildren( ) { 
		return new ArrayList<SdObject>(); 
	}
}