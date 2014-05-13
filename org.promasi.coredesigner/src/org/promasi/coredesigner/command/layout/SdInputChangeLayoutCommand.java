package org.promasi.coredesigner.command.layout;

import org.eclipse.draw2d.geometry.Rectangle;

import org.promasi.coredesigner.model.SdInput;
/**
 * Represents the command to change the layout of SdInput
 * 
 * @author antoxron
 *
 */
public class SdInputChangeLayoutCommand extends AbstractLayoutCommand {
	
	/**
	 * 
	 */
	private SdInput _model;
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
		_model = ( SdInput  )model;
		_oldLayout = ( ( SdInput )model ).getLayout();
	}
	@Override
	public void undo( ) { 
		_model.setLayout( _oldLayout ); 
	}
}