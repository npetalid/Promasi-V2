package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdOutput;
/**
 * Represents the command to create SdOutput objects
 * 
 * @author antoxron
 *
 */
public class SdOutputCreateCommand extends Command {

	/**
	 * 
	 */
	private SdModel _sdModel;
	/**
	 * 
	 */
	private SdOutput _sdOutput;
	
	public SdOutputCreateCommand( ) {
		super();
		_sdModel = null;
		_sdOutput = null;
	}
	/**
	 * 
	 * @param sdOutput
	 */
	public void setSdOutput( Object sdOutput ) {
		if ( sdOutput instanceof SdOutput ) {
			this._sdOutput = ( SdOutput ) sdOutput;
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
		if ( _sdOutput == null ) {
			return;
		}
		_sdOutput.setLayout( layout );
	}
	/**
	 * 
	 */
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null ) || ( _sdOutput == null ) ) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdOutput );
	}
	/**
	 * 
	 */
	@Override
	public boolean canUndo( ) {
		if ( ( _sdModel == null ) || ( _sdOutput == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdOutput );
	} 
	/**
	 * 
	 */
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdOutput );
	}
}