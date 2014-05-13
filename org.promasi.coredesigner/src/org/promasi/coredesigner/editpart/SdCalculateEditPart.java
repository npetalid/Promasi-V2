package org.promasi.coredesigner.editpart;

import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import org.promasi.coredesigner.editpolicies.SdObjectDeletePolicy;
import org.promasi.coredesigner.editpolicies.EditorEditLayoutPolicy;
import org.promasi.coredesigner.editpolicies.SdDirectEditPolicy;
import org.promasi.coredesigner.figure.SdCalculateFigure;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdObject;
/**
 * Represents the controller between the SdCalculate model and the SdCalculateFigure
 * 
 * @author antoxron
 *
 */
public class SdCalculateEditPart extends AbstractEditPart {

	@Override
	protected IFigure createFigure( ) {
		IFigure figure = new SdCalculateFigure(); 
		return figure;
	}
	@Override
	protected void createEditPolicies( ) {
		installEditPolicy( EditPolicy.LAYOUT_ROLE, new EditorEditLayoutPolicy() );
		installEditPolicy( EditPolicy.COMPONENT_ROLE,new SdObjectDeletePolicy() );
		installEditPolicy( EditPolicy.DIRECT_EDIT_ROLE, new SdDirectEditPolicy() );
	}
	@Override
	protected void refreshVisuals( ) { 
		SdCalculateFigure figure = ( SdCalculateFigure )getFigure(); 
		SdCalculate model = ( SdCalculate )getModel(); 
		figure.setName( model.getName() ); 
		figure.setLayout( model.getLayout() ); 
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
	public List<SdObject> getModelChildren( ) { 
		return new ArrayList<SdObject>(); 
	}
}