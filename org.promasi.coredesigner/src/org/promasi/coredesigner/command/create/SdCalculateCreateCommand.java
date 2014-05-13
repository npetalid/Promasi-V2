package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdModel;
/**
 * Represents the command to create SdCalculate objects
 * 
 * @author antoxron
 *
 */
public class SdCalculateCreateCommand extends Command {

	/**
	 * 
	 */
	private SdModel _sdModel;
	/**
	 * 
	 */
	private SdCalculate _sdCalculate;
	
	public SdCalculateCreateCommand( ) {
		super();
		_sdModel = null;
		_sdCalculate = null;
	}
	/**
	 * 
	 * @param sdCalculate
	 */
	public void setSdCalculate( Object sdCalculate ) {
		if ( sdCalculate instanceof SdCalculate ) {
			this._sdCalculate = ( SdCalculate ) sdCalculate;
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
		if ( _sdCalculate == null ) {
			return;
		}
		_sdCalculate.setLayout( layout );
	}
	/**
	 * 
	 */
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null) || ( _sdCalculate == null ) ) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdCalculate );
	}
	/**
	 * 
	 */
	@Override
	public boolean canUndo( ) {
		if ( ( _sdModel == null ) || ( _sdCalculate == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdCalculate );
	}
	/**
	 * 
	 */
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdCalculate );
	}	
}