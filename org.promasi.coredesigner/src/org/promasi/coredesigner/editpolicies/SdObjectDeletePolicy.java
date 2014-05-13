package org.promasi.coredesigner.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import org.promasi.coredesigner.command.DeleteSdObjectCommand;
/**
 * This class handles the requests to delete SdObjects
 * 
 * @author antoxron
 *
 */
public class SdObjectDeletePolicy extends ComponentEditPolicy {
	
	@Override
	protected Command createDeleteCommand( GroupRequest deleteRequest ) {
		DeleteSdObjectCommand command = new DeleteSdObjectCommand(); 
		command.setModel( getHost().getModel() ); 
		command.setParentModel( getHost().getParent().getModel() ); 
		return command;
	}
}
