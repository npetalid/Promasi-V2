package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdVariable;
/**
 * Represents the command to create SdVariable objects
 * 
 * @author antoxron
 *
 */
public class SdVariableCreateCommand extends Command {

	/**
	 * 
	 */
	private SdModel _sdModel;
	/**
	 * 
	 */
	private SdVariable _sdVariable;
	
	public SdVariableCreateCommand( ) {
		super();
		_sdModel = null;
		_sdVariable = null;
	}
	/**
	 * 
	 * @param sdVariable
	 */
	public void setSdVariable( Object sdVariable ) {
		if ( sdVariable instanceof SdVariable ) {
			this._sdVariable = ( SdVariable ) sdVariable;
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
		if ( _sdVariable == null ) {
			return;
		}
		_sdVariable.setLayout( layout );
	}
	/**
	 * 
	 */
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null ) || ( _sdVariable == null ) ) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdVariable );
	}
	/**
	 * 
	 */
	@Override
	public boolean canUndo() {
		if ( ( _sdModel == null ) || ( _sdVariable == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdVariable );
	}
	/**
	 * 
	 */
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdVariable );
	}
}