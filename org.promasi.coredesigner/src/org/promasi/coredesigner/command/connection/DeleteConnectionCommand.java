package org.promasi.coredesigner.command.connection;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdConnection;
/**
 * Represents the command to delete a Connection
 * 
 * @author antoxron
 *
 */
public class DeleteConnectionCommand extends Command {
	
	/**
	 * 
	 */
	private SdConnection _connection;
	
	/**
	 * 
	 * @param model
	 */
	public void setLink( Object model ) {
		if ( model instanceof SdConnection ) {
			
			_connection = ( SdConnection )model;
		}
	}
	
	@Override
	public boolean canExecute( ) {
		
		boolean canExecute = false;
		
		if ( _connection == null ) {
			canExecute =  false;
		}
		else {
			canExecute = true;
		}
		return canExecute;
	}
	@Override
	public void execute( ) {
		_connection.getSourceObject().removeConnection( _connection );
		_connection.getTargetObject().removeConnection( _connection );	
	}
	@Override
	public boolean canUndo() {
		
		boolean canUndo = false;
		
		if ( _connection == null ) {
			canUndo =  false;
		}
		else {
			canUndo = true;
		}
		return canUndo;
	}
	@Override
	public void undo( ){
		
		_connection.getSourceObject().addConnection( _connection );
		_connection.getTargetObject().addConnection( _connection );	
		
	}
}