package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdModel;
/**
 * Represents the command to create SdInput objects
 * 
 * @author antoxron
 *
 */
public class SdInputCreateCommand extends Command {

	private SdModel _sdModel;
	private SdInput _sdInput;
	
	public SdInputCreateCommand( ) {
		super();
		_sdModel = null;
		_sdInput = null;
	}
	public void setSdInput( Object sdInput ) {
		if ( sdInput instanceof SdInput ) {
			this._sdInput = ( SdInput ) sdInput;
		}
	}
	public void setSdModel( Object sdModel ) {
		if ( sdModel instanceof SdModel ) {
			this._sdModel = ( SdModel ) sdModel;
		}
	}
	public void setLayout( Rectangle layout ) {
		if ( _sdInput == null ) {
			return;
		}
		_sdInput.setLayout( layout );
	}
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null ) || ( _sdInput == null ) ) {
			return false;
		}
		return true;
	}
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdInput );
	}
	
	@Override
	public boolean canUndo( ) {
		if ( ( _sdModel == null ) || ( _sdInput == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdInput );
	}
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdInput );
	}	
}