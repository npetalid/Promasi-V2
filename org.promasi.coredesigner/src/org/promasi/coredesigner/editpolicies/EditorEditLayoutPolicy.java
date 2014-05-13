package org.promasi.coredesigner.editpolicies;

import org.eclipse.draw2d.geometry.Rectangle;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import org.promasi.coredesigner.command.create.SdCalculateCreateCommand;
import org.promasi.coredesigner.command.create.SdFlowCreateCommand;
import org.promasi.coredesigner.command.create.SdInputCreateCommand;
import org.promasi.coredesigner.command.create.SdLookupCreateCommand;
import org.promasi.coredesigner.command.create.SdOutputCreateCommand;
import org.promasi.coredesigner.command.create.SdStockCreateCommand;
import org.promasi.coredesigner.command.create.SdVariableCreateCommand;
import org.promasi.coredesigner.command.layout.AbstractLayoutCommand;
import org.promasi.coredesigner.command.layout.SdCalculateChangeLayoutCommand;
import org.promasi.coredesigner.command.layout.SdFlowChangeLayoutCommand;
import org.promasi.coredesigner.command.layout.SdInputChangeLayoutCommand;
import org.promasi.coredesigner.command.layout.SdLookupChangeLayoutCommand;
import org.promasi.coredesigner.command.layout.SdOutputChangeLayoutCommand;
import org.promasi.coredesigner.command.layout.SdStockChangeLayoutCommand;
import org.promasi.coredesigner.command.layout.SdVariableChangeLayoutCommand;
import org.promasi.coredesigner.editpart.SdCalculateEditPart;
import org.promasi.coredesigner.editpart.SdFlowEditPart;
import org.promasi.coredesigner.editpart.SdInputEditPart;
import org.promasi.coredesigner.editpart.SdLookupEditPart;
import org.promasi.coredesigner.editpart.SdModelEditPart;
import org.promasi.coredesigner.editpart.SdOutputEditPart;
import org.promasi.coredesigner.editpart.SdStockEditPart;
import org.promasi.coredesigner.editpart.SdVariableEditPart;
import org.promasi.coredesigner.model.SdCalculate;
import org.promasi.coredesigner.model.SdFlow;
import org.promasi.coredesigner.model.SdInput;
import org.promasi.coredesigner.model.SdLookup;
import org.promasi.coredesigner.model.SdOutput;
import org.promasi.coredesigner.model.SdStock;
import org.promasi.coredesigner.model.SdVariable;
/**
 * This class is used to manage requests layouts
 * 
 * @author antoxron
 *
 */
public class EditorEditLayoutPolicy extends XYLayoutEditPolicy {

	/**
	 * 
	 */
	@Override
	protected Command createChangeConstraintCommand( EditPart child,
			Object constraint ) {
		
		AbstractLayoutCommand command = null; 
		
		if ( child instanceof SdFlowEditPart ) { 
			command = new SdFlowChangeLayoutCommand(); 
		} 
		else if ( child instanceof SdStockEditPart ) { 
			command = new SdStockChangeLayoutCommand(); 
		} 
		else if ( child instanceof SdInputEditPart ) { 
			command = new SdInputChangeLayoutCommand(); 
		} 
		else if ( child instanceof SdOutputEditPart ) { 
			command = new SdOutputChangeLayoutCommand(); 
		} 
		else if ( child instanceof SdVariableEditPart ) { 
			command = new SdVariableChangeLayoutCommand(); 
		} 
		else if ( child instanceof SdLookupEditPart ) { 
			command = new SdLookupChangeLayoutCommand(); 
		} 
		else if ( child instanceof SdCalculateEditPart ) { 
			command = new SdCalculateChangeLayoutCommand(); 
		} 
		command.setModel( child.getModel() ); 
		command.setConstraint( (  Rectangle )constraint ); 
		return command;
	}
	/**
	 * 
	 */
	@Override
	protected Command getCreateCommand( CreateRequest request ) {
		
		
		if ( request.getType() == REQ_CREATE && getHost() instanceof SdModelEditPart ) {
			Object object = request.getNewObject();

			Command command = null;
			
			
			if ( object instanceof SdFlow )  {
				
				SdFlowCreateCommand createFlowCommand = new SdFlowCreateCommand();
		
				createFlowCommand.setSdModel( getHost().getModel() );
				createFlowCommand.setSdFlow( request.getNewObject() );
				
				Rectangle constraint = ( Rectangle ) getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 60 : constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 65 : constraint.height;
				createFlowCommand.setLayout( constraint );
				
				command = ( SdFlowCreateCommand )createFlowCommand;
			}
			if ( object instanceof SdStock ) {
				SdStockCreateCommand createStockCommand = new SdStockCreateCommand();
				createStockCommand.setSdModel( getHost().getModel() );
				createStockCommand.setSdStock( request.getNewObject() );
				Rectangle constraint = ( Rectangle )getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 80 : constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 60 : constraint.height;
				createStockCommand.setLayout( constraint );
				
				command = ( SdStockCreateCommand ) createStockCommand;

			}
			if ( object instanceof SdInput ) {
				SdInputCreateCommand createInputCommand = new SdInputCreateCommand();
				createInputCommand.setSdModel( getHost().getModel() );
				createInputCommand.setSdInput( request.getNewObject() );
				Rectangle constraint = ( Rectangle ) getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 80 : constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 65 : constraint.height;
				createInputCommand.setLayout( constraint );
				
				command = ( SdInputCreateCommand )createInputCommand;

			}
			if ( object instanceof SdOutput ) {
				SdOutputCreateCommand createOutputCommand = new SdOutputCreateCommand();
				createOutputCommand.setSdModel( getHost().getModel() );
				createOutputCommand.setSdOutput( request.getNewObject() );
				Rectangle constraint = ( Rectangle ) getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 80: constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 65 : constraint.height;
				createOutputCommand.setLayout( constraint );
				
				command = ( SdOutputCreateCommand ) createOutputCommand;

			}
			if ( object instanceof SdVariable ) {
				SdVariableCreateCommand createVariableCommand = new SdVariableCreateCommand();
				createVariableCommand.setSdModel( getHost().getModel() );
				createVariableCommand.setSdVariable( request.getNewObject() );
				Rectangle constraint = ( Rectangle ) getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 80: constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 30 : constraint.height;
				createVariableCommand.setLayout( constraint );
				
				command = ( SdVariableCreateCommand ) createVariableCommand;

			}
			
			
			if ( object instanceof SdLookup ) {
				SdLookupCreateCommand createLookupCommand = new SdLookupCreateCommand();
				createLookupCommand.setSdModel( getHost().getModel() );
				createLookupCommand.setSdLookup( request.getNewObject() );
				Rectangle constraint = ( Rectangle )getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 70: constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 65 : constraint.height;
				createLookupCommand.setLayout( constraint );
				
				command = ( SdLookupCreateCommand )createLookupCommand;

			}
			if (object instanceof SdCalculate) {
				SdCalculateCreateCommand createCalculateCommand = new SdCalculateCreateCommand();
				createCalculateCommand.setSdModel( getHost().getModel() );
				createCalculateCommand.setSdCalculate( request.getNewObject() );
				Rectangle constraint = ( Rectangle )getConstraintFor( request );
				constraint.x = ( constraint.x < 0 ) ? 0 : constraint.x;
				constraint.y = ( constraint.y < 0 ) ? 0 : constraint.y;
				constraint.width = ( constraint.width <= 0 ) ? 55: constraint.width;
				constraint.height = ( constraint.height <= 0 ) ? 45 : constraint.height;
				createCalculateCommand.setLayout( constraint );
				
				command = ( SdCalculateCreateCommand )createCalculateCommand;
			}
			return command;
		}
		return null;
	}
}