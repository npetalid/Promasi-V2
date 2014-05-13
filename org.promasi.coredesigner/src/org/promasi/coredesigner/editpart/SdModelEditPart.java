package org.promasi.coredesigner.editpart;

import java.beans.PropertyChangeEvent;

import java.util.List;

import org.eclipse.draw2d.IFigure;

import org.eclipse.gef.EditPolicy;

import org.promasi.coredesigner.editpolicies.EditorEditLayoutPolicy;
import org.promasi.coredesigner.editpolicies.SdDirectEditPolicy;
import org.promasi.coredesigner.figure.SdModelFigure;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdObject;
/**
 * Represents the controller between the SdModel model and the SdModelFigure
 * 
 * @author antoxron
 *
 */
public class SdModelEditPart extends AbstractEditPart {

	
	@Override 
	protected IFigure createFigure( ) { 
		IFigure figure = new SdModelFigure(); 
		return figure; 
	}

	@Override
	protected void createEditPolicies( ) {
		installEditPolicy( EditPolicy.LAYOUT_ROLE, new EditorEditLayoutPolicy() );
		installEditPolicy( EditPolicy.DIRECT_EDIT_ROLE, new SdDirectEditPolicy() );
	}
	@Override
	protected void refreshVisuals( ) { 
		SdModelFigure figure = ( SdModelFigure )getFigure(); 
		SdModel model = ( SdModel )getModel(); 
		figure.setName( model.getName() ); 	
		this.getViewer().setProperty( SdObject.PROPERTY_RENAME, model.getName() );
		
	} 
	@Override 
	public void propertyChange( PropertyChangeEvent event ) { 
		if ( event.getPropertyName().equals( SdObject.PROPERTY_ADD ) ) {
			refreshChildren();
		} 
		if ( event.getPropertyName().equals( SdObject.PROPERTY_REMOVE ) ) {
			refreshChildren();
		}
		if ( event.getPropertyName().equals( SdObject.PROPERTY_RENAME ) ) {
			refreshVisuals();
		}
	}
	@Override
	public List<SdObject> getModelChildren( ) { 
		return ( ( SdModel )getModel() ).getChildrenArray();
	}
}