package org.promasi.coredesigner.editpart;

import java.beans.PropertyChangeListener;

import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import org.eclipse.jface.viewers.TextCellEditor;

import org.promasi.coredesigner.model.SdObject; 
/**
 * Represents the controller between the model and the figure
 * 
 * @author antoxron
 *
 */
public abstract class AbstractEditPart extends AbstractGraphicalEditPart
	implements PropertyChangeListener { 
	
	/**
	 * 
	 */
	private SdRenameEditPart _directManager = null;

	@Override
	public void activate( ) {
		super.activate(); 
		( ( SdObject ) getModel() ).addPropertyChangeListener( this ); 
	} 
	@Override
	public void deactivate() { 
		super.deactivate(); 
		( ( SdObject ) getModel() ).removePropertyChangeListener( this ); 
	}
	@Override
	public void performRequest( Request request ) {
			
		if ( request.getType().equals( RequestConstants.REQ_DIRECT_EDIT ) ) { 
				
			if ( _directManager == null ) {
					
				_directManager = new SdRenameEditPart( this , TextCellEditor.class , new SdCellEditorLocator( getFigure() ) );
			}
			_directManager.show( ); 
			return;
		}
		super.performRequest( request );
	}
}