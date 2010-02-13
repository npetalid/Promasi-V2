package org.promasi.shell.ui.playmode;


import org.promasi.shell.Shell;
import org.promasi.shell.ui.IMainFrame;


/**
 * 
 * A UI component used for user log in.
 * 
 * @author eddiefullmetal
 * 
 */
public interface ILoginUi
{

    /**
     * Logs in the user. This method should call the {@link Shell#start()} in
     * order to show the {@link IMainFrame} after the user logs in.
     */
    void login ( );

    /**
     * Shows the Login UI.
     */
    void showUi ( );
}
