package org.promasi.coredesigner.command.create;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdStock;
/**
 * Represents the command to create SdStock objects
 * 
 * @author antoxron
 *
 */
public class SdStockCreateCommand extends Command {

	/**
	 * 
	 */
	private SdModel _sdModel;
	/**
	 * 
	 */
	private SdStock _sdStock;
	
	public SdStockCreateCommand( ) {
		super();
		_sdModel = null;
		_sdStock = null;
	}
	/**
	 * 
	 * @param sdStock
	 */
	public void setSdStock( Object sdStock ) {
		if ( sdStock instanceof SdStock ) {
			this._sdStock = ( SdStock ) sdStock;
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
		if ( _sdStock == null ) {
			return;
		}
		_sdStock.setLayout( layout );
	}
	/**
	 * 
	 */
	@Override
	public boolean canExecute( ) {
		if ( ( _sdModel == null ) || ( _sdStock == null ) ) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public void execute( ) {
		_sdModel.addChild( _sdStock );
	}
	/**
	 * 
	 */
	@Override
	public boolean canUndo() {
		if ( ( _sdModel == null ) || ( _sdStock == null ) ) {
			return false;
		}
		return _sdModel.contains( _sdStock );
	}
	/**
	 * 
	 */
	@Override
	public void undo( ) {
		_sdModel.removeChild( _sdStock );
	}
}