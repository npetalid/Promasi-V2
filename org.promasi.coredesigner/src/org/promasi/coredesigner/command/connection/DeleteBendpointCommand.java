package org.promasi.coredesigner.command.connection;


import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdConnection;

/**
 * Represents the command to delete Bendpoint
 * 
 * @author antoxron
 *
 */
public class DeleteBendpointCommand extends Command {
	
	/**
	 * 
	 */
	private SdConnection _connection;
	/**
	 * 
	 */
	private Point _oldLocation;
	/**
	 * 
	 */
	private int _index;

	@Override
	public void execute( ) {
		_oldLocation = ( Point ) _connection.getBendpoints().get( _index );
		_connection.removeBendpoint( _index );
	}
	/**
	 * 
	 * @param model
	 */
	public void setConnectionModel( Object model ) {
		_connection = ( SdConnection ) model;
	}
	/**
	 * 
	 * @param index
	 */
	public void setIndex( int index ) {
		_index = index;
	}
	@Override
	public void undo( ) {
		_connection.addBendpoint( _index, _oldLocation );
	}
}
