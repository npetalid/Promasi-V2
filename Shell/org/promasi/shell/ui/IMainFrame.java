package org.promasi.shell.ui;

import org.promasi.communication.ICommunicator;


/**
 *
 * Interface that the mainframe of a UI implementation must implement.
 *
 * @author eddiefullmetal
 *
 */
public interface IMainFrame
{

    /**
     * Shows the main frame.
     */
    void showMainFrame ( );

    /**
     * Initializes the main frame. All member variables and any other
     * constructor related jobs must be done here.
     */
    void initializeMainFrame ( );

    /**
     * Will register the system communicator.
     * @param communicator
     */
    public void registerCommunicator(ICommunicator communicator);
}
