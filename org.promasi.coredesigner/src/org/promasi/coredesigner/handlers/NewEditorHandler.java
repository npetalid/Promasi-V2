package org.promasi.coredesigner.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import org.promasi.coredesigner.Activator;
import org.promasi.coredesigner.model.NameValidator;
import org.promasi.coredesigner.model.SdModel;
import org.promasi.coredesigner.model.SdProject;
import org.promasi.coredesigner.resources.ModelManager;
import org.promasi.coredesigner.ui.dialogs.CreateProjectDialog;
import org.promasi.coredesigner.ui.editor.EditorInput;
import org.promasi.coredesigner.ui.editor.SdGraphicalEditor;

/**
 * 
 * @author antoxron
 *
 */
public class NewEditorHandler extends AbstractHandler  {

	@Override
	public Object execute( ExecutionEvent event ) throws ExecutionException {
		
		
		
		CreateProjectDialog dialog = new CreateProjectDialog();
		dialog.setBlockOnOpen( true );
		dialog.open();
		
		String projectName = dialog.getProjectName();
		String modelName = dialog.getModelName();
		
		if ( ( projectName != null ) && ( modelName != null ) ) {
			
			if ( ( !projectName.isEmpty() ) && ( !modelName.isEmpty() )  
					&& NameValidator.getInstance().validateString( modelName ) ) {
				SdProject sdProject = new SdProject();
				List<SdModel> sdModels = new ArrayList<SdModel>();
				SdModel defaultModel = new SdModel();
				defaultModel.setProjectName( projectName );
				defaultModel.setName( modelName );
		
				sdModels.add( defaultModel );
				sdProject.setModels( sdModels );
				sdProject.setName( projectName );
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
			
					Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInput( "Core Designer" ), SdGraphicalEditor.ID , false );
				} 
				catch ( PartInitException e ) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
