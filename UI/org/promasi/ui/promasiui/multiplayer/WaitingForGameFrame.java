/**
 * 
 */
package org.promasi.ui.promasiui.multiplayer;


import javax.swing.JFrame;

import org.promasi.ui.utilities.ScreenUtils;


/**
 * @author m1cRo
 *
 */
public class WaitingForGameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public WaitingForGameFrame()
	{
		final double sizePercentage = 0.4d;
        setSize( ScreenUtils.sizeForPercentage( sizePercentage, sizePercentage ) );
        ScreenUtils.centerInScreen( this );
		setTitle("Waiting for other players");
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
