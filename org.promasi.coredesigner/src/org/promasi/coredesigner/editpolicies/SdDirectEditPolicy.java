package org.promasi.coredesigner.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import org.promasi.coredesigner.command.RenameSdObjectCommand;
/**
 * This class handles requests to rename SdObjects
 * 
 * @author antoxron
 *
 */
public class SdDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand( DirectEditRequest directEditRequest ) {
		RenameSdObjectCommand command = new RenameSdObjectCommand();
		command.setModel( getHost().getModel() );
		command.setText( ( String ) directEditRequest.getCellEditor().getValue() );
		return command;
	}

	@Override
	protected void showCurrentEditValue( DirectEditRequest directEditRequest ) {
		
	}
	

}
