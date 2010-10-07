/**
 * 
 */
package org.promasi.ui.promasiui.promasidesktop.story;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * @author m1cRo
 *
 */
public class WaitingForGameForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public WaitingForGameForm()
	{
		setTitle("Waiting for other players");
	}
	
	/**
	 * 
	 */
    public void showUi ( )
    {
        // Make sure that the setVisible is called inside the event queue.
        EventQueue.invokeLater( new Runnable( )
        {
            @Override
            public void run ( )
            {
                setVisible( true );
            }

        } );
    }
}
