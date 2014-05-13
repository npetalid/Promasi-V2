package org.promasi.coredesigner.editpart;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdConnection;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.SdStock;
import org.promasi.coredesigner.model.SdVariable;
/**
 * This class is used to create the controllers
 * 
 * @author antoxron
 *
 */
public class SdEditPartFactory implements EditPartFactory {

	
	@Override 
	public EditPart createEditPart( EditPart context, Object model ) { 
		AbstractGraphicalEditPart part = null; 
		
		if ( model instanceof SdModel ) { 
			part = new SdModelEditPart(); 
		} 
		else if ( model instanceof SdFlow ) { 
			part = new SdFlowEditPart(); 
		} 
		else if ( model instanceof SdStock ) { 
			part = new SdStockEditPart(); 
		} 
		else if ( model instanceof SdInput ) { 
			part = new SdInputEditPart(); 
		} 
		else if ( model instanceof SdOutput ) { 
			part = new SdOutputEditPart(); 
		} 
		else if ( model instanceof SdVariable ) { 
			part = new SdVariableEditPart(); 
		} 
		else if ( model instanceof SdLookup ) { 
			part = new SdLookupEditPart(); 
		} 
		else if ( model instanceof SdCalculate ) { 
			part = new SdCalculateEditPart(); 
		} 
		else if ( model instanceof SdConnection ) {
			part = new SdConnectionPart();
		}
		part.setModel( model ); 
		return part; 
	}
	
	
	
}
