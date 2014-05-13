package org.promasi.coredesigner.editpart;


import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;

import org.eclipse.swt.widgets.Text;

import org.promasi.coredesigner.model.SdObject;
/**
 * This class represents a controller and is used to rename sdObjects
 * 
 * @author antoxron
 *
 */
public class SdRenameEditPart extends DirectEditManager {

	/**
	 * the object will be renamed
	 */
	private SdObject _sdModel;

	@SuppressWarnings("rawtypes")
	public SdRenameEditPart( GraphicalEditPart source,  Class editorType, CellEditorLocator locator ) {
		super( source, editorType, locator );
		_sdModel = ( SdObject ) source.getModel();
	}
	@Override
	protected void initCellEditor( ) {
		getCellEditor().setValue( _sdModel.getName() );
		Text text  = ( Text ) getCellEditor().getControl();
		text.selectAll();
	}
}