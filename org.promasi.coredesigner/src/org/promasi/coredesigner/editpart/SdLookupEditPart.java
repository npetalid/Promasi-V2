package org.promasi.coredesigner.editpart;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;

import org.eclipse.gef.EditPolicy;

import org.promasi.coredesigner.editpolicies.SdObjectDeletePolicy;
import org.promasi.coredesigner.editpolicies.EditorEditLayoutPolicy;
import org.promasi.coredesigner.editpolicies.SdDirectEditPolicy;
import org.promasi.coredesigner.figure.SdLookupFigure;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdObject;
/**
 * Represents the controller between the SdLookup model and the SdLookupFigure
 * 
 * @author antoxron
 *
 */
public class SdLookupEditPart extends AbstractEditPart {

	@Override
	protected IFigure createFigure() {
		IFigure figure = new SdLookupFigure(); 
		return figure;
	}
	@Override
	protected void createEditPolicies( ) {
		installEditPolicy( EditPolicy.LAYOUT_ROLE, new EditorEditLayoutPolicy() );
		installEditPolicy( EditPolicy.COMPONENT_ROLE, new SdObjectDeletePolicy() );
		installEditPolicy( EditPolicy.DIRECT_EDIT_ROLE, new SdDirectEditPolicy() );
	}
	@Override 
	public void propertyChange( PropertyChangeEvent event ) { 
		if ( event.getPropertyName().equals( SdObject.PROPERTY_LAYOUT ) ) {
			refreshVisuals(); 
		}
		if ( event.getPropertyName().equals( SdObject.PROPERTY_RENAME ) ) {
			refreshVisuals();
		}
	}
	@Override
	protected void refreshVisuals( ) { 
		SdLookupFigure figure = ( SdLookupFigure )getFigure(); 
		SdLookup model = ( SdLookup )getModel(); 
		figure.setName( model.getName() ); 
		figure.setLayout( model.getLayout() ); 
	} 
	@Override
	public List<SdObject> getModelChildren( ) { 
		return new ArrayList<SdObject>(); 
	}
}