package org.promasi.ui.gmtools.coredesigner.actions;


import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import org.promasi.ui.gmtools.coredesigner.controllers.Shell;
import org.promasi.ui.gmtools.coredesigner.resources.ResourceManager;


/**
 * Action that creates a new PSD file.
 * 
 * @author eddiefullmetal
 * 
 */
public class NewSdModelAction
        extends AbstractAction
{

    @Override
    public void actionPerformed ( ActionEvent e )
    {
        String name = JOptionPane.showInputDialog( null, ResourceManager.getString( NewSdModelAction.class, "inputMessage" ), ResourceManager
                .getString( NewSdModelAction.class, "inputTitle" ), JOptionPane.PLAIN_MESSAGE );

        Shell.createNewModel( name );
    }
}
