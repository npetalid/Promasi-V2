package org.promasi.coredesigner.command;

import java.util.List;

import org.eclipse.gef.commands.Command;

import org.promasi.coredesigner.model.AbstractSdObject;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdObject;
import org.promasi.coredesigner.model.refactor.RenameUtils;
import org.promasi.coredesigner.resources.ModelManager;
/**
 * Represents the command to rename sdObjects
 * 
 * @author antoxron
 *
 */
public class RenameSdObjectCommand extends Command {
	/**
	 * 
	 */
	private String _oldName;
	/**
	 * 
	 */
	private String _newName;
	/**
	 * 
	 */
	private SdObject _sdModel;
	
	public void execute() {
		_oldName = _sdModel.getName();
		_sdModel.setName( _newName );
		
		if ( _sdModel instanceof SdModel ) {
			
			List<SdModel> sdModels = ModelManager.getInstance().getSdModels();
			RenameUtils renameUtils = new RenameUtils();
			sdModels = renameUtils.renameSdModel( _oldName, _newName, sdModels );
			if ( sdModels != null ) {
				ModelManager.getInstance().setSdModels( sdModels );
			}
		}
		else {			
			if ( _sdModel.getType().equals( AbstractSdObject.OUTPUT_OBJECT ) ) {
				List<SdModel> sdModels = ModelManager.getInstance().getSdModels();
				RenameUtils renameUtils = new RenameUtils();
				sdModels = renameUtils.renameSdObject( _oldName, _newName, _sdModel.getParent().getName(), sdModels );
				if ( sdModels != null ) {
					ModelManager.getInstance().setSdModels( sdModels );
				}
			}
		}
	}
	/**
	 * 
	 * @param model
	 */
	public void setModel( Object model ) {
		_sdModel = ( SdObject ) model;
	}
	/**
	 * 
	 * @param text
	 */
	public void setText( String text ) {
		_newName = text;
	}
	/**
	 * 
	 */
	@Override
	public void undo() {
		_sdModel.setName( _oldName );
	}

}
