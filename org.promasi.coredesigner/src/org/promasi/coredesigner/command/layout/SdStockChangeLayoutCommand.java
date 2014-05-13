package org.promasi.coredesigner.command.layout;

import org.eclipse.draw2d.geometry.Rectangle;

import org.promasi.coredesigner.model.SdStock;
/**
 * Represents the command to change the layout of SdStock
 * 
 * @author antoxron
 *
 */
public class SdStockChangeLayoutCommand extends AbstractLayoutCommand {

	/**
	 * 
	 */
	private SdStock _model;
	/**
	 * 
	 */
	private Rectangle _layout;
	/**
	 * 
	 */
	private Rectangle _oldLayout;

	@Override
	public void execute( ) { 
		_model.setLayout( _layout ); 
	}
	@Override
	public void setConstraint( Rectangle rectangle ) {
		_layout = rectangle;
	}
	@Override
	public void setModel( Object model ) {
		_model = ( SdStock )model;
		_oldLayout = ( ( SdStock )model ).getLayout();
	}
	@Override
	public void undo( ) { 
		_model.setLayout( _oldLayout ); 
	}
}