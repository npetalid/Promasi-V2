package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdModel;
/**
 * Represents the command to create SdLookup objects
 * 
 * @author antoxron
 *
 */
public class SdLookupCreateCommand extends Command {

	/**
	 * 
	 */
	private SdModel _sdModel;
	/**
	 * 
	 */
	private SdLookup _sdLookup;
	
	public SdLookupCreateCommand( ) {
		super();
		_sdModel = null;
		_sdLookup = null;
	}
	/**
	 * 
	 * @param sdLookup
	 */
	public void setSdLookup( Object sdLookup ) {
		if ( sdLookup instanceof SdLookup ) {
			this._sdLookup = ( SdLookup ) sdLookup;
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
		if ( _sdLookup == null ) {
			return;
		}
		_sdLookup.setLayout( layout );
	}
	/**
	 * 
	 */
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null ) || ( _sdLookup == null ) ) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdLookup );
	}
	/**
	 * 
	 */
	@Override
	public boolean canUndo( ) {
		if ( ( _sdModel == null ) || ( _sdLookup == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdLookup );
	}
	/**
	 * 
	 */
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdLookup );
	}
}