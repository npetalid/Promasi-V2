package org.promasi.coredesigner;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class handle the main layout
 * @author antoxron
 *
 */
public class Perspective implements IPerspectiveFactory {

	/**
	 * 
	 */
	public static final String ID = "org.promasi.coredesigner.perspective";

	public Perspective() {
		super();
	}

	public void createInitialLayout(IPageLayout factory) {
		
		String editorArea = factory.getEditorArea(); 
		factory.setEditorAreaVisible(true);
		factory.addStandaloneView(IPageLayout.ID_OUTLINE, true, IPageLayout.RIGHT, 0.75f, editorArea);
	}
}
