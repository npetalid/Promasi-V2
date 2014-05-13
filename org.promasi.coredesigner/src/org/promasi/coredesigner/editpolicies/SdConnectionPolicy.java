package org.promasi.coredesigner.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.promasi.coredesigner.command.connection.CreateConnectionCommand;
import org.promasi.coredesigner.model.SdObject;
/**
 * This class handles the connection requests
 * 
 * @author antoxron
 *
 */

public class SdConnectionPolicy extends GraphicalNodeEditPolicy  {

	@Override
	protected Command getConnectionCompleteCommand( CreateConnectionRequest request ) {
		CreateConnectionCommand command =  ( CreateConnectionCommand ) request.getStartCommand();
		SdObject targetObject = ( SdObject ) getHost().getModel();
		command.setTargetObject( targetObject );
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand( CreateConnectionRequest request ) {
		CreateConnectionCommand command = new CreateConnectionCommand();
		SdObject sourceObject = ( SdObject ) getHost().getModel();
		command.setSourceObject( sourceObject );
		request.setStartCommand( command );
		return command;
	}

	@Override
	protected Command getReconnectSourceCommand( ReconnectRequest request ) {
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand( ReconnectRequest request ) {
		return null;
	}
}