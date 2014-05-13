package org.promasi.coredesigner.command.layout;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.commands.Command;
/**
 * Represents the command to change the layout of SdObject
 * 
 * @author antoxron
 *
 */
public abstract class AbstractLayoutCommand extends Command {

	public abstract void setConstraint( Rectangle rectangle ); 
	public abstract void setModel( Object model );
}