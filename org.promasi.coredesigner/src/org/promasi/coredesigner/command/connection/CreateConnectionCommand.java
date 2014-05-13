package org.promasi.coredesigner.command.connection;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.SdConnection;
import org.promasi.coredesigner.model.SdObject;
/**
 * Represents the command to create SdConnections
 * 
 * @author antoxron
 *
 */
public class CreateConnectionCommand extends Command  {
	
	/**
	 * 
	 */
	private SdObject _sourceObject;
	/**
	 * 
	 */
	private SdObject _targetObject;
	/**
	 * 
	 */
	private SdConnection _connection;
	
	/**
	 * 
	 * @param sourceObject
	 */
	public void setSourceObject( SdObject sourceObject ) {
		_sourceObject = sourceObject;
	}
	/**
	 * 
	 * @param targetObject
	 */
	public void setTargetObject( SdObject targetObject ) {
		_targetObject = targetObject;
	}
	@Override
	public boolean canExecute( ) {
		
		boolean canExecute = false;
		
		if ( ( _sourceObject == null ) || ( _targetObject == null ) ) {
			canExecute =  false;
		}
		else if ( _sourceObject.equals( _targetObject ) ) {
			canExecute =  false;
		}
		else {
			canExecute = true;
		}
		return canExecute;
	}
	@Override
	public void execute( ) {
		_connection = new SdConnection( _sourceObject, _targetObject );
		_connection.getSourceObject().addConnection( _connection );
		_connection.getTargetObject().addConnection( _connection );
		
	}
	@Override
	public boolean canUndo( ) {
		
		boolean canUndo = false;
		if ( ( _sourceObject == null ) || ( _targetObject == null ) ) {
			canUndo =  false;
		}
		else {
			canUndo = true;
		}
		return canUndo;
	}
	@Override
	public void undo( ) {
		_connection.getSourceObject().removeConnection( _connection );
		_connection.getTargetObject().removeConnection( _connection );
	}
}