package org.promasi.coredesigner.command.connection;


import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdConnection;
/**
 * Represents the command to create Bendpoint
 * 
 * @author antoxron
 *
 */
public class CreateBendpointCommand extends Command {
	
	/**
	 * 
	 */
	private SdConnection _connection;
	/**
	 * 
	 */
	private Point _location; 
	/**
	 * 
	 */
	private int _index; 

	@Override
	public void execute( ) {
		_connection.addBendpoint( _index, _location );
	}
	/**
	 * 
	 * @param model
	 */
	public void setConnection( Object model ) {
		_connection = ( SdConnection ) model;
	}
	/**
	 * 
	 * @param index
	 */
	public void setIndex( int index ) {
		_index = index;
	}
	/**
	 * 
	 * @param point
	 */
	public void setLocation( Point point ) {
		_location = point;
	}
	@Override
	public void undo( ) {
		_connection.removeBendpoint( _index );
	}
}
