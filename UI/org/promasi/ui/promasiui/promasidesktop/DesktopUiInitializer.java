package org.promasi.ui.promasiui.promasidesktop;


import java.awt.EventQueue;

import org.promasi.shell.Shell;
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
	private Shell _shell;

    @Override
    public void registerMainFrame (Shell shell )
    {
    	_shell=shell;
        // Create the DesktopMainFrame on the EventQueue.
        EventQueue.invokeLater( new Runnable()
	        {

	            @Override
	            public void run ( )
	            {
	                UiManager.getInstance( ).setRegisteredMainFrame( DesktopMainFrame.getInstance(_shell) );
	            }
	        }
        );
    }

}
