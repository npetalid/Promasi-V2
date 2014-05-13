package org.promasi.coredesigner.rcp;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setShowCoolBar(true);
        configurer.setShowStatusLine(false);
    }
    
    @Override
    public void postWindowOpen() {
    	
    	
		final IWorkbenchWindow window = getWindowConfigurer().getWindow();
		
		Rectangle displayBounds = window.getShell().getDisplay().getBounds();
		int nMinWidth = 1024;
	    int nMinHeight = 768;
	    
	    int nLeft = (displayBounds.width - nMinWidth) / 2;
	    int nTop = (displayBounds.height - nMinHeight) / 2;
		window.getShell().setBounds(nLeft, nTop, nMinWidth, nMinHeight);
		
    }
}
