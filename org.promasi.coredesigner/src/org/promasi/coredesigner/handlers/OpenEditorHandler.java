package org.promasi.coredesigner.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import org.promasi.coredesigner.Activator;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.model.builder.model.XmlProject;
import org.promasi.coredesigner.model.parser.ModelParser;
import org.promasi.coredesigner.model.parser.VisualXmlParser;
import org.promasi.coredesigner.resources.ModelManager;
import org.promasi.coredesigner.ui.editor.EditorInput;
import org.promasi.coredesigner.ui.editor.SdGraphicalEditor;

import org.xml.sax.SAXException;
/**
 * 
 * @author antoxron
 *
 */
public class OpenEditorHandler extends AbstractHandler {

	@Override
	public Object execute( ExecutionEvent event ) throws ExecutionException {
	
			
		
		FileDialog dialog = new FileDialog( PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				SWT.MULTI | SWT.OPEN );
		dialog.setFilterNames(new String[] { ""+ "pms" +" files(*."+"pms"+")" } );
		dialog.setFilterExtensions(new String[] { "*."+"pms"+"" } );
        String results = dialog.open();
        if ( results != null )  {
        	String path = dialog.getFilterPath();
        	String filename = dialog.getFileName();
   
        	
        	String fullPath = path + File.separatorChar + filename;
        	
        	XmlProject xmlProject = null;
        	VisualXmlParser parser = new VisualXmlParser( fullPath );
        	try {
				xmlProject = parser.parse();
			} 
        	catch ( IOException e2 ) {
				
				e2.printStackTrace();
			} 
        	catch ( SAXException e2 ) {
			
				e2.printStackTrace();
			}        	
        	
        	
    		if ( xmlProject != null ) {
    			ModelParser modelParser = new ModelParser();
    			modelParser.setProject( xmlProject );
    			SdProject sdProject = modelParser.getProject();
    			if ( sdProject != null ) {
    				ModelManager.getInstance().setSdProject( sdProject );
    				
    				
    				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    				
    				
    				try {
    					page.showView( IPageLayout.ID_OUTLINE );
    				}
    				catch ( PartInitException e1 ) {
    					e1.printStackTrace();
    				}

    				try {
    					
    					IEditorPart activeEditor = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
    					if ( activeEditor != null ) {
    						Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors( false );
    					}
    			
    					Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor( new EditorInput( "Core Designer" ), SdGraphicalEditor.ID , false );
    				} 
    				catch ( PartInitException e ) {
    					e.printStackTrace();
    				}			
    			}
    		}
    		else {
				MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
						"Error", "The format of the file is not correct.");
    		}

        	
        }
		return null;
		        
	}
}
