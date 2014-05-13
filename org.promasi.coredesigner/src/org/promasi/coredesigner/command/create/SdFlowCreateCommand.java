package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdModel;
/**
 * Represents the command to create SdFlow objects
 * 
 * @author antoxron
 *
 */
public class SdFlowCreateCommand extends Command {

	/**
	 * 
	 */
	private SdModel _sdModel;
	/**
	 * 
	 */
	private SdFlow _sdFlow;
	
	public SdFlowCreateCommand( ) {
		super();
		_sdModel = null;
		_sdFlow = null;
	}
	/**
	 * 
	 * @param sdFlow
	 */
	public void setSdFlow( Object sdFlow ) {
		if ( sdFlow instanceof SdFlow ) {
			this._sdFlow = ( SdFlow ) sdFlow;
		}
	}
	/**
	 * 
	 * @param sdModel
	 */
	public void setSdModel( Object sdModel ) {
		if ( sdModel instanceof SdModel ) {
			this._sdModel = ( SdModel ) sdModel;
		}
	}
	/**
	 * 
	 * @param layout
	 */
	public void setLayout( Rectangle layout ) {
		if ( _sdFlow == null ) {
			return;
		}
		_sdFlow.setLayout( layout );
	}
	/**
	 * 
	 */
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null ) || ( _sdFlow == null ) ) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdFlow );
	}
	/**
	 * 
	 */
	@Override
	public boolean canUndo( ) {
		if ( ( _sdModel == null ) || ( _sdFlow == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdFlow );
	}
	/**
	 * 
	 */
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdFlow );
	}
	
	
}
