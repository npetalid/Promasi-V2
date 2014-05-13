package org.promasi.coredesigner.command.layout;

import org.eclipse.draw2d.geometry.Rectangle;

import org.promasi.coredesigner.model.SdLookup;
/**
 * Represents the command to change the layout of SdLookup
 * 
 * @author antoxron
 *
 */
public class SdLookupChangeLayoutCommand extends AbstractLayoutCommand {

	/**
	 * 
	 */
	private SdLookup _model;
	/**
	 * 
	 */
	private Rectangle _layout;
	/**
	 * 
	 */
	private Rectangle _oldLayout;
	
	public void execute( ) { 
		_model.setLayout( _layout ); 
	}
	@Override
	public void setConstraint( Rectangle rectangle ) {
		_layout = rectangle;
	}
	@Override
	public void setModel( Object model ) {
		_model = ( SdLookup )model;
		_oldLayout = ( ( SdLookup )model ).getLayout();
	}
	@Override
	public void undo( ) { 
		_model.setLayout( _oldLayout ); 
	}
}