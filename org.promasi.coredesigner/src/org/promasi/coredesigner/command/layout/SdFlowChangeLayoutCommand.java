package org.promasi.coredesigner.command.layout;

import org.eclipse.draw2d.geometry.Rectangle;

import org.promasi.coredesigner.model.SdFlow;
/**
 * Represents the command to change the layout of SdFlow
 * 
 * @author antoxron
 *
 */
public class SdFlowChangeLayoutCommand extends AbstractLayoutCommand {

	/**
	 * 
	 */
	private SdFlow _model;
	/**
	 * 
	 */
	private Rectangle _layout;
	/**
	 * 
	 */
	private Rectangle _oldLayout;
	
	/**
	 * 
	 */
	public void execute( ) { 
		_model.setLayout( _layout ); 
	}

	@Override
	public void setConstraint( Rectangle rectangle ) {
		_layout = rectangle;
	}
	@Override
	public void setModel( Object model ) {
		_model = ( SdFlow )model;
		_oldLayout = ( ( SdFlow)model ).getLayout();
	}
	@Override
	public void undo( ) { 
		_model.setLayout( _oldLayout ); 
	}
}