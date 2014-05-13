package org.promasi.coredesigner.command.connection;


import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdConnection;

/**
 * Represents the command to move a BendPoint
 * 
 * @author antoxron
 *
 */
public class MoveBendpointCommand extends Command {
	
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
	private Point _newLocation;
	/**
	 * 
	 */
	private int _index;
	
	@Override
	public void execute( ) {
		_oldLocation = ( Point ) _connection.getBendpoints().get( _index );
		_connection.replaceBendpoint( _index, _newLocation );
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
	/**
	 * 
	 * @param point
	 */
	public void setNewLocation( Point point ) {
		_newLocation = point;
	}
	@Override
	public void undo( ) {
		_connection.replaceBendpoint( _index, _oldLocation );
	}

}
