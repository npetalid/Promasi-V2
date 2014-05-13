package org.promasi.coredesigner.editpolicies;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import org.promasi.coredesigner.command.connection.CreateBendpointCommand;
import org.promasi.coredesigner.command.connection.DeleteBendpointCommand;
import org.promasi.coredesigner.command.connection.MoveBendpointCommand;
/**
 * This class is used to manage the Bendpoint connection requests
 * 
 * @author antoxron
 *
 */

public class BendpointConnectionEditPolicy extends BendpointEditPolicy {

	/**
	 * 
	 */
	@Override
	protected Command getCreateBendpointCommand( BendpointRequest request ) {
		Point point = request.getLocation();
		getConnection().translateToRelative( point );
		CreateBendpointCommand command = new CreateBendpointCommand();
		command.setLocation( point );
		command.setConnection( getHost().getModel() );
		command.setIndex( request.getIndex() );
		return command;
	}
	/**
	 * 
	 */
	@Override
	protected Command getDeleteBendpointCommand( BendpointRequest request ) {
		DeleteBendpointCommand command = new DeleteBendpointCommand();
		command.setConnectionModel( getHost().getModel() );
		command.setIndex( request.getIndex() );
		return command;
	}
	/**
	 * 
	 */
	@Override
	protected Command getMoveBendpointCommand( BendpointRequest request ) {
		Point location = request.getLocation();
		getConnection().translateToRelative( location );
		MoveBendpointCommand command = new MoveBendpointCommand();
		command.setConnectionModel( getHost().getModel() );
		command.setIndex( request.getIndex() );
		command.setNewLocation( location );
		return command;
	}
}