package org.promasi.coredesigner.dnd;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;

import org.promasi.coredesigner.ui.editor.SdObjectCreationFactory;
/**
 * 
 * @author antoxron
 *
 */
public class SdTemplateTransferDropTargetListener extends TemplateTransferDropTargetListener {

	public SdTemplateTransferDropTargetListener( EditPartViewer viewer ) {
		super( viewer );
	}
	@Override
	protected CreationFactory getFactory( Object template ) {
		return new SdObjectCreationFactory( ( Class<?> )template );
	}
}