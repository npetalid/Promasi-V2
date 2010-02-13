package org.promasi.ui.gmtools.coredesigner.actions;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import org.jfree.ui.ExtensionFileFilter;
import org.promasi.ui.gmtools.coredesigner.controllers.Shell;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * 
 * Actions that loads a PSD.
 * 
 * @author eddiefullmetal
 * 
 */
public class LoadSdModelAction
        extends AbstractAction
{

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        JFileChooser fileChooser = new JFileChooser( );
        fileChooser.setDialogTitle( ResourceManager.getString( LoadSdModelAction.class, "fileChooseTitle" ) );
        fileChooser.setFileSelectionMode( JFileChooser.FILES_ONLY );
        fileChooser.setFileFilter( new ExtensionFileFilter( "XML", "xml" ) );
        int result = fileChooser.showOpenDialog( null );
        if ( result == JFileChooser.APPROVE_OPTION )
        {
            Shell.loadModel( fileChooser.getSelectedFile( ) );
        }
    }

}
