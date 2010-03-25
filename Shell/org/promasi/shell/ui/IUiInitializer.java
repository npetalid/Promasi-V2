package org.promasi.shell.ui;

import org.promasi.shell.Shell;


/**
 *
 * Registers needed UI components.
 *
 * @author eddiefullmetal
 *
 */
public interface IUiInitializer
{

    /**
     * Registers the {@link IMainFrame} of the UI.
     */
	void registerMainFrame(Shell shell);
}
