package org.promasi.coredesigner.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import org.promasi.coredesigner.command.connection.DeleteConnectionCommand;
/**
 * This class handles requests for deletion connections
 * 
 * @author antoxron
 *
 */
public class SdConnectionDeleteEditPolicy extends ConnectionEditPolicy {

	@Override
	protected Command getDeleteCommand( GroupRequest request ) {
		DeleteConnectionCommand command = new DeleteConnectionCommand();
		command.setLink( getHost().getModel() );
		return command;
	}

}
