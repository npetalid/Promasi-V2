/**
 * 
 */
package org.promasi.ui.promasiui.multiplayer;


import javax.swing.JFrame;

import org.promasi.utilities.ui.ScreenUtils;

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
		final double sizePercentage = 0.4d;
        setSize( ScreenUtils.sizeForPercentage( sizePercentage, sizePercentage ) );
        ScreenUtils.centerInScreen( this );
		setTitle("Waiting for other players");
        setTitle( "Make Game" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
}
