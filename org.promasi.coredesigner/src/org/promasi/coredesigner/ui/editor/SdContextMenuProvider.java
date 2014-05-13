package org.promasi.coredesigner.ui.editor;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;

import org.eclipse.ui.actions.ActionFactory;

import org.promasi.coredesigner.actions.SdObjectPropertiesAction;
/**
 * 
 * @author antoxron
 *
 */
public class SdContextMenuProvider extends ContextMenuProvider {

	
	private ActionRegistry actionRegistry;
	
	public SdContextMenuProvider( EditPartViewer viewer, ActionRegistry registry ) {
		super(viewer); 
		setActionRegistry( registry );
	}
	
	@Override 
	public void buildContextMenu( IMenuManager menu ) { 
		IAction action; 
		GEFActionConstants.addStandardActionGroups( menu ); 
		action = getActionRegistry().getAction( ActionFactory.UNDO.getId() ); 
		menu.appendToGroup( GEFActionConstants.GROUP_UNDO, action ); 
		action = getActionRegistry().getAction( ActionFactory.REDO.getId() ); 
		menu.appendToGroup( GEFActionConstants.GROUP_UNDO, action ); 
		action = getActionRegistry().getAction( ActionFactory.DELETE.getId() ); 
		menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action ); 
		action = actionRegistry.getAction( SdObjectPropertiesAction.ID );
		if (action.isEnabled()) {
			menu.appendToGroup( GEFActionConstants.GROUP_EDIT, action );
		}
	}
	
	
	private ActionRegistry getActionRegistry( ) { 
		return actionRegistry; 
	}
	private void setActionRegistry( ActionRegistry registry ) { 
		actionRegistry = registry; 
	}
}