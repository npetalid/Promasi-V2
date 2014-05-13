package org.promasi.coredesigner.command.connection;

import org.eclipse.gef.requests.CreationFactory;
/**
 * This class is used from palette , to create SdConnections
 * 
 * @author antoxron
 *
 */

public class ConnectionCreationFactory implements CreationFactory {
	
	/**
	 * 
	 */
	private Class<?> _clazz;
	
	public ConnectionCreationFactory( Class<?> clazz ) {
		_clazz = clazz;
	}

	@Override
	public Object getNewObject( ) {		
		return null;
	}

	@Override
	public Object getObjectType( ) {
		return _clazz;
	}
}