package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;

import org.promasi.shell.UiManager;
import org.promasi.shell.ui.IUiInitializer;


/**
 * 
 * A {@link IUiInitializer} that registers the {@link DesktopMainFrame} to the
 * {@link UiManager}.
 * 
 * @author eddiefullmetal
 * 
 */
public class DesktopUiInitializer
        implements IUiInitializer
{

    @Override
    public void registerMainFrame ( )
    {
        // Create the DesktopMainFrame on the EventQueue.
        EventQueue.invokeLater( new Runnable( )
        {

            @Override
            public void run ( )
            {
                UiManager.getInstance( ).setRegisteredMainFrame( DesktopMainFrame.getInstance( ) );
            }
        } );
    }

}
