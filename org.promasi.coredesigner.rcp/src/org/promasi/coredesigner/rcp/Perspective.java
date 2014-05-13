package org.promasi.coredesigner.rcp;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea(); 
		layout.setEditorAreaVisible(true);
		layout.addStandaloneView(IPageLayout.ID_OUTLINE, true, IPageLayout.RIGHT, 0.75f, editorArea);
	}
}
