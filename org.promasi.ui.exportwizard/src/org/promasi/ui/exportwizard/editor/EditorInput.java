package org.promasi.ui.exportwizard.editor;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
/**
 * 
 * @author antoxron
 *
 */
public class EditorInput implements IEditorInput {
	private String participant;

	public EditorInput(String participant) {
		super();
		Assert.isNotNull(participant);
		this.participant = participant;
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return participant;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		if (!(obj instanceof EditorInput))
			return false;
		EditorInput other = (EditorInput) obj;
		return this.participant.equals(other.participant);
	}

	public int hashCode() {
		return participant.hashCode();
	}

	public String getToolTipText() {
		return participant;
	}

	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}
}
